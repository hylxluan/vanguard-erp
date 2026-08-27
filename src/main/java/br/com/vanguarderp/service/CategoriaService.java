package br.com.vanguarderp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
}
