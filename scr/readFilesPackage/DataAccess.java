package readFilesPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

public class DataAccess {

	public static void main(String[] args) throws SQLException {
		ReadExcel readExcel = new ReadExcel();
		String[] arguments = new String[] { "" };
		readExcel.main(arguments);

		ReadWordFile readWord = new ReadWordFile();
		readWord.main(arguments);

		Connection conn = DriverManager.getConnection("jdbc:postgresql://dev.vk.edu.ee:5432/GroupWork?currentSchema=project", "t131566", "t131566");

		String method = "";
		boolean exit = method == "7";
		do {
			System.out.println("1-find course by name 2-add new course 3-update course data 4-delete course 7-exit \nPlease make your choise: ");
			Scanner scanner = new Scanner(System.in);
			method = scanner.nextLine();

			switch (method) {
			case "1":
				Find(conn, scanner);
				break;
			case "2":
				Add(conn, scanner);
				break;
			case "3":
				Update(conn, scanner);
				break;
			case "4":
				Delete(conn, scanner);
				break;
			case "7":
				exit = true;
				System.exit(0);
				break;
			default:
				;
				break;
			}
		} while (!exit);
	}

	private static void Find(Connection conn, Scanner scanner) throws SQLException {
		System.out.println("Enter course name: ");
		String courseName = scanner.nextLine();
		System.out.println("You entered: " + courseName);

		PreparedStatement preparedStatementGetCourse = conn.prepareStatement("SELECT * FROM course where lower(name) like ?");
		preparedStatementGetCourse.setString(1, courseName.toLowerCase() + "%");
		ResultSet resultCourse = preparedStatementGetCourse.executeQuery();

		if (resultCourse.next()) {
			do {
				String code = resultCourse.getString(2);
				String name = resultCourse.getString(3);
				String lecture = resultCourse.getString(4);
				System.out.println("Code:" + code + " Name:" + name + " Lectureship:" + lecture);
			} while (resultCourse.next());
		} else {
			System.out.println("Course with name " + courseName + " not found");
		}
	}

	private static void Add(Connection conn, Scanner scanner) throws SQLException {
		System.out.println("Enter code for course: ");
		String courseCode = scanner.nextLine();
		System.out.println("Enter name for course: ");
		String courseName = scanner.nextLine();
		System.out.println("Enter lectureship for course: ");
		String lectureship = scanner.nextLine();

		PreparedStatement preparedStatementGetCourse = conn.prepareStatement("SELECT * FROM course where lower(code)=? and lower(name)=? and lower(lectureship)=?");
		preparedStatementGetCourse.setString(1, courseCode.toLowerCase());
		preparedStatementGetCourse.setString(2, courseName.toLowerCase());
		preparedStatementGetCourse.setString(3, lectureship.toLowerCase());

		ResultSet resultCourse = preparedStatementGetCourse.executeQuery();

		if (resultCourse.next()) {
			System.out.println("Course with name " + courseName + " and code " + courseCode + " and lectureship " + lectureship + " already exists");
		} else {
			PreparedStatement preparedStatementNewCourse = conn.prepareStatement("INSERT INTO course (code,name,lectureship) VALUES (?,?,?)");
			preparedStatementNewCourse.setString(1, courseCode);
			preparedStatementNewCourse.setString(2, courseName);
			preparedStatementNewCourse.setString(3, lectureship);
			preparedStatementNewCourse.executeUpdate();
			System.out.println("Successfully inserted new course: " + courseCode + " " + courseName + " " + lectureship);
		}
	}

	private static void Update(Connection conn, Scanner scanner) throws SQLException {

		System.out.println("Enter course code you want to update : ");
		String courseCode = scanner.nextLine();

		PreparedStatement preparedStatementGetCourse = conn.prepareStatement("SELECT * FROM course where lower(code)=?");
		preparedStatementGetCourse.setString(1, courseCode.toLowerCase());

		ResultSet resultCourse = preparedStatementGetCourse.executeQuery();

		if (resultCourse.next()) {
			int courseId = resultCourse.getInt(1);
			System.out.println("If you do not want to update a field, leave it empty;");
			System.out.println("Enter course name : ");
			String courseName = scanner.nextLine();
			if (!courseName.isEmpty()) {
				PreparedStatement preparedStatementNewCourse = conn.prepareStatement("UPDATE course SET name=? where id=?");
				preparedStatementNewCourse.setString(1, courseName);
				preparedStatementNewCourse.setInt(2, courseId);
				preparedStatementNewCourse.executeUpdate();
				System.out.println("Successfully updated course " + courseCode + " name to " + courseName);
			}
			System.out.println("Enter course lectureship : ");
			String lectureship = scanner.nextLine();
			if (!lectureship.isEmpty()) {
				PreparedStatement preparedStatementNewCourse = conn.prepareStatement("UPDATE course SET lectureship=? where id=?");
				preparedStatementNewCourse.setString(1, lectureship);
				preparedStatementNewCourse.setInt(2, courseId);
				preparedStatementNewCourse.executeUpdate();
				System.out.println("Successfully updated course " + courseCode + " lectureship to " + lectureship);
			}
		} else {
			System.out.println("Course with code " + courseCode + " does not exist");
		}

	private static void Delete(Connection conn, Scanner scanner) throws SQLException {

		System.out.println("Enter course code you want to delete : ");
		String courseCode = scanner.nextLine();

		PreparedStatement preparedStatementGetCourse = conn.prepareStatement("SELECT * FROM course where lower(code)=?");
		preparedStatementGetCourse.setString(1, courseCode.toLowerCase());

		ResultSet resultCourse = preparedStatementGetCourse.executeQuery();

		if (resultCourse.next()) {
			int courseId = resultCourse.getInt(1);
			PreparedStatement preparedStatementNewCourse = conn.prepareStatement("DELETE FROM COURSE where id=?");
			preparedStatementNewCourse.setInt(1, courseId);
			preparedStatementNewCourse.executeUpdate();
			System.out.println("Successfully deleted course " + courseCode);
		} else {
			System.out.println("Course with code " + courseCode + " does not exist");
		}
	}
}
