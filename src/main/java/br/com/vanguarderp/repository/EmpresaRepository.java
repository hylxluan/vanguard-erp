package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.annotations.IgnoreEmpresaId;
import br.com.vanguarderp.model.Empresa;
import jakarta.transaction.Transactional;

@Repository
@IgnoreEmpresaId
public interface EmpresaRepository extends JpaVanguardRepository<Empresa, Long> {

	@Query("select c from Empresa c ")
	List<Empresa> findAll();
	
	@Query("select c from Empresa c where c.id = :id")
	Empresa buscarPorId(@Param("id") Long id);

	@Query("select e from Empresa e where unaccent(upper(trim(e.pessoa.nome))) "
			+ " like unaccent(upper(concat('%', trim(:nome) ,'%')))" )
	List<Empresa> buscaPorNome(@Param("nome") String nome);

	@Query("select count(e.id) > 0 from Empresa e "
			+ " where unaccent(upper(trim(e.pessoa.nome))) "
			+ " = unaccent(upper(trim(:nome)))")
	boolean existePorNome(@Param("nome") String nome);

	@Query("select count(e.id) > 0 from Empresa e "
			+ " where unaccent(upper(trim(e.pessoa.nome))) "
			+ " = unaccent(upper(trim(:nome))) and e.id <> :id")
	boolean existePorNomeDiferenteId(@Param("id") Long id, @Param("nome") String nome);

	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Empresa e where e.id = :id")
	void deleteById(@Param("id") Long id);
	
}
