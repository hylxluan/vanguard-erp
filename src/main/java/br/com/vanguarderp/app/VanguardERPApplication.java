package br.com.vanguarderp.app;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableAsync
@EnableTransactionManagement
@EntityScan(basePackages = "br.com.vanguarderp.model")
@EnableJpaRepositories(basePackages = "br.com.vanguarderp.repository")
@ComponentScan(basePackages = "br.com.vanguarderp")

public class VanguardERPApplication {

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
	}
	
}
