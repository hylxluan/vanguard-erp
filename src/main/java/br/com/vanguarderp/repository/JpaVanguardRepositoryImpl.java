package br.com.vanguarderp.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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
	
	
	
	
	
	@Override
	public Page<T> listarPaginado(Long empresaId, Pageable pageable) {
		String nomeEntidade = domainClass.getSimpleName();
		boolean possuiEmpresa = possuiEmpresa();
		String jpql = "FROM " + nomeEntidade;
		
		if (possuiEmpresa) {
			jpql += " WHERE empresa.id = : empresaId";
		}
		
		if (pageable.getSort().isSorted()) {
			jpql += " order by ";
			
			List<String> orders = new ArrayList<>();
			
			for (Order order : pageable.getSort()) {
				orders.add(order.getProperty() + " " + order.getDirection().name());
			}
			
			jpql += String.join(",", orders);
		}
		
		TypedQuery<T> query = entityManager.createQuery(jpql, domainClass);
		
		if (possuiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
		
		List<T> listaPaginada = query.setFirstResult( (int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize())
				.getResultList();
		
		return new PageImpl<T>(listaPaginada, pageable, total(empresaId));
	}
	
	

	@Override
	public long total(Long empresaId) {
		String nomeEntidade = domainClass.getSimpleName();
		boolean possuiEmpresa = possuiEmpresa();
		String jpql = "SELECT COUNT(*) FROM " + nomeEntidade;
		
		if (possuiEmpresa) {
			jpql += " WHERE empresa.id = : empresaId";
		}
		
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		
		if (possuiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
		
		return query.getSingleResult();
	}

	private boolean possuiEmpresa() {
		try {
			return domainClass.getDeclaredField("empresa") != null;
		} catch (NoSuchFieldException e) {
			return false;
		}
		
	}
	
}
