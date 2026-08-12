package br.com.vanguarderp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import br.com.vanguarderp.context.TestSpringContext;
import br.com.vanguarderp.model.Categoria;
import br.com.vanguarderp.model.Empresa;

public class TestCategoriaRepository extends TestSpringContext {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	
	@Test
	public void testFindAll() {
		Empresa empresa = empresaRepository.findById(1L).get();
		
		Categoria categoria = new Categoria();
		categoria.setNome("Acessórios");
		categoria.setEmpresa(empresa);
		
		categoria = categoriaRepository.saveAndFlush(categoria);
		
		assertNotNull(categoria);
		assertNotNull(categoria.getNome());
		assertTrue(categoria.getId() > 0);
		assertEquals("Acessórios", categoria.getNome());
		
		categoria = categoriaRepository.findAll(empresa.getId()).get(0);
		
		System.out.println("FindAll: " + categoriaRepository.findAll(empresa.getId()).get(0).getNome());
		
		assertTrue(categoria.getId() > 0);
		assertEquals("Acessórios", categoria.getNome());
		
		
	}
	
	@Test
	public void testBuscarPorNome() {
		Empresa empresa = empresaRepository.findById(1L).get();
		
		Categoria categoria = new Categoria();
		categoria.setNome("Acessórios");
		categoria.setEmpresa(empresa);
		
		categoria = categoriaRepository.saveAndFlush(categoria);
		
		assertNotNull(categoria);
		assertNotNull(categoria.getNome());
		assertTrue(categoria.getId() > 0);
		assertEquals("Acessórios", categoria.getNome());
		
		categoria = categoriaRepository.buscarPorNome(categoria.getNome(), empresa.getId()).get(0);
		
		System.out.println("Buscar por Nome: " + categoriaRepository.buscarPorNome("Acessórios", empresa.getId()).get(0).getNome());
	
		assertTrue(categoria.getId() > 0);
		assertEquals("Acessórios", categoria.getNome());
		
	}
	
	@Test
	public void testExistePorNome() {
		Empresa empresa = empresaRepository.findById(1L).get();
		
		Categoria categoria = new Categoria();
		categoria.setNome("Acessórios");
		categoria.setEmpresa(empresa);
		
		categoria = categoriaRepository.saveAndFlush(categoria);
		
		assertNotNull(categoria);
		assertNotNull(categoria.getNome());
		assertTrue(categoria.getId() > 0);
		assertEquals("Acessórios", categoria.getNome());
		
		boolean existeCategoria = categoriaRepository.existePorNome("Acessórios", empresa.getId());
		
		System.out.println("Existe por Nome: " + existeCategoria);
		
		assertTrue(existeCategoria);
		
	}
	
	@Test
	public void testExistePorNomeDiferenteId() {
		Empresa empresa = empresaRepository.findById(1L).get();
		
		Categoria categoria = new Categoria();
		categoria.setNome("Acessórios");
		categoria.setEmpresa(empresa);
		
		categoria = categoriaRepository.saveAndFlush(categoria);
		
		assertNotNull(categoria);
		assertNotNull(categoria.getNome());
		assertTrue(categoria.getId() > 0);
		assertEquals("Acessórios", categoria.getNome());
		
		boolean existeCategoriaDifId = categoriaRepository.existePorNomeDiferenteId(categoria.getId(), "Acessórios", empresa.getId());
		
		System.out.println("Existe por Nome Diferente Id: " + existeCategoriaDifId);
		
		assertTrue(existeCategoriaDifId);
	}
	
	@Test
	public void testDeleteById() {
		Empresa empresa = empresaRepository.findById(1L).get();
		
		Categoria categoria = new Categoria();
		categoria.setNome("Limpeza");
		categoria.setEmpresa(empresa);
		
		categoria = categoriaRepository.saveAndFlush(categoria);
		
		assertNotNull(categoria);
		assertNotNull(categoria.getNome());
		assertTrue(categoria.getId() > 0);
		assertEquals("Limpeza", categoria.getNome());
		
		categoriaRepository.deleteByIdAndEmpresa(categoria.getId(), empresa.getId());
		
		boolean existeCategoria = categoriaRepository.existePorNome("Limpeza", empresa.getId());
		
		System.out.println("Existe por Nome DeleteByIdEmpresa: " + existeCategoria);
		
		assertFalse(existeCategoria);
		
	}
	
	
	@Test
	public void testListaPaginada() {
		Empresa empresa = empresaRepository.findById(1L).get();
		Pageable pageable = PageRequest.of(2, 10, Sort.by(Sort.Direction.DESC, "nome"));
		
		Page<Categoria> categoriasPaginadas = categoriaRepository.listarPaginado(empresa.getId(), pageable);
		
		
		System.out.println(categoriasPaginadas);
		
		if (pageable.getPageNumber() == 2) {
			assertEquals("Ferramentas Elétricas", categoriasPaginadas.getContent().get(2).getNome());
		} else {
			assertEquals("Eletrônicos", categoriasPaginadas.getContent().get(1).getNome());
		}
		
		
	}
}
