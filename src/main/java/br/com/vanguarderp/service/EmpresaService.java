package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Empresa;
import br.com.vanguarderp.repository.EmpresaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Empresa> getAllEmpresas() {
		return empresaRepository.findAll();
	}

	public Empresa getBuscarPorId(Long id) {
		return empresaRepository.buscarPorId(id);
	}

	public List<Empresa> getBuscaPorNome(String nome) {
		return empresaRepository.buscaPorNome(nome);
	}

	public boolean existeEmpresaPorNome(String nome) {
		return empresaRepository.existePorNome(nome);
	}

	public boolean existeEmpresaPorNomeDiferenteId(Long id, String nome) {
		return empresaRepository.existePorNomeDiferenteId(id, nome);
	}

	public void deleteEmpresaById(Long id) {
		empresaRepository.deleteById(id);
	}

	public Empresa buscarEmpresaPorId(Long id) {
		return empresaRepository.buscarPorId(id);
	}

}
