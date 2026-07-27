package br.com.vanguarderp.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;

public class JpaVanguardRepositoryImpl<T, ID extends Serializable> 
	extends SimpleJpaRepository<T, ID> 
	implements JpaVanguardRepository<T, ID> {
	
	
	private final Class<T> domainClass;
	private final EntityManager entityManager;
	
	public JpaVanguardRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		
		this.domainClass = domainClass;
		this.entityManager = entityManager;
	}

	
	
	public JpaVanguardRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.domainClass = entityInformation.getJavaType();
		this.entityManager = entityManager;
    }
	
	private boolean possuiEmpresa() {
		try {
			return domainClass.getDeclaredField("empresa") != null;
		} catch (NoSuchFieldException e) {
			return false;
		}
		
	}
	
}
