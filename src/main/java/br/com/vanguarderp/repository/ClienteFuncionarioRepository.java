package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.model.ClienteFuncionario;
import jakarta.transaction.Transactional;

@Repository
public interface ClienteFuncionarioRepository extends JpaVanguardRepository<ClienteFuncionario, Long> {

    @Query("select c from ClienteFuncionario c where c.empresa.id = :idEmpresa")
    List<ClienteFuncionario> findAll(@Param("idEmpresa") Long idEmpresa);
    
    @Query("select c from ClienteFuncionario c where c.pessoa.id = :idPessoa and c.empresa.id = :idEmpresa")
    ClienteFuncionario findByPessoa(@Param("idPessoa") Long idPessoa, @Param("idEmpresa") Long idEmpresa);

    @Query("select c from ClienteFuncionario c where c.empresa.id = :idEmpresa "
                                + " and unaccent(upper(trim(c.pessoa.nome))) "
                                + " like unaccent(upper(concat('%', trim(:nome) ,'%')))" )
    List<ClienteFuncionario> buscaPorNome(@Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);
    
    @Query("select count(c.id) > 0 from ClienteFuncionario c where c.empresa.id = :idEmpresa "
            + " and unaccent(upper(trim(c.pessoa.nome))) "
            + " = unaccent(upper(trim(:nome)))")
    boolean existePorNome(@Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);
    
    @Query("select count(c.id) > 0 from ClienteFuncionario c where c.empresa.id = :idEmpresa "
            + " and unaccent(upper(trim(c.pessoa.nome))) "
            + " = unaccent(upper(trim(:nome))) and c.id <> :id")
    boolean existePorNomeDiferenteId(@Param("id") Long id, @Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);    

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from ClienteFuncionario c where c.empresa.id = :idEmpresa and c.id = :id")
    void deleteById(@Param("id") Long id, @Param("idEmpresa") Long idEmpresa);

}