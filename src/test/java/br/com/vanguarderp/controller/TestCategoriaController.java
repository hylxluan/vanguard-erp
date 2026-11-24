package br.com.vanguarderp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.annotation.Transactional;

import br.com.vanguarderp.context.TestSpringContext;
import br.com.vanguarderp.model.Categoria;
import br.com.vanguarderp.model.Usuario;
import br.com.vanguarderp.repository.CategoriaRepository;
import br.com.vanguarderp.repository.UsuarioRepository;
import br.com.vanguarderp.security.UsuarioAutenticado;

/**
 * Testes de POST /api/categoria/cadastrar e /api/categoria/atualizar.
 * Usa o usuário 1 (empresa 1) do V1__init.sql como logado.
 */
public class TestCategoriaController extends TestSpringContext {

	private static final String URL_CADASTRAR = "/api/categoria/cadastrar";
	private static final String URL_ATUALIZAR = "/api/categoria/atualizar";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	private Usuario usuario;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@BeforeEach
	public void autenticarUsuario() {
		// TransactionTemplate para funcionar também no teste NOT_SUPPORTED (sem sessão aberta)
		UsernamePasswordAuthenticationToken auth = new TransactionTemplate(transactionManager).execute(tx -> {
			usuario = usuarioRepository.buscarPorId(1L, 1L).orElseThrow();
			UsuarioAutenticado principal = new UsuarioAutenticado(usuario);
			return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
		});

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@AfterEach
	public void limparAutenticacao() {
		SecurityContextHolder.clearContext();
	}

	/* ---------- cadastrar ---------- */

	@Test
	public void cadastrarNomeNovoRetorna200() throws Exception {
		postJson(URL_CADASTRAR, "{\"nome\": \"Cat Teste Nova " + System.nanoTime() + "\"}")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.empresaId").value(usuario.getEmpresa().getId()));
	}

	@Test
	public void cadastrarNomeDuplicadoRetorna400() throws Exception {
		Categoria existente = criarCategoria("Cat Duplicada " + System.nanoTime());

		postJson(URL_CADASTRAR, "{\"nome\": \"" + existente.getNome().toUpperCase() + "\"}")
				.andExpect(status().isBadRequest());
	}

	@Test
	public void cadastrarNomeEmBrancoRetorna400() throws Exception {
		postJson(URL_CADASTRAR, "{\"nome\": \"   \"}")
				.andExpect(status().isBadRequest());
	}

	@Test
	public void cadastrarSemNomeRetorna400() throws Exception {
		postJson(URL_CADASTRAR, "{}")
				.andExpect(status().isBadRequest());
	}

	/* ---------- atualizar ---------- */

	@Test
	public void atualizarParaNomeNovoRetorna200() throws Exception {
		Categoria cat = criarCategoria("Cat Antiga " + System.nanoTime());
		String novoNome = "Cat Renomeada " + System.nanoTime();

		postJson(URL_ATUALIZAR, "{\"id\": " + cat.getId() + ", \"nome\": \"" + novoNome + "\"}")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value(novoNome));
	}

	@Test
	public void atualizarMantendoOMesmoNomeRetorna200() throws Exception {
		Categoria cat = criarCategoria("Cat Mesmo Nome " + System.nanoTime());

		// só muda maiúsculas/minúsculas — é a mesma categoria, não deveria acusar duplicidade
		postJson(URL_ATUALIZAR, "{\"id\": " + cat.getId() + ", \"nome\": \"" + cat.getNome().toUpperCase() + "\"}")
				.andExpect(status().isOk());
	}

	@Test
	public void atualizarParaNomeDeOutraCategoriaRetorna400() throws Exception {
		Categoria a = criarCategoria("Cat A " + System.nanoTime());
		Categoria b = criarCategoria("Cat B " + System.nanoTime());

		postJson(URL_ATUALIZAR, "{\"id\": " + b.getId() + ", \"nome\": \"" + a.getNome() + "\"}")
				.andExpect(status().isBadRequest());
	}

	@Test
	public void atualizarSemIdRetorna400() throws Exception {
		postJson(URL_ATUALIZAR, "{\"nome\": \"Qualquer " + System.nanoTime() + "\"}")
				.andExpect(status().isBadRequest());
	}

	@Test
	public void atualizarIdInexistenteRetorna400() throws Exception {
		postJson(URL_ATUALIZAR, "{\"id\": 999999999, \"nome\": \"Qualquer " + System.nanoTime() + "\"}")
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Categoria não encontrada pra esta empresa"));
	}

	/**
	 * Roda SEM a transação do teste, igual à aplicação rodando de verdade,
	 * para pegar problema de @Transactional(readOnly = true) no service.
	 */
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void atualizarPersisteNoBancoForaDaTransacaoDoTeste() throws Exception {
		Categoria cat = criarCategoria("Cat Commit " + System.nanoTime());
		String novoNome = "Cat Commit Renomeada " + System.nanoTime();
		try {
			postJson(URL_ATUALIZAR, "{\"id\": " + cat.getId() + ", \"nome\": \"" + novoNome + "\"}")
					.andExpect(status().isOk());

			String nomeNoBanco = categoriaRepository.buscarPorId(cat.getId(), 1L).orElseThrow().getNome();
			assertEquals(novoNome, nomeNoBanco);
		} finally {
			categoriaRepository.deleteByIdAndEmpresa(cat.getId(), 1L);
		}
	}

	/* ---------- Helpers ---------- */

	private Categoria criarCategoria(String nome) {
		Categoria c = new Categoria();
		c.setNome(nome);
		c.setEmpresa(usuario.getEmpresa());
		return categoriaRepository.saveAndFlush(c);
	}

	private ResultActions postJson(String url, String body) throws Exception {
		return mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(body));
	}
}
