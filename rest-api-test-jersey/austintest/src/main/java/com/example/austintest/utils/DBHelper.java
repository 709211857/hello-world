package com.example.austintest.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBHelper {

	private static final Logger LOG = LoggerFactory.getLogger(DBHelper.class);
	private static final String PROP_PATH = "\\src\\main\\resources\\db.properties";
	public PreparedStatement pst = null;

	public static Connection getDBConnection() {
		Connection conn = null;

		Properties props = new Properties();
		try {
			ClassLoader.getSystemResource("").getPath();
			String abspath = ClassLoader.getSystemResource("").getPath();
			String rightPath = abspath.substring(0, abspath.indexOf("target") - 1);
			props.load(new FileInputStream(rightPath + PROP_PATH));
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
		String driver = props.getProperty("driver");
		String url = props.getProperty("url");
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {

			throw new RuntimeException(e);
		}
		return conn;
	}

}