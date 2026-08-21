package br.com.vanguarderp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.annotations.IgnoreEmpresaId;
import br.com.vanguarderp.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaVanguardRepository<Usuario, Long> {

	@IgnoreEmpresaId(ignore = true, motivo = "Busca de usuário pelo login para então associar à sua empresa")
	@Query("select u from Usuario u where u.login = :login")
	Usuario buscarPorLogin(@Param("login") String login);
	
}
