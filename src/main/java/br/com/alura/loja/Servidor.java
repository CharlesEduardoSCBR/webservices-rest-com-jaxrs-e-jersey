package br.com.alura.loja;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Servidor {

	public static void main(String[] args) throws IOException {
		
		HttpServer server = inicializaServidor();

		System.out.println("Servidor rodando");
		System.in.read();
		finalizaServidor(server);
	}

	public static HttpServer inicializaServidor() {
		URI uri = URI.create("http://localhost:8080/");
		ResourceConfig configuration = new ResourceConfig().packages("br.com.alura.loja");
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri , configuration);
		return server;
	}
	
	public static void finalizaServidor(HttpServer server) {
		server.stop();
	}
}
