package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.annotations.IgnoreEmpresaId;
import br.com.vanguarderp.model.RoleUsuario;
import jakarta.transaction.Transactional;

@Repository
@IgnoreEmpresaId
public interface RoleUsuarioRepository extends JpaVanguardRepository<RoleUsuario, Long> {

	@Query("select r from RoleUsuario r where r.usuario.id = :idUsuario and r.usuario.empresa.id = :idEmpresa")
	List<RoleUsuario> findAllByUsuario(@Param("idUsuario") Long idUsuario, @Param("idEmpresa") Long idEmpresa);

	@Query("select r from RoleUsuario r where r.acesso.id = :idRole and r.usuario.empresa.id = :idEmpresa")
	List<RoleUsuario> findAllByRoleAndEmpresa(@Param("idRole") Long idRole, @Param("idEmpresa") Long idEmpresa);

	@Query("select count(r.id) > 0 from RoleUsuario r where r.usuario.id = :idUsuario and r.usuario.empresa.id = :idEmpresa and r.acesso.id = :idRole")
	boolean existePorUsuarioERole(@Param("idUsuario") Long idUsuario, @Param("idRole") Long idRole, @Param("idEmpresa") Long idEmpresa);

	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from RoleUsuario r where r.id = :id")
	void deleteById(@Param("id") Long id);

	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from RoleUsuario r where r.usuario.id = :idUsuario and r.usuario.empresa.id = :idEmpresa and r.acesso.id = :idRole")
	void deleteByUsuarioAndRole(@Param("idUsuario") Long idUsuario, @Param("idRole") Long idRole, @Param("idEmpresa") Long idEmpresa);
	
}
