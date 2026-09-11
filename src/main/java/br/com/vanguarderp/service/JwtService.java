package br.com.vanguarderp.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.vanguarderp.exceptions.MsgApiException;
import br.com.vanguarderp.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private Long expirationTime;
	
	@Value("${jwt.issuer}")
	private String issuer;
	
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.ISO_8859_1));
	}
	
	public String gerarToken(Usuario usuario) {
		
		return Jwts.builder()
				   .subject(usuario.getLogin())
				   .claim("usuarioId", usuario.getId())
				   .claim("empresaId", usuario.getEmpresa().getId())
				   .issuedAt(new Date())
				   .expiration(new Date(System.currentTimeMillis() + expirationTime))
				   .issuer(issuer)
				   .signWith(getSigningKey())
				   .compact();
	}
	
	public Claims extrairClaims(String token) {
		
		return Jwts.parser()
			       .verifyWith(getSigningKey())
			       .build()
			       .parseSignedClaims(token)
			       .getPayload();
	
	}
	
	public Claims extrairClaims() {
		return Jwts.parser()
				   .verifyWith(getSigningKey())
				   .build()
				   .parseSignedClaims(getToken())
				   .getPayload();
	}
	
	
	public String extrairLogin(String token) {
		return extrairClaims(token).getSubject();
	}
	
	public String extrairLogin() {
		return extrairClaims().getSubject();
	}
	
	
	
	public Long extrairUsuarioId(String token) {
		return extrairClaims(token).get("usuarioId", Long.class);
	}
	
	public Long extrairUsuarioId() {
		return extrairClaims().get("usuarioId", Long.class);
	}
	
	
	public Long extrairEmpresaId(String token) {
		return extrairClaims(token).get("empresaId", Long.class);
	}
	
	public Long extrairEmpresaId() {
		return extrairClaims().get("empresaId", Long.class);
	}
	
	
	
	public String getIssuer(String token) {
		return extrairClaims(token).getIssuer();
	}
	
	
	
	private String getToken() {
		return (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
	}
	
	public boolean isTokenValido(String token) {
	    try {
	    	
	        if (token == null || token.isEmpty()) {
	            return false;
	        }
	        
	        extrairClaims(token);
	        
	        return true;
	        
	    } catch (Exception e) {
	        throw new MsgApiException("Token inválido ou expirado");
	    }
	}
	
}
