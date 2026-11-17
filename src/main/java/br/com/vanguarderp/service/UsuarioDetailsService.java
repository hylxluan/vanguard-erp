package br.com.vanguarderp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.exceptions.MsgApiException;
import br.com.vanguarderp.model.Usuario;
import br.com.vanguarderp.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private JwtService jwtService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuarioRecuperado = usuarioRepository.buscarPorLogin(username);
		
		if (usuarioRecuperado == null) {
			throw new UsernameNotFoundException("Usuário não encontrado com o login: " + username);
		}
		
		if (!usuarioRecuperado.isEnabled()) {
			throw new MsgApiException("Usuário está bloqueado ou inativo!", HttpStatus.FORBIDDEN);
		}
		
		if (usuarioRecuperado.getEmpresa().getBloqueio()) {
			throw new MsgApiException("Empresa do usuário está bloqueada!", HttpStatus.FORBIDDEN);
		}
		
		return usuarioRecuperado;
	}

	/* Só o último token gerado no login fica salvo em access_token; qualquer token anterior é rejeitado */
	public void validarTokenAtual(String token) {
		Long idUsuario = jwtService.extrairUsuarioId(token);
		Long idEmpresa = jwtService.extrairEmpresaId(token);

		if (!usuarioRepository.existsByTokenParaUsuario(idUsuario, token, idEmpresa)) {
			throw new MsgApiException("Token não é mais válido. Foi feito um novo login com este usuário.", HttpStatus.UNAUTHORIZED);
		}

	}
	
}
