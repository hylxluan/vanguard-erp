package br.com.vanguarderp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaVanguardRepository<T, ID> extends JpaRepository<T, ID> {
	
	Page<T> listarPaginado(Long empresaId, Pageable pageable);
	
	long total(Long empresaId);
	
	Optional<T> buscarPorId(ID id, Long empresaId);
	
	List<T> buscarPorIds(Iterable<ID> ids, Long empresaId);
	
	boolean existePorId(ID id, Long empresaId);
	
	List<T> listar(Long empresaId);
	
	long deletarAllPorIds(Iterable<ID> ids, Long empresaId);
	
	long deletarAll(Long empresaId);
}
