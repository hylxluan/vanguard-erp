package br.com.vanguarderp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.exceptions.MsgApiException;
import br.com.vanguarderp.model.RoleUsuario;
import br.com.vanguarderp.repository.RoleUsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class RoleUsuarioService {

	@Autowired
	private RoleUsuarioRepository roleUsuarioRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UsuarioLogadoService usuarioLogadoService;

	public RoleUsuario salvar(RoleUsuario roleUsuario) {

		if (roleUsuario.getAcesso() == null) {
			throw new MsgApiException("Acesso deve ser informado para associar ao usuário.");
		}

		if (roleUsuario.getUsuario() == null) {
			throw new MsgApiException("Usuário deve ser informado para associar acesso.");
		}

		if (roleUsuarioRepository.existePorUsuarioERole(roleUsuario.getUsuario().getId(),
				roleUsuario.getAcesso().getId(), usuarioLogadoService.getIdEmpresaLogada())) {

			throw new MsgApiException("Já existe o acesso de: " + roleUsuario.getAcesso().getAcesso()
					+ " associado para o mesmo usuário: "
					+ roleUsuario.getUsuario().getClienteFuncionario().getPessoa().getNome());
		}

		return roleUsuarioRepository.saveAndFlush(roleUsuario);
	}

	public List<RoleUsuario> getFindAllByUsuario(Long idUsuario, Long idEmpresa) {
		return roleUsuarioRepository.findAllByUsuario(idUsuario, idEmpresa);
	}

	public List<RoleUsuario> getFindAllByRoleAndEmpresa(Long idRole, Long idEmpresa) {
		return roleUsuarioRepository.findAllByRoleAndEmpresa(idRole, idEmpresa);
	}

	public boolean existeRoleUsuarioPorUsuarioERole(Long idUsuario, Long idRole, Long idEmpresa) {
		return roleUsuarioRepository.existePorUsuarioERole(idUsuario, idRole, idEmpresa);
	}

	public void deleteRoleUsuarioById(Long id) {
		roleUsuarioRepository.deleteById(id);
	}

	public void deleteRoleUsuarioByUsuarioAndRole(Long idUsuario, Long idRole, Long idEmpresa) {
		roleUsuarioRepository.deleteByUsuarioAndRole(idUsuario, idRole, idEmpresa);
	}

}
