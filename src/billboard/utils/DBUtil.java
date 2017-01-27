package billboard.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import billboard.exception.SQLRuntimeException;

//DBコネクション関係のユーティリティ
public class DBUtil {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/billboard";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	static {

		try {
			Class.forName(DRIVER);
		} catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	//コネクションを取得
	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(false);
			return connection;
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}

	//コミットします
	public static void commit(Connection connection) {
		try {
			connection.commit();
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}
	//ロールバックします
	public static void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		}
	}
}
