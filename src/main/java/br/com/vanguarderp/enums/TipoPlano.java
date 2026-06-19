package br.com.vanguarderp.enums;

public enum TipoPlano {
	
	FREE("Plano Gratuito"),
	STARTER("Plano Starter"),
	PRO("Plano Profissional"),
	BUSINESS("Plano Empresarial"),
	ENTERPRISE("Plano Corporativo");
	
	private final String description;

	private TipoPlano(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
