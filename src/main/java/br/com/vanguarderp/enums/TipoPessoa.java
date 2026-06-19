package br.com.vanguarderp.enums;

public enum TipoPessoa {
	
	PF("Pessoa Física"),
	PJ("Pessoa Jurídica");
	
	private final String description;
	
	private TipoPessoa(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	
}
