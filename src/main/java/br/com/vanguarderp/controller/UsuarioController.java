package br.com.vanguarderp.controller;

import br.com.vanguarderp.dto.AlterarSenhaDTO;
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

	@PostMapping("/cadastrarUsuario")
	public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {

		UsuarioDTO usuarioSalvo = usuarioService.salvarUsuario(usuarioDTO);

		return ResponseEntity.ok(usuarioSalvo);
	}

	@PostMapping("/atualizarUsuario")
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {

		UsuarioDTO usuarioSalvo = usuarioService.atualizarUsuario(usuarioDTO);

		return ResponseEntity.ok(usuarioSalvo);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
		
		TokenDTO token = usuarioService.login(loginDTO);
		
		return ResponseEntity.ok(token);
	}

	@PostMapping("/alterarSenha")
	public ResponseEntity<String> alterarSenha(@RequestBody @Valid AlterarSenhaDTO  alterarSenhaDTO) {
		usuarioService.alterarSenhaUsuario(alterarSenhaDTO);
		return  ResponseEntity.ok("Senha alterada!");
	}


	@GetMapping("/listar")
	public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
		return ResponseEntity.ok(usuarioService.listarUsuarios(usuarioLogadoService.getIdEmpresaLogada()));
	}


	@GetMapping("/buscarPorId/{id}")
	public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable(required = true) Long id) {
		return ResponseEntity.ok(usuarioService.buscarUsuarioDtoPorId(id, usuarioLogadoService.getIdEmpresaLogada()));
	}

	@DeleteMapping("/deletarPorId/{id}")
	public ResponseEntity<String> deletarPorId(@PathVariable(required = true) Long id) {
		usuarioService.deleteUsuarioByIdAndEmpresa(id, usuarioLogadoService.getIdEmpresaLogada());
		return ResponseEntity.ok("Usuario deletado com sucesso");
	}

}
