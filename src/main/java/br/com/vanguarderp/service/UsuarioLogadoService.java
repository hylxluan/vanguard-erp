package br.com.vanguarderp.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Empresa;
import br.com.vanguarderp.model.Usuario;
import br.com.vanguarderp.security.UsuarioAutenticado;

@Service
public class UsuarioLogadoService {
	
	public UsuarioAutenticado getUsuarioAutenticado() {
		return (UsuarioAutenticado) SecurityContextHolder.getContext()
														 .getAuthentication()
														 .getPrincipal();
	}

	public Usuario getUsuarioLogado() {
		return getUsuarioAutenticado().getUsuario();
	}
	
	public Empresa getEmpresaLogada() {
		return getUsuarioLogado().getEmpresa();
	}
	
	public Long getIdUsuarioLogado() {
		return getUsuarioLogado().getId();
	}
	
	public Long getIdEmpresaLogada() {
		return getEmpresaLogada().getId();
	}
	
	public boolean isAdmin() {
		return getUsuarioLogado().isAdmin();
	}
	
}
