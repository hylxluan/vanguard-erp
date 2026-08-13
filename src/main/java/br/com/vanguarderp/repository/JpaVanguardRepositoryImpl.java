package br.com.vanguarderp.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class JpaVanguardRepositoryImpl<T, ID extends Serializable> 
	extends SimpleJpaRepository<T, ID> 
	implements JpaVanguardRepository<T, ID> {
	
	private final Class<T> domainClass;
	private final EntityManager entityManager;
	private final boolean isMultiEmpresa;
	
	public JpaVanguardRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		
		this.domainClass = domainClass;
		this.entityManager = entityManager;
		this.isMultiEmpresa = possuiEmpresa();
	}

	public JpaVanguardRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.domainClass = entityInformation.getJavaType();
		this.entityManager = entityManager;
		this.isMultiEmpresa = possuiEmpresa();
    }
	
	
	
	
	
	@Override
	public Page<T> listarPaginado(Long empresaId, Pageable pageable) {
		String nomeEntidade = domainClass.getSimpleName();
		String jpql = "FROM " + nomeEntidade 
				+ (isMultiEmpresa ? " e WHERE e.empresa.id = :empresaId" : "");

		if (pageable.getSort().isSorted()) {
			jpql += " ORDER BY ";
			
			List<String> orders = new ArrayList<>();
			
			for (Order order : pageable.getSort()) {
				orders.add(order.getProperty() + " " + order.getDirection().name());
			}
			
			jpql += String.join(",", orders);
		}
		
		TypedQuery<T> query = entityManager.createQuery(jpql, domainClass);
		
		if (isMultiEmpresa) {
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
		String jpql = "SELECT COUNT(*) FROM " + nomeEntidade 
				+ (isMultiEmpresa ? " e WHERE e.empresa.id = :empresaId" : "");
		
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		
		if (isMultiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
		
		return query.getSingleResult();
	}

	
	
	@Override
	public Optional<T> buscarPorId(ID id, Long empresaId) {
		String nomeEntidade = domainClass.getSimpleName();
		String jpql = "FROM " + nomeEntidade + " e WHERE e.id = :id"
				+ (isMultiEmpresa ? " AND e.empresa.id = :empresaId" : "");
		
		TypedQuery<T> query = entityManager.createQuery(jpql, domainClass);
		
		if (isMultiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
		
		return query.getResultStream().findFirst();
	}

	@Override
	public List<T> buscarPorIds(Iterable<ID> ids, Long empresaId) {
		String nomeEntidade = domainClass.getSimpleName();
		
		List<ID> listaIds = new ArrayList<>();
		ids.forEach(listaIds::add);
		
		if (listaIds.isEmpty()) {
			return new ArrayList<>();
		}
		
		String jpql = "FROM " + nomeEntidade + " e WHERE e.id IN :ids";
		
		if (isMultiEmpresa) {
	        jpql += " AND e.empresa.id = :empresaId";
	    }

		TypedQuery<T> query = entityManager.createQuery(jpql, domainClass);
		query.setParameter("ids", listaIds);
		
		if (isMultiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
				
		return query.getResultList();
	}

	@Override
	public boolean existePorId(ID id, Long empresaId) {
		String nomeEntidade = domainClass.getSimpleName();
		String jpql = "SELECT 1 FROM " + nomeEntidade + " e WHERE e.id = :id"
	            + (isMultiEmpresa ? " AND e.empresa.id = :empresaId" : "");
		
		TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
		query.setParameter("id", id);
		
		if (isMultiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
		
		query.setMaxResults(1);
		
		return !query.getResultList().isEmpty();
	}

	@Override
	public List<T> listar(Long empresaId) {
		String nomeEntidade = domainClass.getSimpleName();
		String jpql = "FROM " + nomeEntidade + (isMultiEmpresa ? " e WHERE e.empresa.id = :empresaId" : "");
		
		TypedQuery<T> query = entityManager.createQuery(jpql, domainClass);
		
		if (isMultiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
		
		return query.getResultList();
	}

	@Override
	public long deletarAllPorIds(Iterable<ID> ids, Long empresaId) {
		String nomeEntidade = domainClass.getSimpleName();
		
		List<ID> listaIds = new ArrayList<>();
		ids.forEach(listaIds::add);
		
		if (listaIds.isEmpty()) {
			return 0;
		}
		
		String jpql = "DELETE FROM " + nomeEntidade + " e WHERE e.id IN :ids";
		
		if (isMultiEmpresa) {
	        jpql += " AND e.empresa.id = :empresaId";
	    }
		
		Query query = entityManager.createQuery(jpql); 
		query.setParameter("ids", listaIds);
		
		if (isMultiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
		
		return query.executeUpdate();
		
	}

	@Override
	public long deletarAll(Long empresaId) {
		String nomeEntidade = domainClass.getSimpleName();
		String jpql = "DELETE FROM " + nomeEntidade
				+ (isMultiEmpresa ? " e WHERE e.empresa.id = :empresaId" : "");
		
		Query query = entityManager.createQuery(jpql);
		
		if (isMultiEmpresa) {
			query.setParameter("empresaId", empresaId);
		}
		
		return query.executeUpdate();
	}

	private boolean possuiEmpresa() {
		try {
			return domainClass.getDeclaredField("empresa") != null;
		} catch (NoSuchFieldException e) {
			return false;
		}
		
	}
	
}
