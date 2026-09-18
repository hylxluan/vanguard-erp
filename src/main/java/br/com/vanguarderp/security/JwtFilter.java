package br.com.vanguarderp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.vanguarderp.exceptions.MsgApiException;
import br.com.vanguarderp.model.Usuario;
import br.com.vanguarderp.service.JwtService;
import br.com.vanguarderp.service.UsuarioDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UsuarioDetailsService usuarioDetailsService;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String header = request.getHeader("Authorization");
			
			if (header == null || !header.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String token = header.substring(7);
			
			if (token.isBlank()) {
				filterChain.doFilter(request, response);
				return;
			}
			
			if (!jwtService.isTokenValido(token)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			
			String login = jwtService.extrairLogin(token);
			
			if (login != null || SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UserDetails userDetails = usuarioDetailsService.loadUserByUsername(login);
				
				UsuarioAutenticado principal = new UsuarioAutenticado((Usuario) userDetails);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
																											 principal, 
																											 token, 
																											 principal.getAuthorities()
																											);
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			SecurityContextHolder.clearContext();
			throw new MsgApiException("Erro ao validar JWT do Usuário no Sistema.");
		}
		
		filterChain.doFilter(request, response);
	}
	
}
