package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.annotations.IgnoreEmpresaId;
import br.com.vanguarderp.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends JpaVanguardRepository<Usuario, Long> {

	@IgnoreEmpresaId(ignore = true, motivo = "Busca de usuário pelo login para então associar à sua empresa")
	@Query("select u from Usuario u where u.login = :login")
	Usuario buscarPorLogin(@Param("login") String login);
	
	@Query("select u from Usuario u where u.empresa.id = :idEmpresa")
	List<Usuario> findAll(@Param("idEmpresa") Long idEmpresa);

	@Query("select u from Usuario u where u.empresa.id = :idEmpresa "
								+ " and unaccent(upper(trim(u.clienteFuncionario.pessoa.nome))) "
								+ " like unaccent(upper(concat('%', trim(:nome) ,'%')))")
	List<Usuario> buscaPorNome(@Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);
	
	@Query("select count(u.id) > 0 from Usuario u where u.empresa.id = :idEmpresa "
			+ " and unaccent(upper(trim(u.login))) "
			+ " = unaccent(upper(trim(:login)))")
	boolean existePorLogin(@Param("login") String login, @Param("idEmpresa") Long idEmpresa);
	
	@Query("select count(u.id) > 0 from Usuario u where u.empresa.id = :idEmpresa "
			+ " and u.clienteFuncionario.pessoa.id =: idPessoa")
	boolean existePorPessoa(@Param("nome") Long idPessoa, @Param("idEmpresa") Long idEmpresa);
	
	@Query("select count(u.id) > 0 from Usuario u where u.empresa.id = :idEmpresa "
			+ " and unaccent(upper(trim(u.clienteFuncionario.pessoa.nome))) "
			+ " = unaccent(upper(trim(:nome)))")
	boolean existePorNome(@Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);
	
	@Query("select count(u.id) > 0 from Usuario u where u.empresa.id = :idEmpresa "
			+ " and unaccent(upper(trim(u.clienteFuncionario.pessoa.nome))) "
			+ " = unaccent(upper(trim(:nome))) and u.id <> :id")
    boolean existePorNomeDiferenteId(@Param("id") Long id, @Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);	
	
	@Query("select count(u.id) > 0 from Usuario u where u.empresa.id = :idEmpresa "
			+ " and u.clienteFuncionario.pessoa.id = :pessoaId and u.id <> :usuarioId")
    boolean existeOutroUsuarioComPessoa(@Param("pessoaId") Long pessoaId, @Param("usuarioId") Long usuarioId, @Param("idEmpresa") Long idEmpresa);	
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("delete from Usuario u where u.empresa.id = :idEmpresa and u.id = :id")
	void deleteById(@Param("id") Long id, @Param("idEmpresa") Long idEmpresa);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query("update Usuario set accessToken = :token where id = :id and empresa.id = :idEmpresa")
	void updateAccessTokenLogin(@Param("id") Long id, @Param("token") String token, @Param("idEmpresa") Long idEmpresa);

	
}
