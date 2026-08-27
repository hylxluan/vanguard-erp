package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Produto;
import br.com.vanguarderp.repository.ProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Produto> getAllProdutos(Long idEmpresa) {
		return produtoRepository.findAll(idEmpresa);
	}

	public List<Produto> getBuscaPorNome(String nome, Long idEmpresa) {
		return produtoRepository.buscaPorNome(nome, idEmpresa);
	}

	public boolean existeProdutoPorNome(String nome, Long idEmpresa) {
		return produtoRepository.existePorNome(nome, idEmpresa);
	}

	public boolean existeProdutoPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return produtoRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}

	public void deleteProdutoByIdAndEmpresa(Long id, Long idEmpresa) {
		produtoRepository.deleteById(id, idEmpresa);
	}

	public Page<Produto> listarProdutosPaginado(Long empresaId, Pageable pageable) {
		return produtoRepository.listarPaginado(empresaId, pageable);
	}

	public long totalProdutos(Long empresaId) {
		return produtoRepository.total(empresaId);
	}

	public Optional<Produto> buscarProdutoPorId(Long id, Long empresaId) {
		return produtoRepository.buscarPorId(id, empresaId);
	}

	public List<Produto> buscarProdutosPorIds(Iterable<Long> ids, Long empresaId) {
		return produtoRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existeProdutoPorId(Long id, Long empresaId) {
		return produtoRepository.existePorId(id, empresaId);
	}

	public List<Produto> listarProdutos(Long empresaId) {
		return produtoRepository.listar(empresaId);
	}

	public long deletarProdutosPorIds(Iterable<Long> ids, Long empresaId) {
		return produtoRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodosProdutos(Long empresaId) {
		return produtoRepository.deletarAll(empresaId);
	}

}
