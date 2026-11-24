package br.com.vanguarderp.controller;


import br.com.vanguarderp.dto.CategoriaDTO;
import br.com.vanguarderp.service.CategoriaService;
import br.com.vanguarderp.service.UsuarioLogadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioLogadoService usuarioLogadoService;


    @PostMapping("/cadastrar")
    public ResponseEntity<CategoriaDTO> cadastrarCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO) {

        CategoriaDTO categoriaResponse = categoriaService.salvarCategoria(categoriaDTO);

        return ResponseEntity.ok(categoriaResponse);
    }

    @PostMapping("/atualizar")
    public ResponseEntity<CategoriaDTO> atualizarCategoria(@RequestBody @Valid CategoriaDTO categoriaDTO) {

        CategoriaDTO categoriaResponse = categoriaService.atualizarCategoria(categoriaDTO);

        return ResponseEntity.ok(categoriaResponse);
    }

}
