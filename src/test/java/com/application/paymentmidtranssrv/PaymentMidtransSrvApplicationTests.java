package com.application.paymentmidtranssrv;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(
		properties = {
				"management.endpoint.health.show-details=always",
				"spring.datasource.url=jdbc:tc:mysql:9.2.0:///bankDB"
		},
		webEnvironment = RANDOM_PORT
)
class PaymentMidtransSrvApplicationTests {
	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.2.0")
			.withDatabaseName("testdb")
			.withUsername("test")
			.withPassword("test");

	@DynamicPropertySource
	static void mysqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mysql::getJdbcUrl);
		registry.add("spring.datasource.username", mysql::getUsername);
		registry.add("spring.datasource.password", mysql::getPassword);
	}

	@BeforeAll
	static void beforeAll() {
		mysql.start();
	}

	@AfterAll
	static void afterAll() {
		mysql.stop();
	}

	@Test
	void connectionEstablished() {
		assertThat(mysql.isCreated()).isTrue();
		assertThat(mysql.isRunning()).isTrue();
	}

}
