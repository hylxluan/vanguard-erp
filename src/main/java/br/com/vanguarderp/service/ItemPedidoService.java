package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.ItemPedido;
import br.com.vanguarderp.repository.ItemPedidoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<ItemPedido> getAllItensPedido(Long idPedido, Long idEmpresa) {
		return itemPedidoRepository.findAll(idPedido, idEmpresa);
	}

	public List<ItemPedido> getBuscaPorNome(String nome, Long idPedido, Long idEmpresa) {
		return itemPedidoRepository.buscaPorNome(nome, idPedido, idEmpresa);
	}

	public boolean existeItemPedidoPorNome(String nome, Long idPedido, Long idEmpresa) {
		return itemPedidoRepository.existePorNome(nome, idPedido, idEmpresa);
	}

	public boolean existeItemPedidoPorNomeDiferenteId(Long id, String nome, Long idPedido, Long idEmpresa) {
		return itemPedidoRepository.existePorNomeDiferenteId(id, nome, idPedido, idEmpresa);
	}

	public void deleteItemPedidoByIdAndPedido(Long id, Long idPedido, Long idEmpresa) {
		itemPedidoRepository.deleteById(id, idPedido, idEmpresa);
	}

	public List<ItemPedido> getFindAllByPedido(Long idPedido, Long idEmpresa) {
		return itemPedidoRepository.findAllByPedido(idPedido, idEmpresa);
	}

	public List<ItemPedido> getBuscaPorNomePorPedido(String nome, Long idPedido, Long idEmpresa) {
		return itemPedidoRepository.buscaPorNomePorPedido(nome, idPedido, idEmpresa);
	}

	public boolean existeItemPedidoPorNomePorPedido(String nome, Long idPedido, Long idEmpresa) {
		return itemPedidoRepository.existePorNomePorPedido(nome, idPedido, idEmpresa);
	}

	public boolean existeItemPedidoPorNomeDiferenteIdPorPedido(Long id, String nome, Long idPedido, Long idEmpresa) {
		return itemPedidoRepository.existePorNomeDiferenteIdPorPedido(id, nome, idPedido, idEmpresa);
	}

	public void deleteItemPedidoByIdAndPedidoMethod(Long id, Long idPedido, Long idEmpresa) {
		itemPedidoRepository.deleteByIdAndPedido(id, idPedido, idEmpresa);
	}

	public Page<ItemPedido> listarItensPedidoPaginado(Long empresaId, Pageable pageable) {
		return itemPedidoRepository.listarPaginado(empresaId, pageable);
	}

	public long totalItensPedido(Long empresaId) {
		return itemPedidoRepository.total(empresaId);
	}

	public Optional<ItemPedido> buscarItemPedidoPorId(Long id, Long empresaId) {
		return itemPedidoRepository.buscarPorId(id, empresaId);
	}

	public List<ItemPedido> buscarItensPedidoPorIds(Iterable<Long> ids, Long empresaId) {
		return itemPedidoRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existeItemPedidoPorId(Long id, Long empresaId) {
		return itemPedidoRepository.existePorId(id, empresaId);
	}

	public List<ItemPedido> listarItensPedido(Long empresaId) {
		return itemPedidoRepository.listar(empresaId);
	}

	public long deletarItensPedidoPorIds(Iterable<Long> ids, Long empresaId) {
		return itemPedidoRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodosItensPedido(Long empresaId) {
		return itemPedidoRepository.deletarAll(empresaId);
	}

}
