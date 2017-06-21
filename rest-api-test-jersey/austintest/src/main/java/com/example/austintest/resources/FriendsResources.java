package com.example.austintest.resources;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.austintest.domain.Friend;
import com.example.austintest.response.RetrieveSuccessResponse;
import com.example.austintest.response.SuccessResponse;
import com.example.austintest.utils.DBUtil;

@Path("/friends")
public class FriendsResources {

	private static final Logger LOG = LoggerFactory.getLogger(FriendsResources.class);

	/**
	 * create a friend connection
	 * 
	 * @param user
	 */
	@PUT
	@Path("/makefriends")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response putUser(Friend friend) {
		LOG.debug("begin to create friend connection");
		SuccessResponse response = new SuccessResponse();
		try {
			DBUtil.createFriendsConnection(friend);
			response.setSuccess(true);
			LOG.debug("create friend connection scuccessfully!!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.ACCEPTED).entity(response).build();
	}

	@POST
	@Path("/retrievefriends")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response retrieveFriendList(Friend friend) {
		LOG.debug("begin to retrieve friend list");
		RetrieveSuccessResponse response = new RetrieveSuccessResponse();
		try {
			List<Map<String, Object>> list = DBUtil.retrieveFriendLists(friend);

			StringBuffer sb = new StringBuffer();

			for (Map<String, Object> map : list) {

				sb.append(map.get("person2_email")).append(",");

			}
			if (sb.toString().length() > 0) {
				response.setFriends(sb.toString().substring(0, sb.toString().length() - 1).split(","));
				response.setSuccess(true);
			} else {
				response.setFriends(new String[] {});
				response.setSuccess(true);
			}
			response.setCount(list.size());

			LOG.debug("Friends List retrieve scuccessfully!!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.ACCEPTED).entity(response).build();
	}

	@POST
	@Path("/commonfriends")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response retrieveCommonFriends(Friend friend) {

		RetrieveSuccessResponse response = new RetrieveSuccessResponse();
		try {
			List<Map<String, Object>> list = DBUtil.retrieveCommonFriends(friend);

			StringBuffer sb = new StringBuffer();

			for (Map<String, Object> map : list) {

				sb.append(map.get("person2_email")).append(",");

			}
			if (sb.toString().length() > 0) {
				response.setFriends(sb.toString().substring(0, sb.toString().length() - 1).split(","));
				response.setSuccess(true);
			} else {
				response.setFriends(new String[] {});
				response.setSuccess(true);
			}

			response.setCount(list.size());
			LOG.debug("Friends List retrieve scuccessfully!!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.ACCEPTED).entity(response).build();
	}

	@POST
	@Path("/commonfriends")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void subscribeUpdates(Friend friend) {

		// TBD

	}

}