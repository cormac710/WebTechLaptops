package com.cormacLaptops.test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilsDAOExt {

	Connection connection;

	private Connection getConnection() {
		Connection conn = null;
		final String url = "jdbc:mysql://localhost:3306/a00237795webtechTest";
		// user=root?password=admin

		try {
			// conn = DriverManager.getConnection(url, "root", "admin");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void deleteTable() {
		try {
			final Connection conn = getConnection();
			final Statement statement = conn.createStatement();
			final String sql = "DROP TABLE IF EXISTS laptop";
			statement.executeUpdate(sql);
			conn.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addThreeRows() {
		try {
			final Connection conn = getConnection();
			final Statement stmt;
			PreparedStatement preparedStatement = null;
			final String sql = "CREATE TABLE `laptop` (" + "`id` int(11) NOT NULL AUTO_INCREMENT, "
					+ "`make` varchar(255) DEFAULT NULL,`model`" + " varchar(255) DEFAULT NULL,"
					+ "`price` int(11) DEFAULT NULL," + "`ram` int(11) DEFAULT NULL,"
					+ "`hardDrive` int(11) DEFAULT NULL," + "`processor` varchar(255) DEFAULT NULL,"
					+ "`description` varchar(255) DEFAULT NULL,"
					+ "`image` varchar(255) DEFAULT NULL,PRIMARY KEY (`id`))";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			preparedStatement = conn.prepareStatement(
					"INSERT INTO `laptop`(`make`,`model`,`price`,`ram`,`hardDrive`,`processor`,`description`)VALUES (?, ?, ?, ?, ?, ?, ?)",
					new String[] { "id" });
			preparedStatement.setString(1, "Lenovo");
			preparedStatement.setString(2, "Q50");
			preparedStatement.setInt(3, 1000);
			preparedStatement.setInt(4, 8);
			preparedStatement.setInt(5, 1000);
			preparedStatement.setString(6, "i7");
			preparedStatement.setString(7, "solid business laptop");
			preparedStatement.executeUpdate();
			
			preparedStatement = conn.prepareStatement(
					"INSERT INTO `laptop`(`make`,`model`,`price`,`ram`,`hardDrive`,`processor`,`description`)VALUES (?, ?, ?, ?, ?, ?, ?)",
					new String[] { "id" });
			preparedStatement.setString(1, "MacBook");
			preparedStatement.setString(2, "Pro");
			preparedStatement.setInt(3, 1000);
			preparedStatement.setInt(4, 4);
			preparedStatement.setInt(5, 132);
			preparedStatement.setString(6, "i7");
			preparedStatement.setString(7, "A lovely Mac");
			preparedStatement.executeUpdate();
			
			preparedStatement = conn.prepareStatement(
					"INSERT INTO `laptop`(`make`,`model`,`price`,`ram`,`hardDrive`,`processor`,`description`)VALUES (?, ?, ?, ?, ?, ?, ?)",
					new String[] { "id" });
			preparedStatement.setString(1, "Dell");
			preparedStatement.setString(2, "Inspiron");
			preparedStatement.setInt(3, 289);
			preparedStatement.setInt(4, 5);
			preparedStatement.setInt(5, 5);
			preparedStatement.setString(6, "Intel Celeron");
			preparedStatement.setString(7, "11.6 Touchsceen 2 in 1");
			preparedStatement.executeUpdate();
			conn.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
