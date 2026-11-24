package br.com.vanguarderp.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaDTO {
    private Long id;

    @NotBlank(message = "Nome da categoria deve ser informado!")
    private String nome;

    private Long empresaId;

    public CategoriaDTO() {  }

    public CategoriaDTO(Long id, String nome, Long empresaId) {
        this.id = id;
        this.nome = nome;
        this.empresaId = empresaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}
