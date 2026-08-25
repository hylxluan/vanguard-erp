package br.com.vanguarderp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.vanguarderp.model.MovimentacaoProduto;
import jakarta.transaction.Transactional;

@Repository
public interface MovimentacaoProdutoRepository extends JpaVanguardRepository<MovimentacaoProduto, Long> {

    @Query("select m from MovimentacaoProduto m where m.empresa.id = :idEmpresa")
    List<MovimentacaoProduto> findAll(@Param("idEmpresa") Long idEmpresa);

    @Query("select m from MovimentacaoProduto m where m.empresa.id = :idEmpresa "
                                + " and unaccent(upper(trim(m.produto.nome))) "
                                + " like unaccent(upper(concat('%', trim(:nome) ,'%')))" )
    List<MovimentacaoProduto> buscaPorNome(@Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);

    @Query("select count(m.id) > 0 from MovimentacaoProduto m where m.empresa.id = :idEmpresa "
            + " and unaccent(upper(trim(m.produto.nome))) "
            + " = unaccent(upper(trim(:nome)))")
    boolean existePorNome(@Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);

    @Query("select count(m.id) > 0 from MovimentacaoProduto m where m.empresa.id = :idEmpresa "
            + " and unaccent(upper(trim(m.produto.nome))) "
            + " = unaccent(upper(trim(:nome))) and m.id <> :id")
    boolean existePorNomeDiferenteId(@Param("id") Long id, @Param("nome") String nome, @Param("idEmpresa") Long idEmpresa);    
    
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from MovimentacaoProduto m where m.empresa.id = :idEmpresa and m.id = :id")
    void deleteById(@Param("id") Long id, @Param("idEmpresa") Long idEmpresa);

    @Query("select m from MovimentacaoProduto m where m.empresa.id = :idEmpresa and m.pedido.id = :idPedido")
    List<MovimentacaoProduto> findAllByPedido(@Param("idPedido") Long idPedido, @Param("idEmpresa") Long idEmpresa);

    @Query("select m from MovimentacaoProduto m where m.empresa.id = :idEmpresa and m.pedido.id = :idPedido "
                                + " and unaccent(upper(trim(m.produto.nome))) "
                                + " like unaccent(upper(concat('%', trim(:nome) ,'%')))" )
    List<MovimentacaoProduto> buscaPorNomeByPedido(@Param("nome") String nome, @Param("idPedido") Long idPedido, @Param("idEmpresa") Long idEmpresa);

    @Query("select count(m.id) > 0 from MovimentacaoProduto m where m.empresa.id = :idEmpresa and m.pedido.id = :idPedido "
            + " and unaccent(upper(trim(m.produto.nome))) "
            + " = unaccent(upper(trim(:nome)))")
    boolean existePorNomeByPedido(@Param("nome") String nome, @Param("idPedido") Long idPedido, @Param("idEmpresa") Long idEmpresa);

    @Query("select count(m.id) > 0 from MovimentacaoProduto m where m.empresa.id = :idEmpresa and m.pedido.id = :idPedido "
            + " and unaccent(upper(trim(m.produto.nome))) "
            + " = unaccent(upper(trim(:nome))) and m.id <> :id")
    boolean existePorNomeDiferenteIdByPedido(@Param("id") Long id, @Param("nome") String nome, @Param("idPedido") Long idPedido, @Param("idEmpresa") Long idEmpresa);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("delete from MovimentacaoProduto m where m.empresa.id = :idEmpresa and m.pedido.id = :idPedido and m.id = :id")
    void deleteByIdAndPedido(@Param("id") Long id, @Param("idPedido") Long idPedido, @Param("idEmpresa") Long idEmpresa);

}