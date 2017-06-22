package com.example.austintest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.austintest.domain.Friend;

public class FriendResourcesTest {
	private static String serverUri = "http://localhost:8080/webapp/friends";
	private static final Logger LOG = LoggerFactory.getLogger(FriendResourcesTest.class);

	public static void main(String[] args) {
		makeFriends();
	}

	public static void makeFriends() {

		LOG.debug("begin to do junit test for make friends");
		Friend friend = new Friend();
		String friends[] = { "andy11@qq.com", "john11@example.com" };
		friend.setFriends(friends);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serverUri + "/makefriends");
		Response response = target.request().buildPut(Entity.entity(friend, MediaType.APPLICATION_JSON)).invoke();
		response.close();

	}
	
	
	
	public static void retrieveFriendList() {

		LOG.debug("begin to test retrieveFriendList");
		Friend friend = new Friend();
		
		friend.setEmail("pp@qq.com");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serverUri + "/retrievefriends");
		Response response = target.request().buildPost(Entity.entity(friend, MediaType.APPLICATION_JSON)).invoke();
		response.close();

	}
	
	
	
	public static void retrieveCommonFriends() {

		LOG.debug("begin to test retrieveCommonFriends");
		Friend friend = new Friend();
		
		String friends[] = { "andy11@qq.com", "john11@example.com" };
		friend.setFriends(friends);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serverUri + "/commonfriends");
		Response response = target.request().buildPost(Entity.entity(friend, MediaType.APPLICATION_JSON)).invoke();
		response.close();

	}
	
	
	
	public static void subscribeupdates() {

		LOG.debug("begin to test subscribeupdates");
		Friend friend = new Friend();
		
		
		friend.setRequestor("aaa@qq.com");
		friend.setTarget("bb@qq.com");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serverUri + "/subscribeupdates");
		Response response = target.request().buildPut(Entity.entity(friend, MediaType.APPLICATION_JSON)).invoke();
		response.close();

	}
	
	
	public static void blockUpdates() {

		LOG.debug("begin to test blockUpdates");
		Friend friend = new Friend();
		
		
		friend.setRequestor("aaa@qq.com");
		friend.setTarget("bb@qq.com");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serverUri + "/blockupdates");
		Response response = target.request().buildPut(Entity.entity(friend, MediaType.APPLICATION_JSON)).invoke();
		response.close();

	}
	
	
	
	public static void retrieveUpdateList() {

		LOG.debug("begin to test retrieveUpdateList");
		Friend friend = new Friend();
		
		
		friend.setSender("aaa@qq.com");
		friend.setText("hello bb@qq.com");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(serverUri + "/retrieveupdateslist");
		Response response = target.request().buildPost(Entity.entity(friend, MediaType.APPLICATION_JSON)).invoke();
		response.close();

	}
}
