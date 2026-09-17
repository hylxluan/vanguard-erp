package br.com.vanguarderp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.dto.AlterarSenhaDTO;
import br.com.vanguarderp.dto.LoginDTO;
import br.com.vanguarderp.dto.TokenDTO;
import br.com.vanguarderp.exceptions.MsgApiException;
import br.com.vanguarderp.model.ClienteFuncionario;
import br.com.vanguarderp.model.Role;
import br.com.vanguarderp.model.Usuario;
import br.com.vanguarderp.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UsuarioLogadoService usuarioLogadoService;
	
	@Autowired
	private ClienteFuncionarioService clienteFuncionarioService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleUsuarioService usuarioRoleService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public TokenDTO login(LoginDTO login) {
		Usuario usuario = usuarioRepository.buscarPorLogin(login.getLogin());
		
		if (usuario == null) {
			throw new MsgApiException("Usuário não encontrado", HttpStatus.UNAUTHORIZED);
		}
		
		
		if (!passwordEncoder.matches(login.getSenha(), usuario.getSenha())) {
			throw new MsgApiException("Senha inválida", HttpStatus.UNAUTHORIZED);
		}
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(), login.getSenha()));
		
		String token = jwtService.gerarToken(usuario);
		
		usuarioRepository.updateAccessTokenLogin(usuario.getId(), token, usuario.getEmpresa().getId());
		
		return new TokenDTO(token);
	}
	
	
	public Usuario salvarUsuario(Usuario usuario) {
		
		if (!usuario.isAdmin()) {
			throw new MsgApiException("Apenas usuários administradores podem criar novos usuários!");
		}
		
		if (usuarioRepository.existePorLogin(usuario.getLogin(), usuarioLogadoService.getIdEmpresaLogada())) {
			throw new MsgApiException("Já existe um usuário com este login");
		}
		
		if (usuario.getSenha().length() < 5) {
			throw new MsgApiException("A senha deve ter no mínimo 5 caracteres");
		}
		
		if (usuarioRepository.existePorPessoa(usuario.getClienteFuncionario()
													 .getPessoa().getId(), usuarioLogadoService.getIdEmpresaLogada())) {
			
			throw new MsgApiException("Já existe um usuário vinculado a esta pessoa.");
		}
		
		ClienteFuncionario clienteFuncionario = clienteFuncionarioService.findByPessoa(usuario.getClienteFuncionario()
				.getPessoa().getId(), usuarioLogadoService.getIdEmpresaLogada());
		
		
		List<Role> roles = roleService.buscarPorAcesso("ROLE_USER");
		
		usuario.setEmpresa(usuarioLogadoService.getEmpresaLogada());
		usuario.setClienteFuncionario(clienteFuncionario);
		usuario.setAcessos(roles);
		
		usuario = usuarioRepository.saveAndFlush(usuario);
		
		clienteFuncionario.setUsuario(usuario);
		clienteFuncionarioService.salvar(clienteFuncionario);
		
		
		return usuario;
	}
	
	public Usuario atualizarUsuario(Usuario usuario) {
		
		if (!usuario.isAdmin()) {
			throw new MsgApiException("Apenas usuários administradores podem atualizar usuários!");
		}
		
		if (usuarioRepository.existeOutroUsuarioComPessoa(usuario.getClienteFuncionario()
				.getPessoa().getId(), usuario.getId(), usuarioLogadoService.getIdEmpresaLogada())) {
			throw new MsgApiException("Já existe outro usuário vinculado a esta pessoa.");
		}
		
		Usuario usuarioExistente = usuarioRepository.buscarPorId(usuario.getId(), usuarioLogadoService.getIdEmpresaLogada()).get();
		
		if (usuarioExistente == null) {
			throw new MsgApiException("Usuário não encontrado para atualização.");
		}
		
		if (usuarioExistente.getAcessos() == null || usuarioExistente.getAcessos().isEmpty()) {
			usuario.setAcessos(usuarioExistente.getAcessos());
		}
		
		ClienteFuncionario clienteFuncionario = clienteFuncionarioService.findByPessoa(usuario.getClienteFuncionario()
				.getPessoa().getId(), usuarioLogadoService.getIdEmpresaLogada());
		
		if (clienteFuncionario == null) {
			throw new MsgApiException("Pessoa não encontrada para atualizar o usuário.");
		}
		
		
		usuarioExistente.setEmpresa(usuarioLogadoService.getEmpresaLogada());
		usuarioExistente.setClienteFuncionario(clienteFuncionario);
		usuario.setSenha(usuarioExistente.getSenha());
		
		
		
		return usuarioRepository.saveAndFlush(usuarioExistente);
		
	}
	
	public void alterarSenhaUsuario(AlterarSenhaDTO dto) {
		Usuario usuarioRecuperado = usuarioRepository.buscarPorId(dto.getIdUsuario(), usuarioLogadoService.getIdEmpresaLogada()).get();
		
		if (usuarioRecuperado == null) {
			throw new MsgApiException("Usuário não encontrado para alteração de senha.");
		}
		
		if (usuarioRecuperado.getEmpresa().getBloqueio()) {
			throw new MsgApiException("A empresa está bloqueada. Não é possível alterar a senha do usuário, entre em contato com o administrador do sistema!");
		}
		
		
		
		if (!passwordEncoder.matches(dto.getSenhaAtual(), usuarioRecuperado.getSenha())) {
			throw new MsgApiException("Senha atual inválida.");
		}
		
		if (!dto.getNovaSenha().equals(dto.getConfirmarNovaSenha())) {
			throw new MsgApiException("A nova senha e a confirmação da nova senha não coincidem!");
		}
		
		if (passwordEncoder.matches(dto.getNovaSenha(), usuarioRecuperado.getSenha())) {
			throw new MsgApiException("A senha nova não pode ser igual à senha atual.");
		}
		
		usuarioRecuperado.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
		
		usuarioRepository.saveAndFlush(usuarioRecuperado);
	}
	
	
	public List<Usuario> getAllUsuarios(Long idEmpresa) {
		return usuarioRepository.findAll(idEmpresa);
	}

	public Usuario getBuscarPorLogin(String login) {
		return usuarioRepository.buscarPorLogin(login);
	}

	public List<Usuario> getBuscaPorNome(String nome, Long idEmpresa) {
		return usuarioRepository.buscaPorNome(nome, idEmpresa);
	}

	public boolean existeUsuarioPorLogin(String login, Long idEmpresa) {
		return usuarioRepository.existePorLogin(login, idEmpresa);
	}

	public boolean existeUsuarioPorPessoa(Long idPessoa, Long idEmpresa) {
		return usuarioRepository.existePorPessoa(idPessoa, idEmpresa);
	}

	public boolean existeUsuarioPorNome(String nome, Long idEmpresa) {
		return usuarioRepository.existePorNome(nome, idEmpresa);
	}

	public boolean existeUsuarioPorNomeDiferenteId(Long id, String nome, Long idEmpresa) {
		return usuarioRepository.existePorNomeDiferenteId(id, nome, idEmpresa);
	}

	public boolean existeOutroUsuarioComPessoa(Long pessoaId, Long usuarioId, Long idEmpresa) {
		return usuarioRepository.existeOutroUsuarioComPessoa(pessoaId, usuarioId, idEmpresa);
	}

	public void deleteUsuarioByIdAndEmpresa(Long id, Long idEmpresa) {
		usuarioRepository.deleteById(id, idEmpresa);
	}

	public void updateAccessTokenLogin(Long id, String token, Long idEmpresa) {
		usuarioRepository.updateAccessTokenLogin(id, token, idEmpresa);
	}

	public Page<Usuario> listarUsuariosPaginado(Long empresaId, Pageable pageable) {
		return usuarioRepository.listarPaginado(empresaId, pageable);
	}

	public long totalUsuarios(Long empresaId) {
		return usuarioRepository.total(empresaId);
	}

	public Optional<Usuario> buscarUsuarioPorId(Long id, Long empresaId) {
		return usuarioRepository.buscarPorId(id, empresaId);
	}

	public List<Usuario> buscarUsuariosPorIds(Iterable<Long> ids, Long empresaId) {
		return usuarioRepository.buscarPorIds(ids, empresaId);
	}

	public boolean existeUsuarioPorId(Long id, Long empresaId) {
		return usuarioRepository.existePorId(id, empresaId);
	}

	public List<Usuario> listarUsuarios(Long empresaId) {
		return usuarioRepository.listar(empresaId);
	}

	public long deletarUsuariosPorIds(Iterable<Long> ids, Long empresaId) {
		return usuarioRepository.deletarAllPorIds(ids, empresaId);
	}

	public long deletarTodosUsuarios(Long empresaId) {
		return usuarioRepository.deletarAll(empresaId);
	}

}
