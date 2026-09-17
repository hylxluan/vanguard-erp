package br.com.vanguarderp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AlterarSenhaDTO {

	@NotNull(message = "Senha atual deve ser informada!")
	private Long idUsuario;
	
	@NotBlank(message = "Senha atual deve ser informada!")
	@Size(min = 6, message = "Senha atual deve ter no mínimo 6 caracteres e máximo de 60 caracteres!", max = 60)
	private String senhaAtual;
	
	@NotBlank(message = "Nova senha deve ser informada!")
	@Size(min = 6, message = "Nova senha deve ter no mínimo 6 caracteres e máximo de 60 caracteres!", max = 60)
	private String novaSenha;
	
	@NotBlank(message = "Confirmação da nova senha deve ser informada!")
	@Size(min = 6, message = "Confirmação de senha deve ter no mínimo 6 caracteres e máximo de 60 caracteres!", max = 60)
	private String confirmarNovaSenha;
	
	public AlterarSenhaDTO() {}
	
	public AlterarSenhaDTO(Long idUsuario, String senhaAtual, String novaSenha, String confirmarNovaSenha) {
		this.idUsuario = idUsuario;
		this.senhaAtual = senhaAtual;
		this.novaSenha = novaSenha;
		this.confirmarNovaSenha = confirmarNovaSenha;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmarNovaSenha() {
		return confirmarNovaSenha;
	}

	public void setConfirmarNovaSenha(String confirmarNovaSenha) {
		this.confirmarNovaSenha = confirmarNovaSenha;
	}
	
	
	
	
}
