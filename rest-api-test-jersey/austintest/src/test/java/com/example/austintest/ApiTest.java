package com.example.austintest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.example.austintest.domain.Friend;

public class ApiTest {
	private static String serverUri = "http://localhost:8080/webapp";

	@Test
	private static void makeFriends() {
		System.out.println("*****");
		Friend friend = new Friend();
		friend.setEmail("aa@qq.com");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serverUri + "/makefriends");
		Response response = target.request().buildPut(Entity.entity(friend, MediaType.APPLICATION_JSON)).invoke();
		response.close();
	}
}
