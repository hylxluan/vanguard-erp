package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.model.Pedido;
import jakarta.transaction.Transactional;

@Repository
public interface PedidoRepository extends JpaVanguardRepository<Pedido, Long> {

    @Query("select p from Pedido p where p.empresa.id = :idEmpresa")
    List<Pedido> findAll(@Param("idEmpresa") Long idEmpresa);

    @Query("select p from Pedido p where p.empresa.id = :idEmpresa "
                                + " and unaccent(upper(trim(p.numeroPedido))) "
                                + " like unaccent(upper(concat('%', trim(:numeroPedido) ,'%')))" )
    List<Pedido> buscaPorNumeroPedido(@Param("numeroPedido") String numeroPedido, @Param("idEmpresa") Long idEmpresa);
    
    @Query("select count(p.id) > 0 from Pedido p where p.empresa.id = :idEmpresa "
            + " and unaccent(upper(trim(p.numeroPedido))) "
            + " = unaccent(upper(trim(:numeroPedido)))")
    boolean existePorNumeroPedido(@Param("numeroPedido") String numeroPedido, @Param("idEmpresa") Long idEmpresa);
    
    @Query("select count(p.id) > 0 from Pedido p where p.empresa.id = :idEmpresa "
            + " and unaccent(upper(trim(p.numeroPedido))) "
            + " = unaccent(upper(trim(:numeroPedido))) and p.id <> :id")
    boolean existePorNumeroPedidoDiferenteId(@Param("id") Long id, @Param("numeroPedido") String numeroPedido, @Param("idEmpresa") Long idEmpresa);    
    
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from Pedido p where p.empresa.id = :idEmpresa and p.id = :id")
    void deleteById(@Param("id") Long id, @Param("idEmpresa") Long idEmpresa);

}