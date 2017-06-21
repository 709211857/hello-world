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

import com.example.austintest.domain.Friend;

public class DBUtil {

	static String sql = null;
	private static PreparedStatement psql = null;
	private static ResultSet rs = null;
	private static ResultSet rs_ = null;

	public static void createFriendsConnection(Friend friend) throws Exception {

		Connection conn = DBHelper.getDBConnection();

		try {

			psql = conn.prepareStatement(
					"insert into friend_rel (person1_email,person2_email,isfriend) " + "values(?,?,true)");
			String emailArr[] = friend.getFriends();
			psql.setString(1, emailArr[0]);
			psql.setString(2, emailArr[1]);

			psql.executeUpdate();

			DBHelper.close();
			conn.close();
			psql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// throw Exception();
		}
	}

	public static List retrieveFriendLists(Friend friend) {
		List list = null;
		Connection conn = DBHelper.getDBConnection();
		PreparedStatement psql = null;

		try {

			psql = conn.prepareStatement("select person2_email from  friend_rel where person1_email='"
					+ friend.getEmail() + "'" + "union select person1_email from  friend_rel where person2_email='"
					+ friend.getEmail() + "'");

			rs = psql.executeQuery();
			list = convertToList(rs);
			DBHelper.close();
			conn.close();
			psql.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List retrieveCommonFriends(Friend friend) {
		List list = null;
		List list_ = null;
		List commonlist = null;
		Connection conn = DBHelper.getDBConnection();
		PreparedStatement psql = null;
		PreparedStatement psql_ = null;

		try {

			psql = conn.prepareStatement("select person2_email from  friend_rel where person1_email='"
					+ friend.getFriends()[0] + "'" + "union select person1_email from  friend_rel where person2_email='"
					+ friend.getFriends()[0] + "'");

			psql_ = conn.prepareStatement("select person2_email from  friend_rel where person1_email='"
					+ friend.getFriends()[1] + "'" + "union select person1_email from  friend_rel where person2_email='"
					+ friend.getFriends()[1] + "'");
			rs = psql.executeQuery();
			rs_ = psql_.executeQuery();
			list = convertToList(rs);
			list_ = convertToList(rs_);
			commonlist = calculateCommonRes(list, list_);

			DBHelper.close();
			conn.close();
			psql.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return commonlist;
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

			e.printStackTrace();
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
