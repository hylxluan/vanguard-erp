package br.com.vanguarderp.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CNPJ.Format;
import org.hibernate.validator.constraints.br.CPF;

import br.com.vanguarderp.enums.TipoPessoa;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pessoa", uniqueConstraints = {
		@UniqueConstraint(name = "unique_inscricao_estadual_pessoa", columnNames = "inscricao_estadual"),
		@UniqueConstraint(name = "unique_cnpj_pessoa", columnNames = "cnpj"),
		@UniqueConstraint(name = "unique_cpf_pessoa", columnNames = "cpf"),
		@UniqueConstraint(name = "unique_email_pessoa", columnNames = "email"),
})
@SequenceGenerator(name = "seq_pessoa", sequenceName = "seq_pessoa",
allocationSize = 1, initialValue = 1)
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
	private Long id;

	@Column(name = "nome", length = 150, nullable = false)
	private String nome;

	@Column(name = "razao_social", length = 150)
	private String razaoSocial;

	@Column(name = "nome_fantasia", length = 150)
	private String nomeFantasia;

	@Column(name = "inscricao_estadual", length = 20, unique = true)
	private String inscricaoEstadual;

	@CNPJ(format = Format.ALPHANUMERIC, message = "Informe o CNPJ corretamente!")
	@Column(name = "cnpj", length = 20, unique = true)
	private String cnpj;

	@NotBlank(message = "Número de telefone deve ser informado!")
	@Column(name = "telefone", length = 30, nullable = false)
	private String telefone;

	@CPF(message = "Informe o CPF corretamente!")
	@Column(name = "cpf", length = 20, nullable = false)
	private String cpf;

	@Email(message = "Informe o Email corretamente!")
	@Column(name = "email", length = 150, nullable = false)
	private String email;

	@Column(name = "tipo_pessoa", nullable = false)
	@NotNull(message = "O tipo de pessoa não pode ser nulo!")
	@Enumerated(EnumType.STRING)
	private TipoPessoa tipoPessoa;

	@Column(name = "ativo", nullable = false)
	@NotNull(message = "O status ativo não pode ser nulo!")
	private Boolean ativo = true;

	@Column(name = "data_cadastro", nullable = false, updatable = false)
	@NotNull(message = "A data de cadastro não pode ser nula!")
	private LocalDate dataCadastro = LocalDate.now();

	@Column(name = "observacao", columnDefinition = "text")
	private String observacao;

	@NotBlank(message = "O CEP deve ser informado!")
	@Column(name = "cep", length = 20)
	private String cep;

	@NotBlank(message = "O nome da rua deve ser informado!")
	@Column(name = "logradouro", length = 200, nullable = false)
	private String logradouro;

	@NotBlank(message = "O bairro deve ser informado!")
	@Column(name = "bairro", length = 200, nullable = false)
	private String bairro;

	@NotBlank(message = "O estado deve ser informado!")
	@Column(name = "estado", length = 50, nullable = false)
	private String estado;

	@NotBlank(message = "A cidade deve ser informado!")
	@Column(name = "cidade", length = 200, nullable = false)
	private String cidade;

	@NotBlank(message = "O pais deve ser informado!")
	@Column(name = "pais", length = 60, nullable = false)
	private String pais;

	@Column(name = "complemento", length = 250)
	private String complemento;

	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", 
	nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_empresa_fk"))
	private Empresa empresa;

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

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}