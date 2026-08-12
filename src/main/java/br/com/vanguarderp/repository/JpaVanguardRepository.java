package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaVanguardRepository<T, ID> extends JpaRepository<T, ID> {
	
	Page<T> listarPaginado(Long empresaId, Pageable pageable);
	
	long total(Long empresaId);
}
