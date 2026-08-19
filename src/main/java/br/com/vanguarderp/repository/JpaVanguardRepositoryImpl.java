package br.com.vanguarderp.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class JpaVanguardRepositoryImpl<T, ID extends Serializable> 
	extends SimpleJpaRepository<T, ID> 
	implements JpaVanguardRepository<T, ID> {
	
	private final Class<T> domainClass;
	private final EntityManager entityManager;
	private final boolean isMultiEmpresa;
	private static final String MSG_BLOQUEIO_QUERY = 
			"Use ou crie um método que tenha o empresa.id incluída para a separação dos dados por empresa e ativar o multitanent.";
	
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
	
	@Override
	public List<T> findAll() {
		validar("findAll");
		return super.findAll();
	}
	
	@Override
	public List<T> findAll(Sort sort) {
		validar("findAll");
		return super.findAll(sort);
	}
	
	@Override
	public Page<T> findAll(Pageable pageable) {
		validar("findAll");
		return super.findAll(pageable);
	}
	
	@Override
	public <S extends T> List<S> findAll(Example<S> example) {
		validar("findAll");
		return super.findAll(example);
	}
	
	
	@Override
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		validar("findAll");
		return super.findAll(example, pageable);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		validar("findAll");
		return super.findAll(example, sort);
	}
	
	
	@Override
	public List<T> findAll(PredicateSpecification<T> spec) {
		validar("findAll");
		return super.findAll(spec);
	}
	
	
	@Override
	public List<T> findAll(Specification<T> spec) {
		validar("findAll");
		return super.findAll(spec);
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		validar("findAll");
		return super.findAll(spec, pageable);
	}

	@Override
	public List<T> findAll(Specification<T> spec, Sort sort) {
		validar("findAll");
		return super.findAll(spec, sort);
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Specification<T> countSpec, Pageable pageable) {
		validar("findAll");
		return super.findAll(spec, countSpec, pageable);
	}

	@Override
	public Optional<T> findById(ID id) {
		validar("findById");
		return super.findById(id);
	}

	@Override
	public List<T> findAllById(Iterable<ID> ids) {
		validar("findAllById");
		return super.findAllById(ids);
	}
	
	
	@Override
	public T getReferenceById(ID id) {
		validar("getReferenceById");
		return super.getReferenceById(id);
	}
	
	@Override
	public boolean existsById(ID id) {
		validar("existsById");
		return super.existsById(id);
	}
	
	@Override
	public long count() {
		validar("count");
		return super.count();
	}

	@Override
	public <S extends T> long count(Example<S> example) {
		validar("count");
		return super.count(example);
	}

	@Override
	public long count(PredicateSpecification<T> spec) {
		validar("count");
		return super.count(spec);
	}

	@Override
	public long count(Specification<T> spec) {
		validar("count");
		return super.count(spec);
	}
	
	
	@Override
	public void delete(T entity) {
		validar("delete");
		super.delete(entity);
	}

	@Override
	public void deleteAll() {
		validar("delete");
		super.deleteAll();
	}
	
	@Override
	public void deleteById(ID id) {
		validar("deleteById");
		super.deleteById(id);
	}
	
	@Override
	public <S extends T> Optional<S> findOne(Example<S> example) {
		validar("findOne");
		return super.findOne(example);
	}
	
	@Override
	public <S extends T> boolean exists(Example<S> example) {
		validar("exists");
		return super.exists(example);
	}
	
	@Override
	public void deleteAllInBatch() {
		validar("deleteAllInBatch");
		super.deleteAllInBatch();
	}
	
	
	
	@Override
	public <S extends T, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		validar("findBy");
		return super.findBy(example, queryFunction);
	}

	private void validar(String metodo) {
		if (isMultiEmpresa) {
			throw new UnsupportedOperationException("o método: " + metodo + " não pode ser usado. " + MSG_BLOQUEIO_QUERY);
		}
	}
	
}
