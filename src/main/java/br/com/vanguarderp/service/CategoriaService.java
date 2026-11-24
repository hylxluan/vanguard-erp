package br.com.vanguarderp.service;

import br.com.vanguarderp.dto.CategoriaDTO;
import br.com.vanguarderp.exceptions.MsgApiException;
import br.com.vanguarderp.model.Categoria;
import br.com.vanguarderp.repository.CategoriaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoriaService {

	private static final Map<String, String> CAMPOS_ORDENACAO_CATEGORIA = Map.of(
			"id", "id",
			"nome", "nome");

	private static final int TAMANHO_MAXIMO_PAGINA = 100;

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

		Long empresaId = usuarioLogadoService.getIdEmpresaLogada();

		Categoria categoriaExistente = categoriaRepository.buscarPorId(categoriaDTO.getId(), empresaId)
														  .orElseThrow(() -> new MsgApiException("Categoria não encontrada pra esta empresa"));

		if (categoriaRepository.existePorNomeDiferenteId(categoriaDTO.getId(), categoriaDTO.getNome(), empresaId)) {
			throw new MsgApiException("Já existe uma categoria com este mesmo nome, informe outro nome.");
		}

		categoriaExistente.setNome(categoriaDTO.getNome());

		return converterParaDto(categoriaRepository.saveAndFlush(categoriaExistente));
	}

	public List<CategoriaDTO> getAllCategorias(Long idEmpresa) {
		return converterListaParaDto(categoriaRepository.findAll(idEmpresa));
	}

	public List<CategoriaDTO> getCategoriaPorNome(String nome, Long idEmpresa) {
		return converterListaParaDto(categoriaRepository.buscarPorNome(nome, idEmpresa));
	}

	public boolean existeCategoriaPorNome(String nome, Long idEmpresa) {
		return categoriaRepository.existePorNome(nome, idEmpresa);
	}

	public boolean existeCategoriaPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return categoriaRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}

	@Transactional
	public void deleteCategoriaByIdAndEmpresa(Long id, Long idEmpresa) {

		if (!categoriaRepository.existePorId(id, idEmpresa)) {
			throw new MsgApiException("Categoria não encontrada para a empresa logada, portanto não pode ser deletada.");
		}

		categoriaRepository.deleteByIdAndEmpresa(id, idEmpresa);
	}

	public Page<Categoria> listarCategoriasPaginado(Long empresaId, Pageable pageable) {
		return categoriaRepository.listarPaginado(empresaId, pageable);
	}

	public List<CategoriaDTO> listarCategoriasPaginadoDto(Long empresaId, Pageable pageable) {
		return converterListaParaDto(categoriaRepository.listarPaginado(empresaId, pageable).getContent());
	}

	public List<CategoriaDTO> listarCategoriasPaginadoDto(Long empresaId, int page, int size, String sort, String direction) {
		if (page < 0) {
			throw new MsgApiException("A página deve ser maior ou igual a 0.");
		}

		if (size < 1 || size > TAMANHO_MAXIMO_PAGINA) {
			throw new MsgApiException("O tamanho da página deve estar entre 1 e " + TAMANHO_MAXIMO_PAGINA + ".");
		}

		String campoOrdenacao = CAMPOS_ORDENACAO_CATEGORIA.get(sort);
		if (campoOrdenacao == null) {
			throw new MsgApiException("Campo de ordenação inválido: " + sort + ". Use: id ou nome.");
		}

		Sort.Direction sortDirection;
		if ("asc".equalsIgnoreCase(direction)) {
			sortDirection = Sort.Direction.ASC;
		} else if ("desc".equalsIgnoreCase(direction)) {
			sortDirection = Sort.Direction.DESC;
		} else {
			throw new MsgApiException("Direção de ordenação inválida: " + direction + ". Use asc ou desc.");
		}

		return listarCategoriasPaginadoDto(empresaId, PageRequest.of(page, size, Sort.by(sortDirection, campoOrdenacao)));
	}

	public long totalCategorias(Long empresaId) {
		return categoriaRepository.total(empresaId);
	}

	public Optional<Categoria> buscarCategoriaPorId(Long id, Long empresaId) {
		return categoriaRepository.buscarPorId(id, empresaId);
	}

	public CategoriaDTO buscarCategoriaDtoPorId(Long id, Long empresaId) {
		Categoria categoria = categoriaRepository.buscarPorId(id, empresaId)
												 .orElseThrow(() -> new MsgApiException("Categoria não encontrada para a empresa logada."));

		return converterParaDto(categoria);
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

	public List<CategoriaDTO> listarCategoriasDto(Long empresaId) {
		return converterListaParaDto(categoriaRepository.listar(empresaId));
	}

	@Transactional
	public long deletarCategoriasPorIds(Iterable<Long> ids, Long empresaId) {
		return categoriaRepository.deletarAllPorIds(ids, empresaId);
	}

	@Transactional
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
