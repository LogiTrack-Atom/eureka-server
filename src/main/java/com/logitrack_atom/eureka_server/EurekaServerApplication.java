package com.logitrack_atom.eureka_server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Servidor de registro y descubrimiento de LogiTrack.
 * <p>
 * Los microservicios del ecosistema se registran aquí para ser localizados
 * por nombre, sin acoplarse a IP ni puerto. Sus propiedades se obtienen del
 * Config Server.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	/**
	 * Punto de entrada de la aplicación.
	 *
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}