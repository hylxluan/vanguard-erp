package br.com.vanguarderp.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.core.support.RepositoryFactoryInformation;
import org.springframework.stereotype.Component;

import br.com.vanguarderp.annotations.IgnoreEmpresaId;

@Component
public class RepositoryIgnoreEmpresaValidator implements SmartInitializingSingleton {

	private final ApplicationContext applicationContext;
	
	public RepositoryIgnoreEmpresaValidator(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	private List<Class<?>> getRepositories() {
		List<Class<?>> listaRepositories = new ArrayList<>();
		
		applicationContext.getBeansOfType(RepositoryFactoryInformation.class).values()
			.forEach(info -> listaRepositories
					.add(info.getRepositoryInformation().getRepositoryInterface()));
		
		return listaRepositories;
	}

	@Override
	public void afterSingletonsInstantiated() {
		
		for (Class<?> interfaceRepository : getRepositories()) {
			
			if (interfaceRepository.isAnnotationPresent(IgnoreEmpresaId.class)) {
				continue;
			}
			
			for (Method method : interfaceRepository.getMethods()) {
				
				if (method.isAnnotationPresent(IgnoreEmpresaId.class)) {
					continue;
				}
				
				if (!method.getDeclaringClass().equals(interfaceRepository)) {
					continue;
				}
				
				boolean queryPresent = method.isAnnotationPresent(Query.class);
				
				if (!queryPresent) {
					throw new IllegalStateException("O método: "+ method 
                            + " da interface: "+ interfaceRepository 
                            + " deve possuir Query escrita.");
				}
				
				Query query = method.getAnnotation(Query.class);
				
				String sqlQuery = query.value().toLowerCase();
				
				if (sqlQuery.contains("empresa.id") || sqlQuery.contains("empresa_id")) {
					continue;
				}
				
				throw new IllegalStateException("""

						====================================================================
						ERRO DE SEGURANÇA

						Repository: %s
						Método: %s

						A consulta abaixo NÃO possui filtro por empresa.

						%s

						Toda consulta deve possuir:

						empresa.id

						ou

						empresa_id

						Caso esta consulta realmente não necessite
						do filtro, utilize:

						@IgnoreEmpresaId

						Essa anotação pode ser usada para o Repository completo ou para métodos únicos.
						====================================================================

						""".formatted(interfaceRepository.getSimpleName(), method.getName(), query.value()));
			}
		}
		
		
	}
	
}
