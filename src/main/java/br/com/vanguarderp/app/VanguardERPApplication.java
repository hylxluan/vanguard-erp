package br.com.vanguarderp.app;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.vanguarderp.repository.JpaVanguardRepositoryImpl;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableAsync
@EnableTransactionManagement
@EntityScan(basePackages = "br.com.vanguarderp.model")
@EnableJpaRepositories(basePackages = "br.com.vanguarderp.repository", 
repositoryBaseClass = JpaVanguardRepositoryImpl.class)
@ComponentScan(basePackages = "br.com.vanguarderp")

public class VanguardERPApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;
	private String pass = "sport clube do recife";
	
	void main(String[] args) {
		SpringApplication app = new SpringApplication(VanguardERPApplication.class);
		app.run(args);
	}
	
	@Bean
	public CacheManager cacheManagement() {
		ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager("vanguardCache");
		return manager;
	}
	
	@PostConstruct
	private void configTimeZone() {
		Locale.setDefault(Locale.forLanguageTag("pt_BR"));
		TimeZone timeZoneSP = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(timeZoneSP);
		Calendar.getInstance().setTimeZone(timeZoneSP);
		System.out.println("senha gerada: " + passwordEncoder.encode(pass));
	}
	
}
