package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Plano;
import br.com.vanguarderp.repository.PlanoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class PlanoService {

	@Autowired
	private PlanoRepository planoRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Plano> getAllPlanos() {
		return planoRepository.findAll();
	}

	public List<Plano> getBuscaPorNome(String nome) {
		return planoRepository.buscaPorNome(nome);
	}

	public boolean existePlanoPorNome(String nome) {
		return planoRepository.existePorNome(nome);
	}

	public boolean existePlanoPorNomeDiferenteId(Long id, String nome) {
		return planoRepository.existePorNomeDiferenteId(id, nome);
	}

	public void deletePlanoById(Long id) {
		planoRepository.deleteById(id);
	}

}
