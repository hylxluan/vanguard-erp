package br.com.vanguarderp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vanguarderp.dto.LoginDTO;
import br.com.vanguarderp.dto.TokenDTO;
import br.com.vanguarderp.service.UsuarioLogadoService;
import br.com.vanguarderp.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
		
		TokenDTO token = usuarioService.login(loginDTO);
		
		return ResponseEntity.ok(token);
	}

}
