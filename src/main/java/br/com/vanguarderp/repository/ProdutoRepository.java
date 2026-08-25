package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.model.Produto;
import jakarta.transaction.Transactional;

@Repository
public interface ProdutoRepository extends JpaVanguardRepository<Produto, Long> {

	@Query("select p from Produto p where p.empresa.id = :idEmpresa")
	List<Produto> findAll(@Param("idEmpresa") Long idEmpresa); 

	@Query("select p from Produto p where p.empresa.id = :idEmpresa "
								+ " and unaccent(upper(trim(p.nome))) "
								+ " like unaccent(upper(concat('%', trim(:nome) ,'%')))")
	List<Produto> buscaPorNome(@Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);
	
	@Query("select count(p.id) > 0 from Produto p where p.empresa.id = :idEmpresa "
			+ " and unaccent(upper(trim(p.nome))) "
			+ " = unaccent(upper(trim(:nome)))")
	boolean existePorNome(@Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);
	
	@Query("select count(p.id) > 0 from Produto p where p.empresa.id = :idEmpresa "
			+ " and unaccent(upper(trim(p.nome))) "
			+ " = unaccent(upper(trim(:nome))) and p.id <> :id")
    boolean existePorNomeDiferenteId(@Param("id") Long id, @Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);	
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Produto p where p.empresa.id = :idEmpresa and p.id = :id")
	void deleteById(@Param("id") Long id, @Param("idEmpresa") Long idEmpresa);

}