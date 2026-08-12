package br.com.vanguarderp.repository;

import java.util.List;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.model.Categoria;
import jakarta.transaction.Transactional;

@Repository
public interface CategoriaRepository extends JpaVanguardRepository<Categoria, Long> {
	
	
	@Query("select c from Categoria c where c.empresa.id = :idEmpresa")
	List<Categoria> findAll(@Param("idEmpresa") Long idEmpresa);
	
	
	@Query("select c from Categoria c where c.empresa.id = :idEmpresa " +
			" and upper(trim(c.nome)) " + 
			" like upper(concat('%', trim(:nomeCategoria), '%'))")
	List<Categoria> buscarPorNome(@Param("nomeCategoria") String nome, @Param("idEmpresa") Long idEmpresa);
	
	
	@Query("select count(c.id) > 0 from Categoria c " +
		   "where c.empresa.id = :idEmpresa and " +
		   "upper(trim(c.nome)) " + 
		   " = upper(trim(:nomeCategoria))")
	boolean existePorNome(@Param("nomeCategoria") String nome, @Param("idEmpresa") Long idEmpresa);
	
	
	@Query("select count(c.id) > 0 from Categoria c where c.empresa.id = :idEmpresa " +
		   " and upper(trim(c.nome)) = upper(trim(:nomeCategoria)) and c.id <> :id")
	boolean existePorNomeDiferenteId(@Param("id") Long id, @Param("nomeCategoria") String nome, 
									 @Param("idEmpresa") Long idEmpresa);
	
	
	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("delete from Categoria c where c.empresa.id = :idEmpresa and " +
		   " c.id = :id")
	void deleteByIdAndEmpresa(@Param("id") Long id, @Param("idEmpresa") Long idEmpresa);

	
	
	
}
