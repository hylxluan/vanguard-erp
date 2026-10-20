package br.com.vanguarderp.controller;

import br.com.vanguarderp.dto.LoginDTO;
import br.com.vanguarderp.dto.TokenDTO;
import br.com.vanguarderp.dto.UsuarioDTO;
import br.com.vanguarderp.service.UsuarioLogadoService;
import br.com.vanguarderp.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


	@GetMapping("/listar")
	public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
		return ResponseEntity.ok(usuarioService.listarUsuarios(usuarioLogadoService.getIdEmpresaLogada()));
	}

}
