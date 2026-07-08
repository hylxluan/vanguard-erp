package br.com.vanguarderp.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.vanguarderp.enums.TipoClienteFuncionario;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cliente_funcionario", uniqueConstraints = {
		@UniqueConstraint(name = "unique_pessoa_usuario", 
				columnNames = {"pessoa_id", "usuario_id"}),
		@UniqueConstraint(name = "unique_usuario", columnNames = "usuario_id"),
		@UniqueConstraint(name = "unique_pessoa", columnNames = "pessoa_id")
})
@SequenceGenerator(name = "seq_cliente_funcionario", sequenceName = "seq_cliente_funcionario", 
initialValue = 1,
allocationSize = 1)
public class ClienteFuncionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_cliente_funcionario", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotNull(message = "Informe o tipo de relação da pessoa!")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_cliente_funcionario", nullable = false)
	private TipoClienteFuncionario tipoClienteFuncionario;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pessoa_id", 
			nullable = false,
			foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;
	
	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", 
	nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Empresa empresa;
	
	@NotNull(message = "Usuário deve ser informado")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "usuario_fk"))
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoClienteFuncionario getTipoClienteFuncionario() {
		return tipoClienteFuncionario;
	}

	public void setTipoClienteFuncionario(TipoClienteFuncionario tipoClienteFuncionario) {
		this.tipoClienteFuncionario = tipoClienteFuncionario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(empresa, id, pessoa, tipoClienteFuncionario, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteFuncionario other = (ClienteFuncionario) obj;
		return Objects.equals(empresa, other.empresa) && Objects.equals(id, other.id)
				&& Objects.equals(pessoa, other.pessoa) && tipoClienteFuncionario == other.tipoClienteFuncionario
				&& Objects.equals(usuario, other.usuario);
	}
	
	
}
