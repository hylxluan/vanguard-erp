package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Mensagem;
import br.com.vanguarderp.repository.MensagemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository mensagemRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Mensagem> getAllMensagens(Long idEmpresa) {
		return mensagemRepository.findAll(idEmpresa);
	}

	public List<Mensagem> getBuscaPorConteudo(String conteudo, Long idEmpresa) {
		return mensagemRepository.buscaPorConteudo(conteudo, idEmpresa);
	}

	public boolean existeMensagemPorConteudo(String conteudo, Long idEmpresa) {
		return mensagemRepository.existePorConteudo(conteudo, idEmpresa);
	}

	public boolean existeMensagemPorConteudoDiferenteId(Long id, String conteudo, Long idEmpresa) {
		return mensagemRepository.existePorConteudoDiferenteId(id, conteudo, idEmpresa);
	}

	public void deleteMensagemByIdAndEmpresa(Long id, Long idEmpresa) {
		mensagemRepository.deleteById(id, idEmpresa);
	}

	// ====================Métodos específicos para Chamado====================

	public List<Mensagem> getFindAllByChamado(Long idChamado, Long idEmpresa) {
		return mensagemRepository.findAllByChamado(idChamado, idEmpresa);
	}

	public List<Mensagem> getBuscaPorConteudoByChamado(String conteudo, Long idChamado, Long idEmpresa) {
		return mensagemRepository.buscaPorConteudoByChamado(conteudo, idChamado, idEmpresa);
	}

	public boolean existeMensagemPorConteudoByChamado(String conteudo, Long idChamado, Long idEmpresa) {
		return mensagemRepository.existePorConteudoByChamado(conteudo, idChamado, idEmpresa);
	}

	public boolean existeMensagemPorConteudoDiferenteIdByChamado(Long id, String conteudo, Long idChamado, Long idEmpresa) {
		return mensagemRepository.existePorConteudoDiferenteIdByChamado(id, conteudo, idChamado, idEmpresa);
	}

	public long countByChamado(Long idChamado, Long idEmpresa) {
		return mensagemRepository.countByChamado(idChamado, idEmpresa);
	}

	public void deleteAllMensagensByChamado(Long idChamado, Long idEmpresa) {
		mensagemRepository.deleteAllByChamado(idChamado, idEmpresa);
	}

	public void deleteMensagemByIdAndChamado(Long id, Long idChamado, Long idEmpresa) {
		mensagemRepository.deleteByIdAndChamado(id, idChamado, idEmpresa);
	}

	// ====================Métodos para Status de Leitura====================

	public List<Mensagem> getFindAllNaoLidas(Long idEmpresa) {
		return mensagemRepository.findAllNaoLidas(idEmpresa);
	}

	public List<Mensagem> getFindAllNaoLidasByChamado(Long idChamado, Long idEmpresa) {
		return mensagemRepository.findAllNaoLidasByChamado(idChamado, idEmpresa);
	}

	public long countNaoLidasByChamado(Long idChamado, Long idEmpresa) {
		return mensagemRepository.countNaoLidasByChamado(idChamado, idEmpresa);
	}

	public void updateLida(Long id, Boolean lida, Long idEmpresa) {
		mensagemRepository.updateLida(id, lida, idEmpresa);
	}

	// ====================Métodos para Atendente====================

	public List<Mensagem> getFindAllByAtendente(Long idAtendente, Long idEmpresa) {
		return mensagemRepository.findAllByAtendente(idAtendente, idEmpresa);
	}

	public long countByAtendente(Long idAtendente, Long idEmpresa) {
		return mensagemRepository.countByAtendente(idAtendente, idEmpresa);
	}

	// ====================Métodos para Cliente====================

	public List<Mensagem> getFindAllByCliente(Long idCliente, Long idEmpresa) {
		return mensagemRepository.findAllByCliente(idCliente, idEmpresa);
	}

	public long countByCliente(Long idCliente, Long idEmpresa) {
		return mensagemRepository.countByCliente(idCliente, idEmpresa);
	}

	// ====================Métodos Combinados====================

	public List<Mensagem> getFindAllByChamadoAndAtendente(Long idChamado, Long idAtendente, Long idEmpresa) {
		return mensagemRepository.findAllByChamadoAndAtendente(idChamado, idAtendente, idEmpresa);
	}

	public List<Mensagem> getFindAllNaoLidasByAtendente(Long idAtendente, Long idEmpresa) {
		return mensagemRepository.findAllNaoLidasByAtendente(idAtendente, idEmpresa);
	}

	public List<Mensagem> getFindAllNaoLidasByCliente(Long idCliente, Long idEmpresa) {
		return mensagemRepository.findAllNaoLidasByCliente(idCliente, idEmpresa);
	}

	// ====================Métodos genéricos do JpaVanguardRepository====================

	public Page<Mensagem> listarMensagensPaginado(Long empresaId, Pageable pageable) {
		return mensagemRepository.listarPaginado(empresaId, pageable);
	}

	public long totalMensagens(Long empresaId) {
		return mensagemRepository.total(empresaId);
	}

	public Optional<Mensagem> buscarMensagemPorId(Long id, Long empresaId) {
		return mensagemRepository.buscarPorId(id, empresaId);
	}

	public List<Mensagem> buscarMensagensPorIds(Iterable<Long> ids, Long empresaId) {
		return mensagemRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existeMensagemPorId(Long id, Long empresaId) {
		return mensagemRepository.existePorId(id, empresaId);
	}

	public List<Mensagem> listarMensagens(Long empresaId) {
		return mensagemRepository.listar(empresaId);
	}

	public long deletarMensagensPorIds(Iterable<Long> ids, Long empresaId) {
		return mensagemRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodasMensagens(Long empresaId) {
		return mensagemRepository.deletarAll(empresaId);
	}

}
