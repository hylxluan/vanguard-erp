package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Usuario;
import br.com.vanguarderp.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Usuario> getAllUsuarios(Long idEmpresa) {
		return usuarioRepository.findAll(idEmpresa);
	}

	public Usuario getBuscarPorLogin(String login) {
		return usuarioRepository.buscarPorLogin(login);
	}

	public List<Usuario> getBuscaPorNome(String nome, Long idEmpresa) {
		return usuarioRepository.buscaPorNome(nome, idEmpresa);
	}

	public boolean existeUsuarioPorLogin(String login, Long idEmpresa) {
		return usuarioRepository.existePorLogin(login, idEmpresa);
	}

	public boolean existeUsuarioPorPessoa(Long idPessoa, Long idEmpresa) {
		return usuarioRepository.existePorPessoa(idPessoa, idEmpresa);
	}

	public boolean existeUsuarioPorNome(String nome, Long idEmpresa) {
		return usuarioRepository.existePorNome(nome, idEmpresa);
	}

	public boolean existeUsuarioPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return usuarioRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}

	public boolean existeOutroUsuarioComPessoa(Long pessoaId, Long usuarioId, Long idEmpresa) {
		return usuarioRepository.existeOutroUsuarioComPessoa(pessoaId, usuarioId, idEmpresa);
	}

	public void deleteUsuarioByIdAndEmpresa(Long id, Long idEmpresa) {
		usuarioRepository.deleteById(id, idEmpresa);
	}

	public void updateAccessTokenLogin(Long id, String token, Long idEmpresa) {
		usuarioRepository.updateAccessTokenLogin(id, token, idEmpresa);
	}

	public Page<Usuario> listarUsuariosPaginado(Long empresaId, Pageable pageable) {
		return usuarioRepository.listarPaginado(empresaId, pageable);
	}

	public long totalUsuarios(Long empresaId) {
		return usuarioRepository.total(empresaId);
	}

	public Optional<Usuario> buscarUsuarioPorId(Long id, Long empresaId) {
		return usuarioRepository.buscarPorId(id, empresaId);
	}

	public List<Usuario> buscarUsuariosPorIds(Iterable<Long> ids, Long empresaId) {
		return usuarioRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existeUsuarioPorId(Long id, Long empresaId) {
		return usuarioRepository.existePorId(id, empresaId);
	}

	public List<Usuario> listarUsuarios(Long empresaId) {
		return usuarioRepository.listar(empresaId);
	}

	public long deletarUsuariosPorIds(Iterable<Long> ids, Long empresaId) {
		return usuarioRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodosUsuarios(Long empresaId) {
		return usuarioRepository.deletarAll(empresaId);
	}

}
