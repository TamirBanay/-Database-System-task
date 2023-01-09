//Tamir banay - 311492268
//Oz benacot - 315860213

package assigg3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.print.DocFlavor.INPUT_STREAM;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class menuClass {
	public static Connection conn;
	public static Statement stmt;
	public static Scanner sc = new Scanner(System.in);
	public static PreparedStatement preparedStatement;

	public menuClass(Connection conn, Statement stmt) {
		this.conn = conn;
		this.stmt = stmt;
	}

	public static void printLangTable() {
		System.out.print("\n");
		ResultSet resultSet;
		try {
			resultSet = stmt.executeQuery("SELECT * FROM language");
			while (resultSet.next()) {
				int language_id = resultSet.getInt("language_id");
				String name = resultSet.getString("name");
				System.out.println(language_id + " " + name);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void menu() {

		boolean loopCond = true;

		while (loopCond) {
			System.out.println(
					"\nwhat would you like to do? \n1. add movie \n2. add actore \n3. add info about actor \n4. ask for a sql query \n5. ask a query whit parametric \n6. checkout");
			int numMenu = sc.nextInt();
			sc.nextLine();

			switch (numMenu) {
			case 1: {
				System.out.println("insert the movie name");
				String title = sc.nextLine();
				System.out.println("insert the movie description");
				String description = sc.nextLine();
				printLangTable();
				System.out.println("insert a language by the table");
				int language_id = sc.nextInt();
				sc.nextLine();

				try {
					String insertSQL = "INSERT INTO film(title,description,language_id)VALUES(?,?,?)";
					PreparedStatement statement = conn.prepareStatement(insertSQL);

					statement.setString(1, title);
					statement.setString(2, description);
					statement.setInt(3, language_id);

					int rowsInserted = statement.executeUpdate();
					System.out.println(rowsInserted + " row(s) inserted");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				break;
			}
			case 2: {
				System.out.println("what the first name of the actor to add?");
				String first_name = sc.nextLine();
				System.out.println("what is the last name of the actor?");
				String last_name = sc.nextLine();
				String insertSQL = "INSERT INTO actor (first_name, last_name) VALUES ( ?, ?)";

				try {
					PreparedStatement statement = conn.prepareStatement(insertSQL);

					statement.setString(1, first_name);
					statement.setString(2, last_name);
					int rowsInserted = statement.executeUpdate();
					System.out.println(rowsInserted + " row(s) inserted");
					System.out.println(
							"add to table successfully.\n actor " + first_name + " " + last_name + " add to table");

				} catch (SQLException e1) {

					e1.printStackTrace();
				}

				break;
			}

			case 3: {
				int numCond = 0;
				while (numCond != 3) {
					System.out.println("Which fild to update? \n1. first name \n2. last name \n3. back to main manu");
					int fild = sc.nextInt();
					sc.nextLine();
					switch (fild) {
					case 1: {
						System.out.println("what is the ID of the name that you whant to change?");
						int id = sc.nextInt();
						sc.nextLine();

						System.out.println("what is the NEW NAME?");
						String newName = sc.next();
						sc.nextLine();

						Statement statement;
						try {
							statement = conn.createStatement();
							String insertSQL = "UPDATE actor SET first_name = '" + newName + "' WHERE" + " actor_id = "
									+ id;
							System.out.println(insertSQL);
							statement.addBatch(insertSQL);

							int rowsAffected = statement.executeUpdate(insertSQL);
							System.out.println(rowsAffected + " row(s) updated");

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}

					case 2: {
						System.out.println("what is the ID of the LAST name that you whant to change?");
						int id = sc.nextInt();
						sc.nextLine();

						System.out.println("what is the NEW LAST NAME?");
						String newLastName = sc.next();
						sc.nextLine();

						Statement statement;
						try {
							statement = conn.createStatement();
							String updateSQL = "UPDATE actor SET last_name = '" + newLastName + "' WHERE"
									+ " actor_id = " + id;
							statement.addBatch(updateSQL);

							int rowsAffected = statement.executeUpdate(updateSQL);
							System.out.println(rowsAffected + " row(s) updated");

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					case 3: {
						System.out.println("BACK TO MENU");
						numCond = 3;
						break;
					}
					}
				}
			}
				break;
			case 4: {

				System.out.println(
						"choose the type of query: \n1. SELECT \n2. INSERT \n3. UPDATE \n4. back to main manu");
				int numType = sc.nextInt();
				sc.nextLine();
				if (numType == 1) {
					System.out.print("Enter a SELECT query: ");
					String query = sc.nextLine();
					ResultSet resultSet = null;
					ResultSetMetaData metaData = null;
					PreparedStatement statement = null;

					try {
						statement = conn.prepareStatement(query);
						resultSet = statement.executeQuery();
						metaData = (ResultSetMetaData) resultSet.getMetaData();
						int columnCount = 0;
						columnCount = metaData.getColumnCount();

						while (resultSet.next()) {
							for (int i = 1; i <= columnCount; i++) {
								Object value = resultSet.getObject(i);
								if (value instanceof String) {
									System.out.print((String) value + " ");
								} else if (value instanceof Integer) {
									System.out.print((Integer) value + " ");
								} else if (value instanceof Double) {
									System.out.print((Double) value + " ");
								} else {
									System.out.print(value + " ");
								}
							}
							System.out.println();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else if (numType == 2) {
					// update case
				} else if (numType == 3) {
					// insert case
				} else if (numType == 4) {
					System.out.println("\nyou back to main manu");

					break;

				} else {
					System.out.println("worng choose!! you back to main manu");
					break;

				}

			}

				break;

			case 5: {
				System.out.println(
						"choose what u need to do\na. serch movie by word in the movie\nb. Search all movies appeared actor by actor name\nc. Search all actors who appeared in movies in a certain language by language\nd. Searching for a movie that, according to the database, is played by exactly X actors\ne. Search for an English language movie that did not star Robert De Niro\nf. back to main manu ");
				char choose = sc.nextLine().charAt(0);
				switch (choose) {
				case 'a': {
					System.out.println("what is the word that you what to serch whit?");
					String serchPartWord = sc.next();
					String insertSQL = "SELECT * FROM film WHERE title LIKE ?";
					PreparedStatement statement = null;
					try {
						statement = conn.prepareStatement(insertSQL);
						statement.setString(1, "%" + serchPartWord + "%");

					} catch (SQLException e) {
						e.printStackTrace();
					}
					try {
						ResultSet resultSet = statement.executeQuery();
						while (resultSet.next()) {
							int id = resultSet.getInt("film_id");
							String name = resultSet.getString("title");

							System.out.println(id + ", " + name);
						}
					} catch (SQLException e) {

						e.printStackTrace();
					}

					break;
				}
				case 'b': {
					String insertSQL = "SELECT film.title FROM film JOIN film_actor ON film.film_id = film_actor.film_id JOIN actor ON actor.actor_id = film_actor.actor_id WHERE actor.first_name =? AND actor.last_name =? ";

					try {

						PreparedStatement statement = conn.prepareStatement(insertSQL);
						System.out.println("Enter the actor first name:");
						String firstName = sc.nextLine();
						System.out.println("Enter the actor's last name:");
						String lastName = sc.nextLine();

						statement.setString(1, firstName);
						statement.setString(2, lastName);
						ResultSet resultSet = statement.executeQuery();
						System.out.println("MOVIES LIST:");
						while (resultSet.next()) {
							String title = resultSet.getString("title");
							System.out.println(title);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case 'c': {
					printLangTable();
					System.out.println("insert a language by the table");
					int language_id = sc.nextInt();
					sc.nextLine();

					String insertSQL = "SELECT DISTINCT first_name FROM actor  JOIN film_actor ON actor.actor_id = film_actor.actor_id JOIN film ON film.film_id = film_actor.film_id WHERE film.language_id = ?";
					PreparedStatement statement;
					try {
						statement = conn.prepareStatement(insertSQL);
						statement.setInt(1, language_id);
						ResultSet resultSet = statement.executeQuery();
						System.out.println("ACTORS LIST:");
						while (resultSet.next()) {
							String name = resultSet.getString("first_name");
							System.out.println(name);
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				}
				case 'd': {
					System.out.println("choose the num of actors in the movie");
					int numOfActors = sc.nextInt();
					sc.nextLine();
					String insertSQL = "SELECT title FROM film JOIN film_actor ON film.film_id = film_actor.film_id GROUP BY film.film_id HAVING COUNT(film.film_id) = ?";
					PreparedStatement statement;
					ResultSet resultSet;
					try {
						statement = conn.prepareStatement(insertSQL);
						statement.setInt(1, numOfActors);
						resultSet = statement.executeQuery();
						System.out.println("MOVIES LIST:");
						while (resultSet.next()) {
							String name = resultSet.getString("title");
							System.out.println(name);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					break;

				}
				case 'e': {

					String insertSQL = "SELECT film.title FROM film JOIN film_actor ON film.film_id = film_actor.film_id JOIN actor ON actor.actor_id = film_actor.actor_id WHERE actor.first_name != ? AND actor.last_name != ? AND film.language_id =1";

					try {

						PreparedStatement statement = conn.prepareStatement(insertSQL);
						statement.setString(1, "ROBBERT");
						statement.setString(2, "DE NIRO");

						ResultSet resultSet = statement.executeQuery();
						System.out.println("MOVIES LIST:");
						while (resultSet.next()) {
							String title = resultSet.getString("title");
							System.out.println(title);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;

				}
				case 'f': {
					System.out.println("you back to main manu");

					break;

				}
				}

			}

				break;

			case 6: {
				loopCond = false;
				System.out.println("EXIT");

				sc.close();
				break;

			}

			}

		}

	}

}
