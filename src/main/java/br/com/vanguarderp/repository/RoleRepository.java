package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.annotations.IgnoreEmpresaId;
import br.com.vanguarderp.model.Role;
import jakarta.transaction.Transactional;

@Repository
@IgnoreEmpresaId
public interface RoleRepository extends JpaVanguardRepository<Role, Long> {
	
	@Query("select r from Role r")
	List<Role> findAll();

	@Query("select r from Role r "
								+ " where unaccent(upper(trim(r.acesso))) "
								+ " like unaccent(upper(concat('%', trim(:acesso) ,'%')))")
	List<Role> buscaPorAcesso(@Param("acesso") String acesso);
	
	@Query("select count(r.id) > 0 from Role r "
			+ " where unaccent(upper(trim(r.acesso))) "
			+ " = unaccent(upper(trim(:acesso)))")
	boolean existePorAcesso(@Param("acesso") String acesso);
	
	@Query("select count(r.id) > 0 from Role r "
			+ " where unaccent(upper(trim(r.acesso))) "
			+ " = unaccent(upper(trim(:acesso))) and r.id <> :id")
    boolean existePorAcessoDiferenteId(@Param("id") Long id, @Param("acesso") String acesso);	
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Role r where r.id = :id")
	void deleteById(@Param("id") Long id);
	
}
