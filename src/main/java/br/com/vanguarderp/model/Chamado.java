package br.com.vanguarderp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.vanguarderp.enums.PrioridadeChamado;
import br.com.vanguarderp.enums.StatusChamado;
import br.com.vanguarderp.enums.TipoChamado;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "chamado")
@SequenceGenerator(name = "seq_chamado", sequenceName = "seq_chamado",
allocationSize = 1, initialValue = 1)
public class Chamado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_chamado", strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotBlank(message = "O título deve ser informado!")
	@Column(name = "titulo", nullable = false, length = 150)
	private String titulo;

	@NotBlank(message = "A descrição deve ser informada!")
	@Column(name = "descricao", nullable = false, columnDefinition = "text")
	private String descricao;

	@NotNull(message = "O tipo de chamado deve ser informado!")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_chamado", nullable = false)
	private TipoChamado tipoChamado;

	@NotNull(message = "O status não pode ser nulo!")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private StatusChamado status;

	@NotNull(message = "A prioridade deve ser informada!")
	@Enumerated(EnumType.STRING)
	@Column(name = "prioridade", nullable = false)
	private PrioridadeChamado prioridade;

	@NotNull(message = "A data de abertura não pode ser nula!")
	@Column(name = "data_abertura", nullable = false)
	private LocalDate dataAbertura;

	@Column(name = "data_fechamento")
	private LocalDate dataFechamento;

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

	@NotNull(message = "O usuário de abertura deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_abertura_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "usuario_abertura_fk"))
	private Usuario usuarioAbertura;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_fechamento_id", 
	nullable = true,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "usuario_fechamento_fk"))
	private Usuario usuarioFechamento;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chamado")
	private List<Mensagem> mensagens = new ArrayList<>();
	
	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Empresa empresa;
	
	public Chamado() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoChamado getTipoChamado() {
		return tipoChamado;
	}

	public void setTipoChamado(TipoChamado tipoChamado) {
		this.tipoChamado = tipoChamado;
	}

	public StatusChamado getStatus() {
		return status;
	}

	public void setStatus(StatusChamado status) {
		this.status = status;
	}

	public PrioridadeChamado getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(PrioridadeChamado prioridade) {
		this.prioridade = prioridade;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
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

	public Usuario getUsuarioAbertura() {
		return usuarioAbertura;
	}

	public void setUsuarioAbertura(Usuario usuarioAbertura) {
		this.usuarioAbertura = usuarioAbertura;
	}

	public Usuario getUsuarioFechamento() {
		return usuarioFechamento;
	}

	public void setUsuarioFechamento(Usuario usuarioFechamento) {
		this.usuarioFechamento = usuarioFechamento;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(atendente, cliente, dataAbertura, dataFechamento, descricao, empresa, id, mensagens,
				prioridade, status, tipoChamado, titulo, usuarioAbertura, usuarioFechamento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamado other = (Chamado) obj;
		return Objects.equals(atendente, other.atendente) && Objects.equals(cliente, other.cliente)
				&& Objects.equals(dataAbertura, other.dataAbertura)
				&& Objects.equals(dataFechamento, other.dataFechamento) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(empresa, other.empresa) && Objects.equals(id, other.id)
				&& Objects.equals(mensagens, other.mensagens) && prioridade == other.prioridade
				&& status == other.status && tipoChamado == other.tipoChamado && Objects.equals(titulo, other.titulo)
				&& Objects.equals(usuarioAbertura, other.usuarioAbertura)
				&& Objects.equals(usuarioFechamento, other.usuarioFechamento);
	}
	
	

}