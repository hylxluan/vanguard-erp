package br.com.vanguarderp.security;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.vanguarderp.model.Usuario;

public class UsuarioAutenticado implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final Usuario usuario;
	
	
	private UsuarioAutenticado(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return usuario.getAuthorities();
	}

	@Override
	public @Nullable String getPassword() {
		
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		
		return usuario.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return usuario.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return usuario.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return usuario.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		
		return usuario.isEnabled();
	}
	
	
	
	
}
