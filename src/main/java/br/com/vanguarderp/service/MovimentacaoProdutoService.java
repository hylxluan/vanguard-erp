package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.MovimentacaoProduto;
import br.com.vanguarderp.repository.MovimentacaoProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class MovimentacaoProdutoService {

	@Autowired
	private MovimentacaoProdutoRepository movimentacaoProdutoRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<MovimentacaoProduto> getAllMovimentacoesProduto(Long idEmpresa) {
		return movimentacaoProdutoRepository.findAll(idEmpresa);
	}

	public List<MovimentacaoProduto> getBuscaPorNome(String nome, Long idEmpresa) {
		return movimentacaoProdutoRepository.buscaPorNome(nome, idEmpresa);
	}

	public boolean existeMovimentacaoProdutoPorNome(String nome, Long idEmpresa) {
		return movimentacaoProdutoRepository.existePorNome(nome, idEmpresa);
	}

	public boolean existeMovimentacaoProdutoPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return movimentacaoProdutoRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}

	public void deleteMovimentacaoProdutoByIdAndEmpresa(Long id, Long idEmpresa) {
		movimentacaoProdutoRepository.deleteById(id, idEmpresa);
	}

	public List<MovimentacaoProduto> getFindAllByPedido(Long idPedido, Long idEmpresa) {
		return movimentacaoProdutoRepository.findAllByPedido(idPedido, idEmpresa);
	}

	public List<MovimentacaoProduto> getBuscaPorNomeByPedido(String nome, Long idPedido, Long idEmpresa) {
		return movimentacaoProdutoRepository.buscaPorNomeByPedido(nome, idPedido, idEmpresa);
	}

	public boolean existeMovimentacaoProdutoPorNomeByPedido(String nome, Long idPedido, Long idEmpresa) {
		return movimentacaoProdutoRepository.existePorNomeByPedido(nome, idPedido, idEmpresa);
	}

	public boolean existeMovimentacaoProdutoPorNomeDiferenteIdByPedido(Long id, String nome, Long idPedido, Long idEmpresa) {
		return movimentacaoProdutoRepository.existePorNomeDiferenteIdByPedido(id, nome, idPedido, idEmpresa);
	}

	public void deleteMovimentacaoProdutoByIdAndPedido(Long id, Long idPedido, Long idEmpresa) {
		movimentacaoProdutoRepository.deleteByIdAndPedido(id, idPedido, idEmpresa);
	}

	public Page<MovimentacaoProduto> listarMovimentacoesProdutoPaginado(Long empresaId, Pageable pageable) {
		return movimentacaoProdutoRepository.listarPaginado(empresaId, pageable);
	}

	public long totalMovimentacoesProduto(Long empresaId) {
		return movimentacaoProdutoRepository.total(empresaId);
	}

	public Optional<MovimentacaoProduto> buscarMovimentacaoProdutoPorId(Long id, Long empresaId) {
		return movimentacaoProdutoRepository.buscarPorId(id, empresaId);
	}

	public List<MovimentacaoProduto> buscarMovimentacoesProdutoPorIds(Iterable<Long> ids, Long empresaId) {
		return movimentacaoProdutoRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existeMovimentacaoProdutoPorId(Long id, Long empresaId) {
		return movimentacaoProdutoRepository.existePorId(id, empresaId);
	}

	public List<MovimentacaoProduto> listarMovimentacoesProduto(Long empresaId) {
		return movimentacaoProdutoRepository.listar(empresaId);
	}

	public long deletarMovimentacoesProdutoPorIds(Iterable<Long> ids, Long empresaId) {
		return movimentacaoProdutoRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodasMovimentacoesProduto(Long empresaId) {
		return movimentacaoProdutoRepository.deletarAll(empresaId);
	}

}
