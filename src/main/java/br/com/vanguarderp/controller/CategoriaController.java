package br.com.vanguarderp.controller;

import br.com.vanguarderp.dto.CategoriaDTO;
import br.com.vanguarderp.service.CategoriaService;
import br.com.vanguarderp.service.UsuarioLogadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategoriasDto(usuarioLogadoService.getIdEmpresaLogada()));
    }

    @GetMapping("/paginado")
    public ResponseEntity<List<CategoriaDTO>> listarPaginado(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "id") String sort,
                                                             @RequestParam(defaultValue = "desc") String direction) {
        List<CategoriaDTO> listaPaginada = categoriaService.listarCategoriasPaginadoDto(
                usuarioLogadoService.getIdEmpresaLogada(), page, size, sort, direction);
        return ResponseEntity.ok(listaPaginada);
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable(required = true) Long id) {
        return ResponseEntity.ok(categoriaService.buscarCategoriaDtoPorId(id, usuarioLogadoService.getIdEmpresaLogada()));
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<CategoriaDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(categoriaService.getCategoriaPorNome(nome, usuarioLogadoService.getIdEmpresaLogada()));
    }

    @GetMapping("/total")
    public ResponseEntity<Long> total() {
        return ResponseEntity.ok(categoriaService.totalCategorias(usuarioLogadoService.getIdEmpresaLogada()));
    }

    @DeleteMapping("/deletarPorId/{id}")
    public ResponseEntity<String> deletarPorId(@PathVariable(required = true) Long id) {
        categoriaService.deleteCategoriaByIdAndEmpresa(id, usuarioLogadoService.getIdEmpresaLogada());
        return ResponseEntity.ok("Categoria deletada com sucesso!");
    }

}
