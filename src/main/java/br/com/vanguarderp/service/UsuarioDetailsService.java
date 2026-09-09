package br.com.vanguarderp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.vanguarderp.exceptions.MsgApiException;
import br.com.vanguarderp.model.Usuario;
import br.com.vanguarderp.repository.UsuarioRepository;

public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
	
}
