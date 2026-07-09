package br.com.vanguarderp.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "item_pedido")
@SequenceGenerator(name = "seq_item_pedido", sequenceName = "seq_item_pedido",
allocationSize = 1, initialValue = 1)
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_item_pedido", strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull(message = "A quantidade não pode ser nula!")
	@DecimalMin(value = "0.01", message = "A quantidade deve ser maior que zero!")
	@Column(name = "quantidade", nullable = false)
	private Double quantidade;

	@NotNull(message = "O subtotal não pode ser nulo!")
	@Column(name = "sub_total", nullable = false)
	private BigDecimal subTotal = BigDecimal.ZERO;

	@Column(name = "desconto")
	private BigDecimal desconto = BigDecimal.ZERO;

	@NotNull(message = "O total não pode ser nulo!")
	@Column(name = "total", nullable = false)
	private BigDecimal total = BigDecimal.ZERO;

	@NotNull(message = "O produto deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "produto_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
	private Produto produto;

	@NotNull(message = "O pedido deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pedido_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pedido_fk"))
	private Pedido pedido;

	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Empresa empresa;

	public ItemPedido() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
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
		return Objects.hash(desconto, empresa, id, pedido, produto, quantidade, subTotal, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemPedido other = (ItemPedido) obj;
		return Objects.equals(desconto, other.desconto) && Objects.equals(empresa, other.empresa)
				&& Objects.equals(id, other.id) && Objects.equals(pedido, other.pedido)
				&& Objects.equals(produto, other.produto) && Objects.equals(quantidade, other.quantidade)
				&& Objects.equals(subTotal, other.subTotal) && Objects.equals(total, other.total);
	}
	
	
	
}