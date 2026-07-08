package br.com.vanguarderp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "usuario", uniqueConstraints = {
		@UniqueConstraint(columnNames = "login", name = "unique_login"),
		@UniqueConstraint(columnNames = "cliente_funcionario_id", name = "unique_cliente_funcionario")
})
@SequenceGenerator(sequenceName = "seq_usuario", name = "seq_usuario", 
allocationSize = 1, initialValue = 1)
public class Usuario implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	private Long id;
	
	@NotBlank(message = "Login deve ser informado!")
	@Column(name = "login", nullable = false, unique = true)
	private String login;
	
	@NotBlank(message = "Senha deve ser informada!")
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@NotNull(message = "Bloqueio não pode ser nulo!")
	@Column(name = "bloqueio", nullable = false)
	private Boolean bloqueio = false;
	
	private String accessToken;
	private String refreshToken;
	
	@NotNull(message = "Cliente ou Funcionário deve ser informado para cadastrar o usuário de acesso no sistema!")
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_funcionario_id", 
	foreignKey = @ForeignKey(
			value = ConstraintMode.CONSTRAINT, name = "cliente_funcionario_fk"),
	nullable = false)
	private ClienteFuncionario clienteFuncionario;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_usuario", 
		joinColumns = @JoinColumn(name = "usuario_id",
					  foreignKey = @ForeignKey(name = "usuario_fk")),
		inverseJoinColumns = @JoinColumn(name = "acesso_id", 
							 foreignKey = @ForeignKey(name = "acesso_fk")),
		uniqueConstraints = @UniqueConstraint(name = "unique_role_usuario", 
		columnNames = {"usuario_id", "acesso_id"}))
	private List<Role> acessos = new ArrayList<>();
	
	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", 
	nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Empresa empresa;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.acessos;
	}

	@Override
	public @Nullable String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	
	public Usuario() {

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(Boolean bloqueio) {
		this.bloqueio = bloqueio;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public List<Role> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Role> acessos) {
		this.acessos = acessos;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public ClienteFuncionario getClienteFuncionario() {
		return clienteFuncionario;
	}

	public void setClienteFuncionario(ClienteFuncionario clienteFuncionario) {
		this.clienteFuncionario = clienteFuncionario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessToken, acessos, bloqueio, clienteFuncionario, empresa, id, login, refreshToken,
				senha);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(accessToken, other.accessToken) && Objects.equals(acessos, other.acessos)
				&& Objects.equals(bloqueio, other.bloqueio)
				&& Objects.equals(clienteFuncionario, other.clienteFuncionario)
				&& Objects.equals(empresa, other.empresa) && Objects.equals(id, other.id)
				&& Objects.equals(login, other.login) && Objects.equals(refreshToken, other.refreshToken)
				&& Objects.equals(senha, other.senha);
	}
	
	
	
}
