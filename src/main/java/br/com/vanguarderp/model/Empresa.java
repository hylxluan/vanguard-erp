package br.com.vanguarderp.model;

import java.io.Serializable;
import java.time.LocalDate;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "empresa", uniqueConstraints = {
		@UniqueConstraint(name = "unique_pessoa_empresa", columnNames = "pessoa_id"),
})
@SequenceGenerator(name = "seq_empresa", sequenceName = "seq_empresa", 
allocationSize = 1, initialValue = 1)
public class Empresa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plano_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "plano_fk"))
	@NotNull(message = "O plano não pode ser nulo!")
	private Plano plano;
	
	@Column(name = "total_usuarios", nullable = false)
	@NotNull(message = "O total de usuários não pode ser nulo!")
	private Integer totalUsuarios = 0;
	
	@Column(name = "total_clientes", nullable = false)
	@NotNull(message = "O total de clientes não pode ser nulo!")
	private Integer totalCliente = 0;
	
	@Column(name = "plano_ativo", nullable = false)
	private Boolean planoAtivo = true;
	
	@Column(name = "bloqueio", nullable = false)
	private Boolean bloqueio = false;
	
	@NotNull(message = "A logo marca não pode ser nula!")
	@NotEmpty(message = "A logo marca não pode estar vazia!")
	@Column(name = "logo_marca", columnDefinition = "text", nullable = false)
	private String logoMarca;
	
	@Column(name = "vigencia_plano", nullable = false)
	private LocalDate vigenciaPlano;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pessoa_id", 
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;

	public Empresa() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}

	public Integer getTotalUsuarios() {
		return totalUsuarios;
	}

	public void setTotalUsuarios(Integer totalUsuarios) {
		this.totalUsuarios = totalUsuarios;
	}

	public Integer getTotalCliente() {
		return totalCliente;
	}

	public void setTotalCliente(Integer totalCliente) {
		this.totalCliente = totalCliente;
	}

	public Boolean getPlanoAtivo() {
		return planoAtivo;
	}

	public void setPlanoAtivo(Boolean planoAtivo) {
		this.planoAtivo = planoAtivo;
	}

	public Boolean getBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(Boolean bloqueio) {
		this.bloqueio = bloqueio;
	}

	public String getLogoMarca() {
		return logoMarca;
	}

	public void setLogoMarca(String logoMarca) {
		this.logoMarca = logoMarca;
	}

	public LocalDate getVigenciaPlano() {
		return vigenciaPlano;
	}

	public void setVigenciaPlano(LocalDate vigenciaPlano) {
		this.vigenciaPlano = vigenciaPlano;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	
}
