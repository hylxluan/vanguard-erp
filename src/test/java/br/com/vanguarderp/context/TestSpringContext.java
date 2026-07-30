package br.com.vanguarderp.context;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import br.com.vanguarderp.app.VanguardERPApplication;

@SpringBootTest(classes = VanguardERPApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(OrderAnnotation.class)
@TestPropertySource(locations = "classpath:application.properties")
@Transactional
public class TestSpringContext {
	
	@Test
	public void testeInicial() {
		System.out.println("teste pae");
	}
}
