package br.com.vanguarderp.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.vanguarderp.enums.UnidadeMedida;
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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "produto", uniqueConstraints = {
		@UniqueConstraint(name = "unique_sku", columnNames = "sku"),
})
@SequenceGenerator(sequenceName = "seq_produto", name = "seq_produto", 
allocationSize = 1, initialValue = 1)
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;
	
	@NotBlank(message = "Nome do produto não pode ser nulo")
	@Column(name = "nome", length = 150, nullable = false)
	private String nome;
	
	@Column(name = "descricao", length = 150)
	private String descricao;
	
	@NotBlank(message = "Imagem do produto deve ser informado!")
	@Column(name = "imagem", nullable = false, columnDefinition = "text")
	private String imagem;
	
	@NotNull(message = "Unidade de medida do produto não pode ser nula!")
	@Enumerated(EnumType.STRING)
	@Column(name = "unidade_medida", nullable = false)
	private UnidadeMedida unidadeMedida;
	
	@NotNull(message = "Preço do produto não pode ser nulo!")
	@Column(name = "preco", nullable = false)
	private Double preco;
	
	@NotNull(message = "Estoque do produto não pode ser nulo!")
	@Column(name = "estoque", nullable = false)
	private Double estoque;
	
	@NotNull(message = "Estoque mínimo do produto não pode ser nulo!")
	@Column(name = "estoque_min", nullable = false)
	@DecimalMin(value = "1.0", message = "Deve ter pelo menos 1 produto no estoque!")
	private Double estoqueMin;
	
	@Column(name = "sku", unique = true)
	private String sku;
	
	@NotBlank(message = "Código de barra não pode ser nulo!")
	@Column(name = "codigo_barra", nullable = false)
	private String codigoBarra;
	
	
	@NotNull(message = "Categoria do produto não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id", 
	nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "categoria_fk"))
	private Categoria categoria;
	
	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", 
	nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Empresa empresa;

	
	public Produto() {

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

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getEstoque() {
		return estoque;
	}

	public void setEstoque(Double estoque) {
		this.estoque = estoque;
	}

	public Double getEstoqueMin() {
		return estoqueMin;
	}

	public void setEstoqueMin(Double estoqueMin) {
		this.estoqueMin = estoqueMin;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, codigoBarra, descricao, empresa, estoque, estoqueMin, id, imagem, nome, preco,
				sku, unidadeMedida);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(codigoBarra, other.codigoBarra)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(empresa, other.empresa)
				&& Objects.equals(estoque, other.estoque) && Objects.equals(estoqueMin, other.estoqueMin)
				&& Objects.equals(id, other.id) && Objects.equals(imagem, other.imagem)
				&& Objects.equals(nome, other.nome) && Objects.equals(preco, other.preco)
				&& Objects.equals(sku, other.sku) && unidadeMedida == other.unidadeMedida;
	}
	
	
	
}
