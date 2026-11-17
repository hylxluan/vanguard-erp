package br.com.vanguarderp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.jayway.jsonpath.JsonPath;

import br.com.vanguarderp.context.TestSpringContext;
import br.com.vanguarderp.model.Usuario;
import br.com.vanguarderp.repository.UsuarioRepository;
import br.com.vanguarderp.security.UsuarioAutenticado;

/**
 * Testes do GET /api/usuario/paginado variando os request params
 * (page, size, sort, direction). Usa o usuário 1 (empresa 1) do V1__init.sql como logado.
 */
public class TestUsuarioControllerPaginado extends TestSpringContext {

	private static final String URL = "/api/usuario/paginado";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsuarioRepository usuarioRepository;

	private Long empresaId;
	private long totalUsuarios;

	@BeforeEach
	public void autenticarUsuario() {
		Usuario usuario = usuarioRepository.buscarPorId(1L, 1L).orElseThrow();
		UsuarioAutenticado principal = new UsuarioAutenticado(usuario);

		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));

		empresaId = usuario.getEmpresa().getId();
		totalUsuarios = usuarioRepository.total(empresaId);
	}

	@AfterEach
	public void limparAutenticacao() {
		SecurityContextHolder.clearContext();
	}

	/* ---------- Cenários de sucesso ---------- */

	@Test
	public void semParametrosUsaDefaultsPage0Size10IdDesc() throws Exception {
		String json = mockMvc.perform(get(URL))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		List<Long> ids = extrairIds(json);
		System.out.println("Paginado default: " + ids);

		assertEquals(Math.min(10, totalUsuarios), ids.size());
		assertOrdenadoDesc(ids);
	}

	@Test
	public void pageESizeLimitamOResultado() throws Exception {
		String json = perform("0", "1", "id", "asc")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andReturn().getResponse().getContentAsString();

		Long menorId = idsDaEmpresaOrdenados().get(0);
		assertEquals(menorId, extrairIds(json).get(0));
	}

	@Test
	public void segundaPaginaTrazOProximoRegistro() throws Exception {
		assertTrue(totalUsuarios >= 2, "Precisa de pelo menos 2 usuários na empresa 1 (V1__init.sql)");

		String pagina0 = perform("0", "1", "id", "asc").andReturn().getResponse().getContentAsString();
		String pagina1 = perform("1", "1", "id", "asc").andReturn().getResponse().getContentAsString();

		List<Long> ids = idsDaEmpresaOrdenados();
		assertEquals(ids.get(0), extrairIds(pagina0).get(0));
		assertEquals(ids.get(1), extrairIds(pagina1).get(0));
	}

	@Test
	public void paginaAlemDoTotalRetornaListaVazia() throws Exception {
		perform("999", "10", "id", "asc")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));
	}

	@Test
	public void ordenaPorIdAscEDesc() throws Exception {
		String asc = perform("0", "100", "id", "asc").andReturn().getResponse().getContentAsString();
		String desc = perform("0", "100", "id", "desc").andReturn().getResponse().getContentAsString();

		List<Long> idsAsc = extrairIds(asc);
		List<Long> idsDesc = extrairIds(desc);

		assertEquals(idsDaEmpresaOrdenados(), idsAsc);
		assertOrdenadoDesc(idsDesc);
	}

	@Test
	public void ordenaPorLoginDescEhOInversoDoAsc() throws Exception {
		String asc = perform("0", "100", "login", "asc").andReturn().getResponse().getContentAsString();
		String desc = perform("0", "100", "login", "desc").andReturn().getResponse().getContentAsString();

		List<String> loginsAsc = JsonPath.read(asc, "$[*].login");
		List<String> loginsDesc = new ArrayList<>(JsonPath.read(desc, "$[*].login"));
		Collections.reverse(loginsDesc);

		System.out.println("Logins asc: " + loginsAsc);
		assertEquals(totalUsuarios, loginsAsc.size());
		assertEquals(loginsAsc, loginsDesc);
	}

	@Test
	public void directionNaoDiferenciaMaiusculas() throws Exception {
		String upper = perform("0", "100", "id", "ASC").andReturn().getResponse().getContentAsString();
		assertEquals(idsDaEmpresaOrdenados(), extrairIds(upper));
	}

	@Test
	public void ordenaPorNomeDaPessoa() throws Exception {
		perform("0", "10", "pessoa", "asc")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].pessoa").isNotEmpty());
	}

	@Test
	public void ordenaPorLiberado() throws Exception {
		perform("0", "10", "liberado", "desc")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].liberado").isBoolean());
	}

	@Test
	public void retornaCamposDoDtoComSenhaMascarada() throws Exception {
		perform("0", "1", "id", "asc")
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").isNumber())
				.andExpect(jsonPath("$[0].login").isNotEmpty())
				.andExpect(jsonPath("$[0].pessoa").isNotEmpty())
				.andExpect(jsonPath("$[0].pessoaId").isNumber())
				.andExpect(jsonPath("$[0].empresa").isNotEmpty())
				.andExpect(jsonPath("$[0].clienteFuncionario").isNumber())
				.andExpect(jsonPath("$[0].tipoClienteFuncionario").isNotEmpty())
				.andExpect(jsonPath("$[0].senha").value("*****"));
	}

	/* ---------- Cenários de erro (400) ---------- */

	@Test
	public void pageNegativaRetorna400() throws Exception {
		perform("-1", "10", "id", "asc")
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("A página deve ser maior ou igual a 0."));
	}

	@Test
	public void sizeZeroRetorna400() throws Exception {
		perform("0", "0", "id", "asc")
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("O tamanho da página deve estar entre 1 e 100."));
	}

	@Test
	public void sizeAcimaDoMaximoRetorna400() throws Exception {
		perform("0", "101", "id", "asc")
				.andExpect(status().isBadRequest());
	}

	@Test
	public void campoDeOrdenacaoForaDaWhitelistRetorna400() throws Exception {
		perform("0", "10", "senha", "asc")
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value(
						"Campo de ordenação inválido: senha. Use: id, login, liberado ou pessoa."));
	}

	@Test
	public void tentativaDeInjecaoNoSortRetorna400() throws Exception {
		perform("0", "10", "id, (select senha from Usuario u where u.id = 1)", "asc")
				.andExpect(status().isBadRequest());
	}

	@Test
	public void directionInvalidaRetorna400() throws Exception {
		perform("0", "10", "id", "cima")
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Direção de ordenação inválida: cima. Use asc ou desc."));
	}

	@Test
	public void pageNaoNumericaRetorna400() throws Exception {
		perform("abc", "10", "id", "asc")
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Valor 'abc' inválido para o parâmetro 'page'."));
	}

	/* ---------- Helpers ---------- */

	private ResultActions perform(String page, String size, String sort, String direction) throws Exception {
		return mockMvc.perform(get(URL)
				.param("page", page)
				.param("size", size)
				.param("sort", sort)
				.param("direction", direction));
	}

	private List<Long> extrairIds(String json) {
		List<Number> ids = JsonPath.read(json, "$[*].id");
		return ids.stream().map(Number::longValue).toList();
	}

	private List<Long> idsDaEmpresaOrdenados() {
		return usuarioRepository.listar(empresaId).stream()
				.map(Usuario::getId)
				.sorted()
				.toList();
	}

	private void assertOrdenadoDesc(List<Long> ids) {
		for (int i = 1; i < ids.size(); i++) {
			assertTrue(ids.get(i - 1) > ids.get(i), "Ids fora de ordem desc: " + ids);
		}
	}
}
