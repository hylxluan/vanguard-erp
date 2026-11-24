package br.com.vanguarderp.service;

import br.com.vanguarderp.dto.CategoriaDTO;
import br.com.vanguarderp.exceptions.MsgApiException;
import br.com.vanguarderp.model.Categoria;
import br.com.vanguarderp.repository.CategoriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private UsuarioLogadoService usuarioLogadoService;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public CategoriaDTO salvarCategoria(CategoriaDTO categoriaDTO) {

		if (categoriaRepository.existePorNome(categoriaDTO.getNome(), usuarioLogadoService.getIdEmpresaLogada())) {
			throw new MsgApiException("Já existe uma categoria com o mesmo nome para a empresa logada.");
		}

		Categoria categoria = new Categoria();
		categoria.setNome(categoriaDTO.getNome());
		categoria.setEmpresa(usuarioLogadoService.getEmpresaLogada());


		return converterParaDto(categoriaRepository.saveAndFlush(categoria));
	}

	@Transactional
	public CategoriaDTO atualizarCategoria(CategoriaDTO categoriaDTO) {

		if (categoriaDTO.getId() == null) {
			throw new MsgApiException("Não foi informado uma categoria com o mesmo id.");
		}

		if (categoriaRepository.existePorNomeDiferenteId(categoriaDTO.getId(), categoriaDTO.getNome(), usuarioLogadoService.getIdEmpresaLogada())) {
			throw new MsgApiException("Já existe uma categoria com este mesmo nome, informe outro nome.");
		}

		Categoria categoriaExistente = categoriaRepository.buscarPorId(categoriaDTO.getId(), usuarioLogadoService.getIdEmpresaLogada())
														  .orElseThrow(() -> new MsgApiException("Categoria não encontrada pra esta empresa"));

		categoriaExistente.setNome(categoriaDTO.getNome());

		return converterParaDto(categoriaRepository.saveAndFlush(categoriaExistente));
	}


	public List<Categoria> getAllCategorias() {
		return categoriaRepository.findAll();
	}
	
	public List<Categoria> getCategoriaPorNome(String nome, Long idEmpresa) {
		return categoriaRepository.buscarPorNome(nome, idEmpresa);
	}
	
	public boolean existeCategoriaPorNome(String nome, Long idEmpresa) {
		return categoriaRepository.existePorNome(nome, idEmpresa);
	}
	
	public boolean existeCategoriaPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return categoriaRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}
	
	public void deleteCategoriaByIdAndEmpresa(Long id, Long idEmpresa) {
		categoriaRepository.deleteByIdAndEmpresa(id, idEmpresa);
	}
	
	public Page<Categoria> listarCategoriasPaginado(Long empresaId, Pageable pageable) {
		return categoriaRepository.listarPaginado(empresaId, pageable);
	}
	
	public long totalCategorias(Long empresaId) {
		return categoriaRepository.total(empresaId);
	}
	
	public Optional<Categoria> buscarCategoriaPorId(Long id, Long empresaId) {
		return categoriaRepository.buscarPorId(id, empresaId);
	}
	
	public List<Categoria> buscarCategoriasPorIds(Iterable<Long> ids, Long empresaId) {
		return categoriaRepository.buscarPorIds(ids, empresaId);
	}
	
	public boolean existeCategoriaPorId(Long id, Long empresaId) {
		return categoriaRepository.existePorId(id, empresaId);
	}
	
	public List<Categoria> listarCategorias(Long empresaId) {
		return categoriaRepository.listar(empresaId);
	}
	
	public long deletarCategoriasPorIds(Iterable<Long> ids, Long empresaId) {
		return categoriaRepository.deletarAllPorIds(ids, empresaId);
	}
	
	public long deletarTodasCategorias(Long empresaId) {
		return categoriaRepository.deletarAll(empresaId);
	}

	private CategoriaDTO converterParaDto(Categoria categoria) {
		CategoriaDTO dto = new CategoriaDTO();
		dto.setId(categoria.getId());
		dto.setNome(categoria.getNome());
		dto.setEmpresaId(categoria.getEmpresa().getId());
		return dto;
	}

	private List<CategoriaDTO> converterListaParaDto(List<Categoria> categorias) {
		return categorias.stream().map(this::converterParaDto).toList();
	}


}
