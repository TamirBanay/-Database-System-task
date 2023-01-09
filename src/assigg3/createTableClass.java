package assigg3;

import java.sql.*;

public class createTableClass {

	public static String dbName;
	public static String tableName;
	public String fild1;
	public static Statement stmt;
	public static Connection conn;
	public static String fild2;
	public static String fild3;

	public createTableClass(String dbName, String tableName, String fild2, String fild3, Connection conn,
			Statement stmt) {
		this.dbName = dbName;
		this.tableName = tableName;
		fild1 = "id INT AUTO_INCREMENT PRIMARY KEY";
		this.fild2 = fild2;
		this.fild3 = fild3;
		this.conn = conn;
		this.stmt = stmt;
	} 

	public static void createTable() {
		Statement stmt;
		String selectDB = "USE " + dbName;
		try {
			stmt = conn.createStatement();
			stmt.execute(selectDB);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String fild1 = "id INT AUTO_INCREMENT PRIMARY KEY";
		System.out.println("\n" + tableName);
		String createTableSQL = "CREATE TABLE " + tableName + "(" + fild1 + ", " + fild2 + " VARCHAR(200) NOT NULL, "
				+ fild3 + " VARCHAR(200) NOT NULL)";

		try {
			stmt = conn.createStatement();
			stmt.execute(createTableSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("table " + tableName + " created successfully...");
		String selectSQL = "SELECT * FROM " + tableName;
		try {
			stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(selectSQL);
			System.out.printf("the table fild: \n%s %s, %s", fild1, fild2, fild3);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
