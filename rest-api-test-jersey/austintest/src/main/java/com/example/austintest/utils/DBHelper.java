package com.example.austintest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	public static final String url = "jdbc:mysql://127.0.0.1/api";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "1111";

	public static Connection conn = null;
	public PreparedStatement pst = null;

	public static Connection getDBConnection() {
		try {
			Class.forName(name);
			conn = DriverManager.getConnection(url, user, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void close() {
		try {
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}