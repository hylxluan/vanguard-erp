package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.model.Chamado;
import br.com.vanguarderp.repository.ChamadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<Chamado> getAllChamados(Long idEmpresa) {
		return chamadoRepository.findAll(idEmpresa);
	}

	public List<Chamado> getBuscaPorTitulo(String titulo, Long idEmpresa) {
		return chamadoRepository.buscaPorTitulo(titulo, idEmpresa);
	}

	public boolean existeChamadoPorTitulo(String titulo, Long idEmpresa) {
		return chamadoRepository.existePorTitulo(titulo, idEmpresa);
	}

	public boolean existeChamadoPorTituloDiferenteId(Long id, String titulo, Long idEmpresa) {
		return chamadoRepository.existePorTituloDiferenteId(id, titulo, idEmpresa);
	}

	public void deleteChamadoByIdAndEmpresa(Long id, Long idEmpresa) {
		chamadoRepository.deleteById(id, idEmpresa);
	}

	public Page<Chamado> listarChamadosPaginado(Long empresaId, Pageable pageable) {
		return chamadoRepository.listarPaginado(empresaId, pageable);
	}

	public long totalChamados(Long empresaId) {
		return chamadoRepository.total(empresaId);
	}

	public Optional<Chamado> buscarChamadoPorId(Long id, Long empresaId) {
		return chamadoRepository.buscarPorId(id, empresaId);
	}

	public List<Chamado> buscarChamadosPorIds(Iterable<Long> ids, Long empresaId) {
		return chamadoRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existeChamadoPorId(Long id, Long empresaId) {
		return chamadoRepository.existePorId(id, empresaId);
	}

	public List<Chamado> listarChamados(Long empresaId) {
		return chamadoRepository.listar(empresaId);
	}

	public long deletarChamadosPorIds(Iterable<Long> ids, Long empresaId) {
		return chamadoRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodosChamados(Long empresaId) {
		return chamadoRepository.deletarAll(empresaId);
	}

}
