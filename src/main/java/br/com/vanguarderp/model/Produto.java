package br.com.vanguarderp.model;

import java.io.Serializable;

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
	
}
