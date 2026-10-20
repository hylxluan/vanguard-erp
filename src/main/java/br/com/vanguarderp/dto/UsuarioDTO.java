package br.com.vanguarderp.dto;

import jakarta.validation.constraints.NotBlank;

public class UsuarioDTO {
    private Long id;
    private String pessoa;
    private String empresa;
    private String tipoClienteFuncionario;
    private boolean liberado = true;

    @NotBlank(message = "Login deve ser informado!")
    private String login;

    @NotBlank(message = "Senha deve ser informada!")
    private String senha;

    private Long clienteFuncionario;

    private Long pessoaId;

    public Long getClienteFuncionario() {
        return clienteFuncionario;
    }

    public void setClienteFuncionario(Long clienteFuncionario) {
        this.clienteFuncionario = clienteFuncionario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTipoClienteFuncionario() {
        return tipoClienteFuncionario;
    }

    public void setTipoClienteFuncionario(String tipoClienteFuncionario) {
        this.tipoClienteFuncionario = tipoClienteFuncionario;
    }

    public boolean isLiberado() {
        return liberado;
    }

    public void setLiberado(boolean liberado) {
        this.liberado = liberado;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }


    public UsuarioDTO() {
    }
    public UsuarioDTO(String login, Long id, String pessoa, String empresa, String tipoClienteFuncionario, boolean liberado, String senha, Long clienteFuncionario, Long pessoaId) {
        this.login = login;
        this.id = id;
        this.pessoa = pessoa;
        this.empresa = empresa;
        this.tipoClienteFuncionario = tipoClienteFuncionario;
        this.liberado = liberado;
        this.senha = senha;
        this.clienteFuncionario = clienteFuncionario;
        this.pessoaId = pessoaId;
    }
}
