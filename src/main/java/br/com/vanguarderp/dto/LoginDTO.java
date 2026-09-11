package br.com.vanguarderp.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
	
	@NotBlank(message = "Login não pode ser vazio")
	private String login;
	
	@NotBlank(message = "Senha não pode ser vazia")
	private String senha;
	
	public LoginDTO() {	}
	
	public LoginDTO(String login, String senha) {
		this.login = login;
		this.senha = senha;
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
}
