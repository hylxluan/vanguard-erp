package br.com.vanguarderp.model;

import java.io.Serializable;
import java.util.Objects;

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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "role_usuario", uniqueConstraints = {
		@UniqueConstraint(name = "unique_role_usuario", 
				columnNames = {"acesso_id", "usuario_id"}),
})
@SequenceGenerator(name = "seq_role_usuario", sequenceName = "seq_role_usuario", 
initialValue = 1,
allocationSize = 1)
public class RoleUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_role_usuario", strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotNull(message = "Acesso deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acesso_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "acesso_fk"))
	private Role acesso;
	
	@NotNull(message = "Usuário deve ser informado")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "usuario_fk"))
	private Usuario usuario;
	
	public RoleUsuario() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getAcesso() {
		return acesso;
	}

	public void setAcesso(Role acesso) {
		this.acesso = acesso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acesso, id, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleUsuario other = (RoleUsuario) obj;
		return Objects.equals(acesso, other.acesso) && Objects.equals(id, other.id)
				&& Objects.equals(usuario, other.usuario);
	}
	
	
	
}
