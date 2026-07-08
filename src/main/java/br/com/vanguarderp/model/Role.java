package br.com.vanguarderp.model;

import java.io.Serializable;
import java.util.Objects;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "role", uniqueConstraints = {
		@UniqueConstraint(columnNames = "acesso", name = "unique_acesso")
})
@SequenceGenerator(name = "seq_role", sequenceName = "seq_role", 
initialValue = 1,
allocationSize = 1)
public class Role implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "seq_role", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "acesso", nullable = false, unique = true)
	@NotBlank(message = "Acesso deve ser informado!")
	private String acesso;
	
	@Override
	public @Nullable String getAuthority() {
		
		return this.acesso;
	}
	
	public Role() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcesso() {
		return acesso;
	}

	public void setAcesso(String acesso) {
		this.acesso = acesso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(acesso, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(acesso, other.acesso) && Objects.equals(id, other.id);
	}

	
	
}
