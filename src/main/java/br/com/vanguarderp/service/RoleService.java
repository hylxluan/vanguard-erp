package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Role;
import br.com.vanguarderp.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	public List<Role> getBuscaPorAcesso(String acesso) {
		return roleRepository.buscaPorAcesso(acesso);
	}

	public boolean existeRolePorAcesso(String acesso) {
		return roleRepository.existePorAcesso(acesso);
	}

	public boolean existeRolePorAcessoDiferenteId(Long id, String acesso) {
		return roleRepository.existePorAcessoDiferenteId(id, acesso);
	}

	public void deleteRoleById(Long id) {
		roleRepository.deleteById(id);
	}

}
