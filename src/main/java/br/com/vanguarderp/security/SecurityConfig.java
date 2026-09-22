package br.com.vanguarderp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.vanguarderp.security.exceptions.JwtAccessDeniedHandler;
import br.com.vanguarderp.security.exceptions.JwtAuthenticationEntryPoint;
import br.com.vanguarderp.service.UsuarioDetailsService;

@Configurable
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private UsuarioDetailsService usuarioDetailsService;
	
	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthEntryPoint;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Argon2Password4jPasswordEncoder();
	}
	
	
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(usuarioDetailsService).passwordEncoder(passwordEncoder());
		
		return builder.build();
	}
	
	
	
	@Bean
	public CorsConfigurationSource corsConfiguration() {
		
		CorsConfiguration corsConfig = new CorsConfiguration();
		
		corsConfig.setAllowCredentials(true);
		corsConfig.addAllowedHeader("*");
		corsConfig.addAllowedMethod("*");
		corsConfig.addAllowedOriginPattern("*");
		
		UrlBasedCorsConfigurationSource sourceCors = new UrlBasedCorsConfigurationSource();
		
		sourceCors.registerCorsConfiguration("/**", corsConfig);
		
		return sourceCors;
	}
	
	
	
	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
		
		return http.csrf(csrf -> csrf.disable())
									 .cors(cors -> {})
									 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
									 .exceptionHandling(exception -> exception.accessDeniedHandler(jwtAccessDeniedHandler)
											 								  .authenticationEntryPoint(jwtAuthEntryPoint))
									 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
									 .authorizeHttpRequests(auth -> auth.requestMatchers("/api/usuario/login").permitAll()
											 							.requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
											 							.anyRequest().authenticated()
									 )
									 .build();
		
	}
	
	
}
