package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.annotations.IgnoreEmpresaId;
import br.com.vanguarderp.model.Plano;
import jakarta.transaction.Transactional;

@Repository
@IgnoreEmpresaId
public interface PlanoRepository extends JpaVanguardRepository<Plano, Long> {

	@Query("select p from Plano p")
	List<Plano> findAll();

	@Query("select p from Plano p "
								+ " where unaccent(upper(trim(p.nome))) "
								+ " like unaccent(upper(concat('%', trim(:nome) ,'%')))")
	List<Plano> buscaPorNome(@Param("nome") String nome);
	
	@Query("select count(p.id) > 0 from Plano p "
			+ " where unaccent(upper(trim(p.nome))) "
			+ " = unaccent(upper(trim(:nome)))")
	boolean existePorNome(@Param("nome") String nome);
	
	@Query("select count(p.id) > 0 from Plano p "
			+ " where unaccent(upper(trim(p.nome))) "
			+ " = unaccent(upper(trim(:nome))) and p.id <> :id")
    boolean existePorNomeDiferenteId(@Param("id") Long id, @Param("nome") String nome);	
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Plano p where p.id = :id")
	void deleteById(@Param("id") Long id);
	
}
