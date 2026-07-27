package br.com.vanguarderp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaVanguardRepository<T, ID> extends JpaRepository<T, ID> {
	
	
	
}
