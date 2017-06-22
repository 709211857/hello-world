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
import com.example.austintest.exception.FriendResourcesException;
import com.example.austintest.response.ErrorResponse;
import com.example.austintest.response.RetrieveSuccessResponse;
import com.example.austintest.response.RetrieveUpdateSuccessResponse;
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
		ErrorResponse errorResponse = new ErrorResponse();
		try {
			DBUtil.createFriendsConnection(friend);
			response.setSuccess(true);
			LOG.debug("create friend connection scuccessfully!!!");

		} catch (FriendResourcesException e) {
			LOG.debug("got error!", e);
			errorResponse.setErrorCode(e.getErrorCode());
			errorResponse.setErrorMsg(e.getMessage());
			return Response.status(Response.Status.ACCEPTED).entity(errorResponse).build();

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

			StringBuilder sb = new StringBuilder();

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
			LOG.error(e.getMessage());
			throw new RuntimeException(e);

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

			StringBuilder sb = new StringBuilder();

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
			LOG.error(e.getMessage());
			throw new RuntimeException(e);

		}
		return Response.status(Response.Status.ACCEPTED).entity(response).build();
	}

	@PUT
	@Path("/subscribeupdates")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response subscribeUpdates(Friend friend) {
		LOG.debug("begin to subscribe update");
		SuccessResponse response = new SuccessResponse();
		try {
			DBUtil.subscribeUpdates(friend);
			response.setSuccess(true);
			LOG.debug("subscribe update scuccessfully!!!");

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return Response.status(Response.Status.ACCEPTED).entity(response).build();
	}

	@PUT
	@Path("/blockupdates")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response blockUpdates(Friend friend) {
		LOG.debug("begin to block update");
		SuccessResponse response = new SuccessResponse();
		try {
			DBUtil.blockUpdates(friend);
			response.setSuccess(true);
			LOG.debug("block update scuccessfully!!!");

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return Response.status(Response.Status.ACCEPTED).entity(response).build();
	}

	@POST
	@Path("/retrieveupdateslist")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response retrieveUpdateList(Friend friend) {
		LOG.debug("begin to retrieve can  receive update list");
		RetrieveUpdateSuccessResponse response = new RetrieveUpdateSuccessResponse();
		try {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = DBUtil.retrieveUpdateLists(friend);

			StringBuilder sb = new StringBuilder();

			for (Map<String, Object> map : list) {

				sb.append(map.get("target").toString().trim()).append(",");

			}
			if (sb.toString().length() > 0) {
				response.setRecipients(sb.toString().substring(0, sb.toString().length() - 1).split(","));
				response.setSuccess(true);
			} else {
				response.setRecipients(new String[] {});
				response.setSuccess(true);
			}
			LOG.debug(" update list receive scuccessfully!!!");

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return Response.status(Response.Status.ACCEPTED).entity(response).build();
	}

}