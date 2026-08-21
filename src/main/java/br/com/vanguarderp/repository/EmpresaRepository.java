package br.com.vanguarderp.repository;

import org.springframework.stereotype.Repository;

import br.com.vanguarderp.annotations.IgnoreEmpresaId;
import br.com.vanguarderp.model.Empresa;

@Repository
@IgnoreEmpresaId
public interface EmpresaRepository extends JpaVanguardRepository<Empresa, Long> {

}
