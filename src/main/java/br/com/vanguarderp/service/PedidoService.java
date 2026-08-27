package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Pedido;
import br.com.vanguarderp.repository.PedidoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Pedido> getAllPedidos(Long idEmpresa) {
		return pedidoRepository.findAll(idEmpresa);
	}

	public List<Pedido> getBuscaPorNumeroPedido(String numeroPedido, Long idEmpresa) {
		return pedidoRepository.buscaPorNumeroPedido(numeroPedido, idEmpresa);
	}

	public boolean existePedidoPorNumeroPedido(String numeroPedido, Long idEmpresa) {
		return pedidoRepository.existePorNumeroPedido(numeroPedido, idEmpresa);
	}

	public boolean existePedidoPorNumeroPedidoDiferenteId(Long id, String numeroPedido, Long idEmpresa) {
		return pedidoRepository.existePorNumeroPedidoDiferenteId(id, numeroPedido, idEmpresa);
	}

	public void deletePedidoByIdAndEmpresa(Long id, Long idEmpresa) {
		pedidoRepository.deleteById(id, idEmpresa);
	}

	public Page<Pedido> listarPedidosPaginado(Long empresaId, Pageable pageable) {
		return pedidoRepository.listarPaginado(empresaId, pageable);
	}

	public long totalPedidos(Long empresaId) {
		return pedidoRepository.total(empresaId);
	}

	public Optional<Pedido> buscarPedidoPorId(Long id, Long empresaId) {
		return pedidoRepository.buscarPorId(id, empresaId);
	}

	public List<Pedido> buscarPedidosPorIds(Iterable<Long> ids, Long empresaId) {
		return pedidoRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existePedidoPorId(Long id, Long empresaId) {
		return pedidoRepository.existePorId(id, empresaId);
	}

	public List<Pedido> listarPedidos(Long empresaId) {
		return pedidoRepository.listar(empresaId);
	}

	public long deletarPedidosPorIds(Iterable<Long> ids, Long empresaId) {
		return pedidoRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodosPedidos(Long empresaId) {
		return pedidoRepository.deletarAll(empresaId);
	}

}
