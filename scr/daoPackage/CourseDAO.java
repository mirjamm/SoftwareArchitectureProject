package daoPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hibernatePackage.Course;

public class CourseDAO {
	public CourseDAO() {
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:postgresql://dev.vk.edu.ee:5432/GroupWork?currentSchema=project", "t131566", "t131566");
		return conn;
	}

	public List<Course> findCourseByName(String courseName) throws SQLException {
		List<Course> courses = new ArrayList<Course>();
		Connection conn = getConnection();
		PreparedStatement preparedStatementGetCourse = conn.prepareStatement("SELECT * FROM course where lower(name) like ?");
		preparedStatementGetCourse.setString(1, courseName.toLowerCase() + "%");
		ResultSet resultCourse = preparedStatementGetCourse.executeQuery();
		if (resultCourse.next()) {
			Course course = new Course();
			course.setCode(resultCourse.getString(2));
			course.setName(resultCourse.getString(3));
			course.setLectureship(resultCourse.getString(4));
			courses.add(course);
		}
		return courses;
	}

	public String AddCourse(String courseCode, String courseName, String lectureship) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement preparedStatementGetCourse = conn.prepareStatement("SELECT * FROM course where lower(code)=? and lower(name)=? and lower(lectureship)=?");
		preparedStatementGetCourse.setString(1, courseCode.toLowerCase());
		preparedStatementGetCourse.setString(2, courseName.toLowerCase());
		preparedStatementGetCourse.setString(3, lectureship.toLowerCase());
		ResultSet resultCourse = preparedStatementGetCourse.executeQuery();
		if (resultCourse.next()) {
			return "Course with name " + courseName + " and code " + courseCode + " and lectureship " + lectureship + " already exists";
		} else {
			PreparedStatement preparedStatementNewCourse = conn.prepareStatement("INSERT INTO course (code,name,lectureship) VALUES (?,?,?)");
			preparedStatementNewCourse.setString(1, courseCode);
			preparedStatementNewCourse.setString(2, courseName);
			preparedStatementNewCourse.setString(3, lectureship);
			preparedStatementNewCourse.executeUpdate();
			return "Successfully inserted new course: " + courseCode + " " + courseName + " " + lectureship;
		}
	}

	public Course FindCourseByCode(String courseCode) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement preparedStatementGetCourse = conn.prepareStatement("SELECT * FROM course where lower(code)=?");
		preparedStatementGetCourse.setString(1, courseCode.toLowerCase());

		Course course = new Course();
		ResultSet resultCourse = preparedStatementGetCourse.executeQuery();
		if (resultCourse.next()) {
			course.setId(resultCourse.getInt(1));
			course.setCode(resultCourse.getString(2));
			course.setName(resultCourse.getString(3));
			course.setLectureship(resultCourse.getString(4));
		}
		return course;
	}

	public String UpdateCourse(int courseId, String courseName, String lectureship, String courseCode) throws SQLException {
		Connection conn = getConnection();
		String returnedStatement = "";
		if (!courseName.isEmpty()) {
			PreparedStatement preparedStatementNewCourse = conn.prepareStatement("UPDATE course SET name=? where id=?");
			preparedStatementNewCourse.setString(1, courseName);
			preparedStatementNewCourse.setInt(2, courseId);
			preparedStatementNewCourse.executeUpdate();
			returnedStatement = "Successfully updated course " + courseCode + " name to " + courseName;
		}

		if (!lectureship.isEmpty()) {
			PreparedStatement preparedStatementNewCourse = conn.prepareStatement("UPDATE course SET lectureship=? where id=?");
			preparedStatementNewCourse.setString(1, lectureship);
			preparedStatementNewCourse.setInt(2, courseId);
			preparedStatementNewCourse.executeUpdate();
			returnedStatement = returnedStatement + "\nSuccessfully updated course " + courseCode + " lectureship to " + lectureship;
		}
		return returnedStatement;
	}

	public String DeleteCourse(String courseCode) throws SQLException {
		Connection conn = getConnection();
		Course course = FindCourseByCode(courseCode);
		String returnedStatement = "";
		if (course != null) {
			int courseId = course.getId();
			PreparedStatement preparedStatementNewCourse = conn.prepareStatement("DELETE FROM COURSE where id=?");
			preparedStatementNewCourse.setInt(1, courseId);
			preparedStatementNewCourse.executeUpdate();
			returnedStatement = "Successfully deleted course " + courseCode;
		}else{
			returnedStatement="Course with code " + courseCode + " does not exist";
		}
		return returnedStatement;
	}
}
