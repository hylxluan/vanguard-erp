package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.ClienteFuncionario;
import br.com.vanguarderp.repository.ClienteFuncionarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ClienteFuncionarioService {

	@Autowired
	private ClienteFuncionarioRepository clienteFuncionarioRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<ClienteFuncionario> getAllClientesFuncionarios(Long idEmpresa) {
		return clienteFuncionarioRepository.findAll(idEmpresa);
	}

	public ClienteFuncionario getFindByPessoa(Long idPessoa, Long idEmpresa) {
		return clienteFuncionarioRepository.findByPessoa(idPessoa, idEmpresa);
	}

	public List<ClienteFuncionario> getBuscaPorNome(String nome, Long idEmpresa) {
		return clienteFuncionarioRepository.buscaPorNome(nome, idEmpresa);
	}

	public boolean existeClienteFuncionarioPorNome(String nome, Long idEmpresa) {
		return clienteFuncionarioRepository.existePorNome(nome, idEmpresa);
	}

	public boolean existeClienteFuncionarioPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return clienteFuncionarioRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}

	public void deleteClienteFuncionarioByIdAndEmpresa(Long id, Long idEmpresa) {
		clienteFuncionarioRepository.deleteById(id, idEmpresa);
	}

	public Page<ClienteFuncionario> listarClientesFuncionariosPaginado(Long empresaId, Pageable pageable) {
		return clienteFuncionarioRepository.listarPaginado(empresaId, pageable);
	}

	public long totalClientesFuncionarios(Long empresaId) {
		return clienteFuncionarioRepository.total(empresaId);
	}

	public Optional<ClienteFuncionario> buscarClienteFuncionarioPorId(Long id, Long empresaId) {
		return clienteFuncionarioRepository.buscarPorId(id, empresaId);
	}

	public List<ClienteFuncionario> buscarClientesFuncionariosPorIds(Iterable<Long> ids, Long empresaId) {
		return clienteFuncionarioRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existeClienteFuncionarioPorId(Long id, Long empresaId) {
		return clienteFuncionarioRepository.existePorId(id, empresaId);
	}

	public List<ClienteFuncionario> listarClientesFuncionarios(Long empresaId) {
		return clienteFuncionarioRepository.listar(empresaId);
	}

	public long deletarClientesFuncionariosPorIds(Iterable<Long> ids, Long empresaId) {
		return clienteFuncionarioRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodosClientesFuncionarios(Long empresaId) {
		return clienteFuncionarioRepository.deletarAll(empresaId);
	}

}
