package readFilesPackage;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class ReadExcel {

	public static void main(String[] args) {

		try {
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("C:/workspace/DataSourceProject/DataSourceProject/src/Koormused_test.xls"));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFSheet sheet = wb.getSheetAt(0);
		    HSSFRow row;

		    int rows; 
		    rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0;
		    int tmp = 0;

		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }

		    Connection conn = DriverManager.getConnection("jdbc:postgresql://dev.vk.edu.ee:5432/GroupWork?currentSchema=project", 
		    		"t131566", "t131566");

		    
		    for(int r = 11; r < rows+11; r++) {
		        row = sheet.getRow(r);
		        if(row != null) {
		        	System.out.println(row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue()+" "+row.getCell(3).getStringCellValue());
		        	
		        	PreparedStatement preparedStatementRoles=conn.prepareStatement("SELECT * FROM role");
		        	ResultSet resultRoles=preparedStatementRoles.executeQuery();
		        	
		        	if(!resultRoles.next()) {
		        	conn.setAutoCommit(false);
		        	PreparedStatement preparedStatementRole=conn.prepareStatement("INSERT INTO role (rolename) VALUES (?)");
		        	preparedStatementRole.setString(1, "teacher");
		        	preparedStatementRole.addBatch();
		        	preparedStatementRole.setString(1, "student");
		        	preparedStatementRole.addBatch();
		        	preparedStatementRole.setString(1, "admin");
		        	preparedStatementRole.addBatch();
		        	preparedStatementRole.executeBatch();
		        	conn.commit();
		        	}
		        	
		        	PreparedStatement preparedStatementLanguages=conn.prepareStatement("SELECT * FROM language where name=?");
		        	preparedStatementLanguages.setString(1,row.getCell(13).getStringCellValue());
		        	ResultSet resultLanguages=preparedStatementLanguages.executeQuery();
		        	
		        	if(!resultLanguages.next()) {
		        	PreparedStatement preparedStatementLanguage=conn.prepareStatement("INSERT INTO language (name) VALUES (?)");
		        	preparedStatementLanguage.setString(1, row.getCell(13).getStringCellValue());
		        	preparedStatementLanguage.executeUpdate();
		        	}
		        	
		        	PreparedStatement preparedStatementTeacher=conn.prepareStatement("SELECT * FROM person where lastname=? and firstname=?");
		        	String name=row.getCell(12).getStringCellValue();
		        	String[] parts=name.split("\\.");
		        	String firstname=parts[0];
		        	String lastname=parts[1];
		        	
		        	preparedStatementTeacher.setString(1,firstname);
		        	preparedStatementTeacher.setString(2, lastname);
		        	
		        	ResultSet resultTeacher=preparedStatementTeacher.executeQuery();	        	
		        	if(!resultTeacher.next()) {
		        		PreparedStatement preparedStatementNewTeacher= conn.prepareStatement("INSERT INTO person (firstname,lastname,roleid) VALUES (?,?,?)");
			        	PreparedStatement preparedStatementTeacherRole=conn.prepareStatement("SELECT * FROM role where rolename='teacher'");
			        	int roleId=0;
			        	ResultSet resultTeacherRole=preparedStatementTeacherRole.executeQuery();
			        	if(resultTeacherRole.next()){
			        	roleId=resultTeacherRole.getInt(1);
			        	}
			        	
		        		preparedStatementNewTeacher.setString(1, firstname);
		        		preparedStatementNewTeacher.setString(2, lastname);
		        		preparedStatementNewTeacher.setInt(3, roleId);
		        		preparedStatementNewTeacher.executeUpdate();
		        	}
		        	
		        	
		        	PreparedStatement preparedStatementCourseCode=conn.prepareStatement("SELECT * FROM course where code=?");
		        	preparedStatementCourseCode.setString(1,row.getCell(2).getStringCellValue());
		        	ResultSet resultCourse=preparedStatementCourseCode.executeQuery();
		        	
		        	if(!resultCourse.next()) {
		        	PreparedStatement preparedStatementCourse= conn.prepareStatement("INSERT INTO course (code,name,lectureship) VALUES (?,?,?)");

		        	preparedStatementCourse.setString(1, row.getCell(2).getStringCellValue());
		        	preparedStatementCourse.setString(2, row.getCell(3).getStringCellValue());
		        	preparedStatementCourse.setString(3, row.getCell(1).getStringCellValue());
		        	preparedStatementCourse.executeUpdate();	        		        	
		        	}
		        	
		        	PreparedStatement preparedStatementId=conn.prepareStatement("SELECT * FROM course where code=?");
		        	preparedStatementId.setString(1, row.getCell(2).getStringCellValue());
		        	ResultSet resultCourseId=preparedStatementId.executeQuery();
		        	int courseId=0;
		        	if(resultCourseId.next()){
		        		courseId=resultCourseId.getInt(1);      		
		        	}
		        	
		        	PreparedStatement preparedStatementCourseData= conn.prepareStatement("INSERT INTO coursedata " +
		        			"(courseid,practice,lecture,excercise,lecturesperweek,languageid,semester,teacherid) " +
		        			"VALUES (?,?,?,?,?,?,?,?)");
		        	int practice=(int) (row.getCell(6)!=null?row.getCell(6).getNumericCellValue():0);
		        	int lecture=(int) (row.getCell(5)!=null?row.getCell(5).getNumericCellValue():0);
		        	int exercise=(int) (row.getCell(7)!=null?row.getCell(7).getNumericCellValue():0);

		        	PreparedStatement preparedStatementLanguageId=conn.prepareStatement("SELECT * FROM language where name=?");
		        	preparedStatementLanguageId.setString(1, row.getCell(13).getStringCellValue());
		        	int languageId=0;
		        	ResultSet resultLanguageId=preparedStatementLanguageId.executeQuery();
		        	if(resultLanguageId.next()){
		        		languageId=resultLanguageId.getInt(1);
		        	}
		        	PreparedStatement preparedStatementTeacherId=conn.prepareStatement("SELECT * FROM person where firstname=? and lastname=?");
		        	preparedStatementTeacherId.setString(1,firstname);
		        	preparedStatementTeacherId.setString(2, lastname);
		        	int teacherId=0;
		        	ResultSet resultTeacherId=preparedStatementTeacherId.executeQuery();
		        	if(resultTeacherId.next()){
		        		teacherId=resultTeacherId.getInt(1);
		        	}
		        	
		        	preparedStatementCourseData.setInt(1,courseId);
		        	preparedStatementCourseData.setInt(2,practice);
		        	preparedStatementCourseData.setInt(3,lecture);
		        	preparedStatementCourseData.setInt(4,exercise);
		        	preparedStatementCourseData.setFloat(5,(float) row.getCell(9).getNumericCellValue());
		        	preparedStatementCourseData.setInt(6,(int) languageId);
		        	preparedStatementCourseData.setString(7, row.getCell(14).getStringCellValue());
		        	preparedStatementCourseData.setInt(8,(int) teacherId);
		        	preparedStatementCourseData.executeUpdate();
		        }
		    }
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}
	}

}
