package br.com.vanguarderp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import br.com.vanguarderp.enums.TipoMovimentacaoProduto;
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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "movimentacao_produto")
@SequenceGenerator(name = "seq_movimentacao_produto", sequenceName = "seq_movimentacao_produto",
allocationSize = 1, initialValue = 1)
public class MovimentacaoProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_movimentacao_produto", strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull(message = "O valor/preço não pode ser nulo!")
	@Column(name = "valor_preco", nullable = false)
	private BigDecimal valorPreco = BigDecimal.ZERO;

	@NotNull(message = "O tipo de movimentação deve ser informado!")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_movimentacao_produto", nullable = false)
	private TipoMovimentacaoProduto tipoMovimentacaoProduto;

	@NotNull(message = "A quantidade não pode ser nula!")
	@DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero!")
	@Column(name = "quantidade", nullable = false)
	private Double quantidade = 1.0;

	@NotNull(message = "A data do movimento não pode ser nula!")
	@Column(name = "data_movimento", nullable = false)
	private LocalDate dataMovimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "produto_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
	private Produto produto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id", nullable = true,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pedido_fk"))
	private Pedido pedido;

	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Empresa empresa;

	public MovimentacaoProduto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorPreco() {
		return valorPreco;
	}

	public void setValorPreco(BigDecimal valorPreco) {
		this.valorPreco = valorPreco;
	}

	public TipoMovimentacaoProduto getTipoMovimentacaoProduto() {
		return tipoMovimentacaoProduto;
	}

	public void setTipoMovimentacaoProduto(TipoMovimentacaoProduto tipoMovimentacaoProduto) {
		this.tipoMovimentacaoProduto = tipoMovimentacaoProduto;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDate getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(LocalDate dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataMovimento, empresa, id, pedido, produto, quantidade, tipoMovimentacaoProduto,
				valorPreco);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimentacaoProduto other = (MovimentacaoProduto) obj;
		return Objects.equals(dataMovimento, other.dataMovimento) && Objects.equals(empresa, other.empresa)
				&& Objects.equals(id, other.id) && Objects.equals(pedido, other.pedido)
				&& Objects.equals(produto, other.produto) && Objects.equals(quantidade, other.quantidade)
				&& tipoMovimentacaoProduto == other.tipoMovimentacaoProduto
				&& Objects.equals(valorPreco, other.valorPreco);
	}

	
}