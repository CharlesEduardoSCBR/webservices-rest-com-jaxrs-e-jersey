package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import junit.framework.Assert;

public class ClienteTeste {

	private HttpServer server;

	@Before
	public void before() {
		this.server = Servidor.inicializaServidor();
	}

	@After
	public void after() {
		server.stop();
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("http://localhost:8080");
		String conteudo = target.path("/carrinhos/1").request().get(String.class);
		System.out.println(conteudo);

		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);

		Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
	}

	@Test
	public void testaPost() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");

		Carrinho carrinho = new Carrinho();
		carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
		carrinho.setRua("Rua Vergueiro");
		carrinho.setCidade("Sao Paulo");

		String xml = carrinho.toXML();
		
		Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

		javax.ws.rs.core.Response response = target.path("/carrinhos").request().post(entity);
		Assert.assertEquals(201, response.getStatus());
	}
}
