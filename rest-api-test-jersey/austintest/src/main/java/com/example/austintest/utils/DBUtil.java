package com.example.austintest.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.austintest.domain.Friend;
import com.example.austintest.exception.FriendResourcesException;

public class DBUtil {
	private static final Logger LOG = LoggerFactory.getLogger(DBUtil.class);
	static String sql = null;
	private static PreparedStatement psql = null;
	private static PreparedStatement psql1 = null;
	private static PreparedStatement psql2 = null;
	private static ResultSet rs = null;

	private DBUtil() {

	}

	public static void createFriendsConnection(Friend friend) throws FriendResourcesException {

		Connection conn = DBHelper.getDBConnection();

		ResultSet rs_1 = null;
		ResultSet rs_2 = null;
		try {
			String[] emailArr = friend.getFriends();

			psql1 = conn.prepareStatement(
					"select * from friend_rel where  (person1_email=? and person2_email=?) or (person1_email=? and person2_email=?)");
			psql1.setString(1, emailArr[0]);
			psql1.setString(2, emailArr[1]);
			psql1.setString(3, emailArr[1]);
			psql1.setString(4, emailArr[0]);
			rs_1 = psql1.executeQuery();
			if (rs_1.next() && rs_1.getString("person1_email") != null) {

				throw new FriendResourcesException(
						"We have already created friend connection for these users ,no need to be created again!",
						"API001");

			}

			psql2 = conn.prepareStatement(
					"select * from block_rel where  (requestor=? and target=?) or (requestor=? and target=?)");
			psql2.setString(1, emailArr[0]);
			psql2.setString(2, emailArr[1]);
			psql2.setString(3, emailArr[1]);
			psql2.setString(4, emailArr[0]);
			rs_2 = psql2.executeQuery();
			if (rs_2.next() && rs_2.getString("requestor") != null) {
				throw new FriendResourcesException("These two users are bocked,they can't create friend connection!",
						"API002");
			}

			psql = conn.prepareStatement(
					"insert into friend_rel (person1_email,person2_email,isblock) " + "values(?,?,false)");
			psql.setString(1, emailArr[0]);
			psql.setString(2, emailArr[1]);
			psql.executeUpdate();

		} catch (FriendResourcesException e) {
			LOG.debug(e.getMessage(),e);
			throw new FriendResourcesException(e.getMessage(), e.getErrorCode());
		}catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (psql != null) {
					psql.close();
				}
				if (psql1 != null) {
					psql1.close();
				}
				if (psql2 != null) {
					psql2.close();
				}
			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);

			}
		}

	}

	public static List retrieveFriendLists(Friend friend) {
		List list = null;
		Connection conn = DBHelper.getDBConnection();

		try {

			psql = conn.prepareStatement("select person2_email from  friend_rel where person1_email= ? union "
					+ "select person1_email from  friend_rel where person2_email=?");

			psql.setString(1, friend.getEmail());
			psql.setString(2, friend.getEmail());
			rs = psql.executeQuery();
			list = convertToList(rs);

		} catch (SQLException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
				if (psql != null) {
					psql.close();
				}

			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);

			}
		}
		return list;
	}

	public static List retrieveCommonFriends(Friend friend) {
		List list = null;
		List list_ = null;
		List commonlist = null;
		Connection conn = DBHelper.getDBConnection();
		ResultSet rs1 = null;
		try {

			psql = conn.prepareStatement(
					"select person2_email from  friend_rel where person1_email=? union select person1_email from  friend_rel where person2_email=?");

			psql.setString(1, friend.getFriends()[0]);
			psql.setString(2, friend.getFriends()[0]);

			psql1 = conn.prepareStatement(
					"select person2_email from  friend_rel where person1_email=? union select person1_email from  friend_rel where person2_email=?");

			psql1.setString(1, friend.getFriends()[1]);
			psql1.setString(2, friend.getFriends()[1]);
			rs = psql.executeQuery();
			rs1 = psql1.executeQuery();
			list = convertToList(rs);
			list_ = convertToList(rs1);
			commonlist = calculateCommonRes(list, list_);

		} catch (SQLException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			try {

				conn.close();

				if (psql != null) {
					psql.close();
				}
				if (psql1 != null) {
					psql1.close();
				}

			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);

			}
		}
		return commonlist;
	}

	public static void subscribeUpdates(Friend friend) throws Exception {

		Connection conn = DBHelper.getDBConnection();

		try {
			psql = conn.prepareStatement("insert into update_rel (requestor,target,status) " + "values(?,?,true)");

			psql.setString(1, friend.getRequestor());
			psql.setString(2, friend.getTarget());

			psql.executeUpdate();

		} catch (SQLException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
				if (psql != null) {
					psql.close();
				}

			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);

			}
		}
	}

	public static void blockUpdates(Friend friend) throws Exception {

		Connection conn = DBHelper.getDBConnection();

		try {

			psql = conn.prepareStatement("insert into block_rel (requestor,target,status) " + "values(?,?,true)");

			psql.setString(1, friend.getRequestor());
			psql.setString(2, friend.getTarget());

			psql.executeUpdate();

		} catch (SQLException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
				if (psql != null) {
					psql.close();
				}

			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);

			}
		}
	}

	/**
	 * whole db sql as below select distinct a.target from ( select target from
	 * update_rel where requestor='andy@qq.com' union select person1_email from
	 * friend_rel where person2_email='andy@qq.com' union select person2_email
	 * from friend_rel where person1_email='andy@qq.com'
	 * 
	 * ) a where a.target not in
	 * 
	 * (select target from block_rel where requestor='andy@qq.com')
	 * 
	 * 
	 * @param friend
	 * @return
	 */
	public static List retrieveUpdateLists(Friend friend) {
		List list = null;
		Connection conn = DBHelper.getDBConnection();

		try {

			psql = conn.prepareStatement("select  distinct a.target from" + "("

					+ "select  target from update_rel where requestor=?"

					+ " union "

					+ "select person1_email from friend_rel  where person2_email=? "

					+ "union "

					+ "select person2_email from friend_rel  where person1_email=?"

					+ ") a  where a.target not in"

					+ "(select  target from block_rel where requestor=?)");

			psql.setString(1, friend.getSender());
			psql.setString(2, friend.getSender());
			psql.setString(3, friend.getSender());
			psql.setString(4, friend.getSender());

			rs = psql.executeQuery();
			list = convertToList(rs);

			String[] mentioned = EmailRetrive.retriveValidMail(friend.getText()).split(",");
			for (String email : mentioned) {
				Map<String, Object> rowData = new HashMap<String, Object>();
				rowData.put("target", email);
				list.add(rowData);

			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
				if (psql != null) {
					psql.close();
				}

			} catch (SQLException e) {
				LOG.error(e.getMessage(), e);

			}
		}
		return list;
	}

	public static List convertToList(ResultSet rs) {
		List list = new ArrayList();
		ResultSetMetaData md;
		try {
			md = rs.getMetaData();

			int columnCount = md.getColumnCount();
			while (rs.next()) {
				Map<String, Object> rowData = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getObject(i));
				}
				list.add(rowData);

			}
		} catch (Exception e) {

			throw new RuntimeException(e);
		}
		return list;
	}

	public static List calculateCommonRes(List list, List list_) {
		List commonlist = new ArrayList();
		List<Map<String, Object>> first = list;
		List<Map<String, Object>> second = list_;
		Collection<Map<String, Object>> common = CollectionUtils.intersection(first, second);

		Iterator<Map<String, Object>> it = common.iterator();
		while (it.hasNext()) {
			commonlist.add(it.next());

		}
		return commonlist;

	}
}
