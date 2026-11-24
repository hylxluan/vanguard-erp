package br.com.vanguarderp.controller;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	private static final String URL_LISTAR = "/api/categoria/listar";
	private static final String URL_PAGINADO = "/api/categoria/paginado";
	private static final String URL_BUSCAR_POR_ID = "/api/categoria/buscarPorId/";
	private static final String URL_BUSCAR_POR_NOME = "/api/categoria/buscarPorNome";
	private static final String URL_TOTAL = "/api/categoria/total";
	private static final String URL_DELETAR_POR_ID = "/api/categoria/deletarPorId/";

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

	@Test
	public void listarRetornaCategoriaDaEmpresa() throws Exception {
		Categoria cat = criarCategoria("Cat Listar " + System.nanoTime());

		mockMvc.perform(get(URL_LISTAR))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[?(@.id == " + cat.getId() + ")].nome").value(cat.getNome()))
				.andExpect(jsonPath("$[?(@.id == " + cat.getId() + ")].empresaId").value(usuario.getEmpresa().getId().intValue()));
	}

	@Test
	public void paginadoPadraoOrdenaPorIdDesc() throws Exception {
		Categoria cat = criarCategoria("Cat Paginado " + System.nanoTime());

		mockMvc.perform(get(URL_PAGINADO).param("size", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].id").value(cat.getId()));
	}

	@Test
	public void paginadoRespeitaTamanhoDaPagina() throws Exception {
		criarCategoria("Cat Pag A " + System.nanoTime());
		criarCategoria("Cat Pag B " + System.nanoTime());
		criarCategoria("Cat Pag C " + System.nanoTime());

		mockMvc.perform(get(URL_PAGINADO).param("page", "0").param("size", "2")
						.param("sort", "nome").param("direction", "asc"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	public void paginadoPaginaAlemDoTotalRetornaVazio() throws Exception {
		mockMvc.perform(get(URL_PAGINADO).param("page", "999999").param("size", "100"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	public void paginadoTamanhoMaximoRespeitado() throws Exception {
		mockMvc.perform(get(URL_PAGINADO).param("size", "100"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(lessThanOrEqualTo(100)));
	}

	@Test
	public void paginadoPaginaNegativaRetorna400() throws Exception {
		mockMvc.perform(get(URL_PAGINADO).param("page", "-1"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("A página deve ser maior ou igual a 0."));
	}

	@Test
	public void paginadoTamanhoZeroRetorna400() throws Exception {
		mockMvc.perform(get(URL_PAGINADO).param("size", "0"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void paginadoTamanhoAcimaDoMaximoRetorna400() throws Exception {
		mockMvc.perform(get(URL_PAGINADO).param("size", "101"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void paginadoCampoOrdenacaoInvalidoRetorna400() throws Exception {
		mockMvc.perform(get(URL_PAGINADO).param("sort", "empresa"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Campo de ordenação inválido: empresa. Use: id ou nome."));
	}

	@Test
	public void paginadoDirecaoInvalidaRetorna400() throws Exception {
		mockMvc.perform(get(URL_PAGINADO).param("direction", "cima"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Direção de ordenação inválida: cima. Use asc ou desc."));
	}

	@Test
	public void paginadoPageNaoNumericoRetorna400() throws Exception {
		mockMvc.perform(get(URL_PAGINADO).param("page", "abc"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void buscarPorIdExistenteRetorna200() throws Exception {
		Categoria cat = criarCategoria("Cat Buscar Id " + System.nanoTime());

		mockMvc.perform(get(URL_BUSCAR_POR_ID + cat.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(cat.getId()))
				.andExpect(jsonPath("$.nome").value(cat.getNome()))
				.andExpect(jsonPath("$.empresaId").value(usuario.getEmpresa().getId()));
	}

	@Test
	public void buscarPorIdInexistenteRetorna400() throws Exception {
		mockMvc.perform(get(URL_BUSCAR_POR_ID + 999999999))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Categoria não encontrada para a empresa logada."));
	}

	@Test
	public void buscarPorIdNaoNumericoRetorna400() throws Exception {
		mockMvc.perform(get(URL_BUSCAR_POR_ID + "abc"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void buscarPorNomeParcialIgnorandoCaixaEAcento() throws Exception {
		String sufixo = String.valueOf(System.nanoTime());
		Categoria cat = criarCategoria("Eletrônicos " + sufixo);

		mockMvc.perform(get(URL_BUSCAR_POR_NOME).param("nome", "ELETRONICOS " + sufixo))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].id").value(cat.getId()));
	}

	@Test
	public void buscarPorNomeSemResultadoRetornaListaVazia() throws Exception {
		mockMvc.perform(get(URL_BUSCAR_POR_NOME).param("nome", "Nao Existe " + System.nanoTime()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	public void buscarPorNomeSemParametroRetorna400() throws Exception {
		mockMvc.perform(get(URL_BUSCAR_POR_NOME))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void totalContaCategoriasDaEmpresa() throws Exception {
		long totalAntes = categoriaRepository.total(usuario.getEmpresa().getId());
		criarCategoria("Cat Total " + System.nanoTime());

		mockMvc.perform(get(URL_TOTAL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").value(totalAntes + 1));
	}

	@Test
	public void deletarPorIdExistenteRetorna200() throws Exception {
		Categoria cat = criarCategoria("Cat Deletar " + System.nanoTime());

		mockMvc.perform(delete(URL_DELETAR_POR_ID + cat.getId()))
				.andExpect(status().isOk());

		assertFalse(categoriaRepository.existePorId(cat.getId(), usuario.getEmpresa().getId()));
	}

	@Test
	public void deletarPorIdInexistenteRetorna400() throws Exception {
		mockMvc.perform(delete(URL_DELETAR_POR_ID + 999999999))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Categoria não encontrada para a empresa logada, portanto não pode ser deletada."));
	}

	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void deletarPersisteNoBancoForaDaTransacaoDoTeste() throws Exception {
		Categoria cat = criarCategoria("Cat Deletar Commit " + System.nanoTime());
		try {
			mockMvc.perform(delete(URL_DELETAR_POR_ID + cat.getId()))
					.andExpect(status().isOk());

			assertFalse(categoriaRepository.existePorId(cat.getId(), 1L));
		} finally {
			if (categoriaRepository.existePorId(cat.getId(), 1L)) {
				categoriaRepository.deleteByIdAndEmpresa(cat.getId(), 1L);
			}
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
