package br.com.vanguarderp.enums;

public enum UnidadeMedida {

	CENTIMETRO("Centímetro"),
	METRO("Metro"),
	UNIDADE("Unidade"),
	QUILO("Quilos");
	
	private final String description;

	
	
	private UnidadeMedida(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
