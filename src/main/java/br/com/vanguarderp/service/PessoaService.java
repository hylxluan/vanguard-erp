package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Pessoa;
import br.com.vanguarderp.repository.PessoaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Pessoa> getAllPessoas(Long idEmpresa) {
		return pessoaRepository.findAll(idEmpresa);
	}

	public List<Pessoa> getBuscaPorNome(String nome, Long idEmpresa) {
		return pessoaRepository.buscaPorNome(nome, idEmpresa);
	}

	public boolean existePessoaPorNome(String nome, Long idEmpresa) {
		return pessoaRepository.existePorNome(nome, idEmpresa);
	}

	public boolean existePessoaPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return pessoaRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}

	public void deletePessoaByIdAndEmpresa(Long id, Long idEmpresa) {
		pessoaRepository.deleteById(id, idEmpresa);
	}

	public Page<Pessoa> listarPessoasPaginado(Long empresaId, Pageable pageable) {
		return pessoaRepository.listarPaginado(empresaId, pageable);
	}

	public long totalPessoas(Long empresaId) {
		return pessoaRepository.total(empresaId);
	}

	public Optional<Pessoa> buscarPessoaPorId(Long id, Long empresaId) {
		return pessoaRepository.buscarPorId(id, empresaId);
	}

	public List<Pessoa> buscarPessoasPorIds(Iterable<Long> ids, Long empresaId) {
		return pessoaRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existePessoaPorId(Long id, Long empresaId) {
		return pessoaRepository.existePorId(id, empresaId);
	}

	public List<Pessoa> listarPessoas(Long empresaId) {
		return pessoaRepository.listar(empresaId);
	}

	public long deletarPessoasPorIds(Iterable<Long> ids, Long empresaId) {
		return pessoaRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodasPessoas(Long empresaId) {
		return pessoaRepository.deletarAll(empresaId);
	}

}
