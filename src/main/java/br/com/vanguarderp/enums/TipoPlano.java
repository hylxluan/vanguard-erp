package br.com.vanguarderp.enums;

public enum TipoPlano {
	FREE("Plano Gratuito"),
	PRO("Plano Profissional"),
	ENTERPRISE("Plano Empresarial");
	
	private final String description;

	private TipoPlano(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
