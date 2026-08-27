package br.com.vanguarderp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
