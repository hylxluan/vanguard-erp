package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Categoria;
import br.com.vanguarderp.repository.CategoriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	

	public List<Categoria> getAllCategorias() {
		return categoriaRepository.findAll();
	}
	
	public List<Categoria> getCategoriaPorNome(String nome, Long idEmpresa) {
		return categoriaRepository.buscarPorNome(nome, idEmpresa);
	}
	
	public boolean existeCategoriaPorNome(String nome, Long idEmpresa) {
		return categoriaRepository.existePorNome(nome, idEmpresa);
	}
	
	public boolean existeCategoriaPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return categoriaRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}
	
	public void deleteCategoriaByIdAndEmpresa(Long id, Long idEmpresa) {
		categoriaRepository.deleteByIdAndEmpresa(id, idEmpresa);
	}
	
	Page<Categoria> listarCategoriasPaginado(Long empresaId, Pageable pageable) {
		return categoriaRepository.listarPaginado(empresaId, pageable);
	}
	
	long totalCategorias(Long empresaId) {
		return categoriaRepository.total(empresaId);
	}
	
	Optional<Categoria> buscarCategoriaPorId(Long id, Long empresaId) {
		return categoriaRepository.buscarPorId(id, empresaId);
	}
	
	List<Categoria> buscarCategoriasPorIds(Iterable<Long> ids, Long empresaId) {
		return categoriaRepository.buscarPorIds(ids, empresaId);
	}
	
	boolean existeCategoriaPorId(Long id, Long empresaId) {
		return categoriaRepository.existePorId(id, empresaId);
	}
	
	List<Categoria> listarCategorias(Long empresaId) {
		return categoriaRepository.listar(empresaId);
	}
	
	long deletarCategoriasPorIds(Iterable<Long> ids, Long empresaId) {
		return categoriaRepository.deletarAllPorIds(ids, empresaId);
	}
	
	long deletarTodasCategorias(Long empresaId) {
		return categoriaRepository.deletarAll(empresaId);
	}
	
}
