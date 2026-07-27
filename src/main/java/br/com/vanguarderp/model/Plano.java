package br.com.vanguarderp.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.vanguarderp.enums.TipoPlano;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "plano")
@SequenceGenerator(name = "seq_plano", sequenceName = "seq_plano", 
allocationSize = 1, initialValue = 1)
public class Plano implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_plano")
	private Long id;
	
	@Column(nullable = false, length = 100, name = "nome")
	@NotBlank(message = "O nome deve ser preenchido!")
	private String nome;
	
	@Column(nullable = false, length = 100, name = "descricao")
	@NotBlank(message = "A descrição deve ser informada!")
	private String descricao;
	
	@Column(name = "ativo", nullable = false)
	private Boolean ativo;
	
	@Column(name = "valor_mensal", nullable = false)
	@NotNull(message = "O valor mensal não pode ser nulo!")
	@Min(value = 49, message = "O valor mínimo mensal do plano deve ser de R$49,00 reais!")
	@Max(value = 299, message = "O valor máximo mensal do plano deve ser de R$299,00 reais!")
	private Double valorMensal;
	
	@Column(name = "limite_usuario", nullable = false)
	@NotNull(message = "O limite de usuário não pode ser nulo!")
	@Min(value = 1, message = "O limite mínimo de usuários é 1")
	@Max(value = 150, message = "O limite máximo de usuários é 150")
	private Integer limiteUsuario;
	
	@Column(name = "limite_cliente", nullable = false)
	@NotNull(message = "O limite de cliente não pode ser nulo!")
	@Min(value = 1, message = "O limite mínimo de clientes é 1")
	@Max(value = 150, message = "O limite máximo de clientes é 150")
	private Integer limiteCliente;
	
	@Column(name = "tipo_plano", nullable = false)
	@NotNull(message = "O tipo de plano não pode ser nulo!")
	@Enumerated(EnumType.STRING)
	private TipoPlano tipoPlano;

	public Plano() {
		
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Double getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(Double valorMensal) {
		this.valorMensal = valorMensal;
	}

	public Integer getLimiteUsuario() {
		return limiteUsuario;
	}

	public void setLimiteUsuario(Integer limiteUsuario) {
		this.limiteUsuario = limiteUsuario;
	}

	public Integer getLimiteCliente() {
		return limiteCliente;
	}

	public void setLimiteCliente(Integer limiteCliente) {
		this.limiteCliente = limiteCliente;
	}

	public TipoPlano getTipoPlano() {
		return tipoPlano;
	}

	public void setTipoPlano(TipoPlano tipoPlano) {
		this.tipoPlano = tipoPlano;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ativo, descricao, id, limiteCliente, limiteUsuario, nome, tipoPlano, valorMensal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plano other = (Plano) obj;
		return Objects.equals(ativo, other.ativo) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(id, other.id) && Objects.equals(limiteCliente, other.limiteCliente)
				&& Objects.equals(limiteUsuario, other.limiteUsuario) && Objects.equals(nome, other.nome)
				&& tipoPlano == other.tipoPlano && Objects.equals(valorMensal, other.valorMensal);
	}
	
	
	
}
