package br.com.vanguarderp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.vanguarderp.enums.FormaPagamento;
import br.com.vanguarderp.enums.StatusPedido;
import br.com.vanguarderp.enums.TipoPedido;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pedido", uniqueConstraints = {
		@UniqueConstraint(name = "unique_numero_pedido", columnNames = "numero_pedido")
})
@SequenceGenerator(name = "seq_pedido", sequenceName = "seq_pedido",
allocationSize = 1, initialValue = 1)
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_pedido", strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotBlank(message = "O número do pedido deve ser informado!")
	@Column(name = "numero_pedido", nullable = false, unique = true)
	private String numeroPedido;

	@NotNull(message = "O status não pode ser nulo!")
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private StatusPedido status;
	
	@NotNull(message = "O tipo do pedido não pode ser nulo!")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pedido", nullable = false)
	private TipoPedido tipoPedido;

	@NotNull(message = "A data do pedido não pode ser nula!")
	@Column(name = "data_pedido", nullable = false)
	private LocalDate dataPedido;

	@NotNull(message = "A forma de pagamento deve ser informada!")
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_pagamento", nullable = false)
	private FormaPagamento formaPagamento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@Column(name = "data_cancelamento")
	private LocalDate dataCancelamento;

	@NotNull(message = "O subtotal não pode ser nulo!")
	@Column(name = "sub_total", nullable = false)
	private BigDecimal subTotal = BigDecimal.ZERO;

	@Column(name = "desconto")
	private BigDecimal desconto = BigDecimal.ZERO;

	@Column(name = "frete")
	private BigDecimal frete = BigDecimal.ZERO;

	@Column(name = "taxas")
	private BigDecimal taxas = BigDecimal.ZERO;
	
	@NotNull(message = "O total não pode ser nulo!")
	@Column(name = "total", nullable = false)
	private BigDecimal total = BigDecimal.ZERO;

	@Column(name = "observacao", columnDefinition = "text")
	private String observacao;

	@NotNull(message = "O vendedor deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vendedor_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "vendedor_fk"))
	private Usuario vendedor;

	@NotNull(message = "O cliente deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cliente_fk"))
	private Usuario cliente;

	@NotNull(message = "A empresa não pode ser nula!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private Empresa empresa;

	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
	private List<ItemPedido> itens = new ArrayList<>();

	public Pedido() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public TipoPedido getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(TipoPedido tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public LocalDate getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(LocalDate dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
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

	public BigDecimal getFrete() {
		return frete;
	}

	public void setFrete(BigDecimal frete) {
		this.frete = frete;
	}

	public BigDecimal getTaxas() {
		return taxas;
	}

	public void setTaxas(BigDecimal taxas) {
		this.taxas = taxas;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, dataCancelamento, dataPagamento, dataPedido, desconto, empresa, formaPagamento,
				frete, id, itens, numeroPedido, observacao, status, subTotal, taxas, tipoPedido, total, vendedor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(dataCancelamento, other.dataCancelamento)
				&& Objects.equals(dataPagamento, other.dataPagamento) && Objects.equals(dataPedido, other.dataPedido)
				&& Objects.equals(desconto, other.desconto) && Objects.equals(empresa, other.empresa)
				&& formaPagamento == other.formaPagamento && Objects.equals(frete, other.frete)
				&& Objects.equals(id, other.id) && Objects.equals(itens, other.itens)
				&& Objects.equals(numeroPedido, other.numeroPedido) && Objects.equals(observacao, other.observacao)
				&& status == other.status && Objects.equals(subTotal, other.subTotal)
				&& Objects.equals(taxas, other.taxas) && tipoPedido == other.tipoPedido
				&& Objects.equals(total, other.total) && Objects.equals(vendedor, other.vendedor);
	}
	
	

}