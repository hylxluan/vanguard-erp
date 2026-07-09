package br.com.vanguarderp.enums;

public enum StatusPedido {
	PENDENTE("Pendente"),
	CONFIRMADO("Confirmado"),
	EM_SEPARACAO("Em separação"),
	ENVIADO("Enviado"),
	ENTREGUE("Entregue"),
	CANCELADO("Cancelado");
	
	private final String descricao;

	public String getDescricao() {
		return descricao;
	}

	private StatusPedido(String descricao) {
		this.descricao = descricao;
	}
	
	
}
