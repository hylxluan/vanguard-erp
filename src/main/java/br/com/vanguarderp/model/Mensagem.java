package br.com.vanguarderp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "mensagem")
@SequenceGenerator(name = "seq_mensagem", sequenceName = "seq_mensagem",
allocationSize = 1, initialValue = 1)
public class Mensagem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_mensagem", strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull(message = "A data de envio não pode ser nula!")
	@Column(name = "data_envio", nullable = false)
	private LocalDateTime dataEnvio;

	@NotNull(message = "O campo lida não pode ser nulo!")
	@Column(name = "lida", nullable = false)
	private Boolean lida = false;

	@NotBlank(message = "O conteúdo da mensagem não pode ser vazio!")
	@Column(name = "conteudo", nullable = false, columnDefinition = "text")
	private String conteudo;

	@Column(name = "arquivo", columnDefinition = "text")
	private String arquivo;

	@NotNull(message = "O chamado deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chamado_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "chamado_fk"))
	private Chamado chamado;

	@NotNull(message = "O atendente deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "atendente_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "atendente_fk"))
	private Usuario atendente;

	@NotNull(message = "O cliente deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cliente_fk"))
	private Usuario cliente;

	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Empresa empresa;

	public Mensagem() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(LocalDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Boolean getLida() {
		return lida;
	}

	public void setLida(Boolean lida) {
		this.lida = lida;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Usuario getAtendente() {
		return atendente;
	}

	public void setAtendente(Usuario atendente) {
		this.atendente = atendente;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arquivo, atendente, chamado, cliente, conteudo, dataEnvio, empresa, id, lida);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mensagem other = (Mensagem) obj;
		return Objects.equals(arquivo, other.arquivo) && Objects.equals(atendente, other.atendente)
				&& Objects.equals(chamado, other.chamado) && Objects.equals(cliente, other.cliente)
				&& Objects.equals(conteudo, other.conteudo) && Objects.equals(dataEnvio, other.dataEnvio)
				&& Objects.equals(empresa, other.empresa) && Objects.equals(id, other.id)
				&& Objects.equals(lida, other.lida);
	}

	
}