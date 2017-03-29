package readFilesPackage;

import hibernatePackage.Course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import daoPackage.CourseDAO;

public class DataAccess {

	public static void main(String[] args) throws SQLException {
		ReadExcel readExcel = new ReadExcel();
		String[] arguments = new String[] { "" };
		readExcel.main(arguments);

		ReadWordFile readWord = new ReadWordFile();
		readWord.main(arguments);

		Connection conn = DriverManager.getConnection("jdbc:postgresql://dev.vk.edu.ee:5432/GroupWork?currentSchema=project", "t131566", "t131566");

		CourseDAO dao = new CourseDAO();
		String method = "";
		boolean exit = method == "7";
		do {
			System.out.println("1-find course by name 2-add new course 3-update course data 4-delete course 7-exit \nPlease make your choice: ");
			Scanner scanner = new Scanner(System.in);
			method = scanner.nextLine();

			switch (method) {
			case "1":
				Find(scanner, dao);
				break;
			case "2":
				Add(scanner, dao);
				break;
			case "3":
				Update(scanner, dao);
				break;
			case "4":
				Delete(scanner, dao);
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

	private static void Find(Scanner scanner, CourseDAO dao) throws SQLException {
		System.out.println("Enter course name: ");
		String courseName = scanner.nextLine();
		System.out.println("You entered: " + courseName);

		List<Course> courses = dao.findCourseByName(courseName);

		if (courses.isEmpty()) {
			System.out.println("Course with name " + courseName + " not found");
		} else {
			for (Course course : courses) {
				System.out.println("Code:" + course.getCode() + " Name:" + course.getName() + " Lectureship:" + course.getLectureship());
			}
		}
	}

	private static void Add(Scanner scanner, CourseDAO dao) throws SQLException {
		System.out.println("Enter code for course: ");
		String courseCode = scanner.nextLine();
		System.out.println("Enter name for course: ");
		String courseName = scanner.nextLine();
		System.out.println("Enter lectureship for course: ");
		String lectureship = scanner.nextLine();
		String addedCourse = dao.AddCourse(courseCode, courseName, lectureship);
		System.out.println(addedCourse);
	}

	private static void Update(Scanner scanner, CourseDAO dao) throws SQLException {

		System.out.println("Enter course code you want to update : ");
		String courseCode = scanner.nextLine();
		Course course = dao.FindCourseByCode(courseCode.toLowerCase());
		if (course != null) {
			System.out.println("If you do not want to update a field, leave it empty;");
			System.out.println("Enter course name : ");
			String courseName = scanner.nextLine();
			System.out.println("Enter course lectureship : ");
			String lectureship = scanner.nextLine();
			String result = dao.UpdateCourse(course.getId(), courseName, lectureship, course.getCode());
			System.out.println(result);
		} else {
			System.out.println("Course with code " + courseCode + " does not exist");
		}
	}

	private static void Delete(Scanner scanner, CourseDAO dao) throws SQLException {
		System.out.println("Enter course code you want to delete : ");
		String courseCode = scanner.nextLine();
		String result = dao.DeleteCourse(courseCode);
		System.out.println(result);
	}
}
