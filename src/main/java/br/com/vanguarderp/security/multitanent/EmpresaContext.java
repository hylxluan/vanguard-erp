package br.com.vanguarderp.security.multitanent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.vanguarderp.service.UsuarioLogadoService;

@Component
public class EmpresaContext {

	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	public Long getEmpresaId() {
		return usuarioLogadoService.getIdEmpresaLogada();
	}
	
}
