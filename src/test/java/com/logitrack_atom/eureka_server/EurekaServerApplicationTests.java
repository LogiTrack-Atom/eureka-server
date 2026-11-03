package com.logitrack_atom.eureka_server;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas de arranque del servidor Eureka sin depender del Config Server.
 */
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {
				"spring.cloud.config.enabled=false",
				"eureka.client.register-with-eureka=false",
				"eureka.client.fetch-registry=false"
		})
class EurekaServerApplicationTests {

	@Value("${local.server.port}")
	private int port;

	private final HttpClient client = HttpClient.newHttpClient();

	private HttpResponse<String> get(String path, String accept) throws Exception {
		HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
				.header("Accept", accept)
				.GET()
				.build();
		return client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	@Test
	void contextLoads() {
	}

	@Test
	void healthDebeEstarUp() throws Exception {
		HttpResponse<String> response = get("/actuator/health", "application/json");
		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("UP"));
	}

	@Test
	void noDebeRegistrarseASiMismo() throws Exception {
		HttpResponse<String> response = get("/eureka/apps", "application/json");
		assertEquals(200, response.statusCode());
		assertFalse(response.body().toUpperCase().contains("EUREKA-SERVER"));
	}
}