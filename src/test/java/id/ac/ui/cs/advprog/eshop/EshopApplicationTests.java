package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EshopApplicationTests {

	@Autowired
	private EshopApplication eshopApplication;

	@Test
	void contextLoads() {
		assertNotNull(eshopApplication);
	}

	@Test
	void mainMethodDoesNotThrowException() {
		assertDoesNotThrow(() -> EshopApplication.main(new String[] {}));
	}
}