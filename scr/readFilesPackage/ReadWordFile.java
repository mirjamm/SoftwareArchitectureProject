package readFilesPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class ReadWordFile {

	public static void main(String[] args) throws SQLException {	
		String FilePath = "C:/workspace/DataSourceProject/DataSourceProject/src/nimekiri_test.doc";
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(FilePath));
			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor extractor = new WordExtractor(doc);

			Connection conn = DriverManager.getConnection("jdbc:postgresql://dev.vk.edu.ee:5432/GroupWork?currentSchema=project", "t131566", "t131566");

			String text = extractor.getText();
			String strippedText = extractor.stripFields(text).replace("\r\n\r\n", "\n").replace("\t", " ").replace("\r\n","\n");
			String[] paragraphs = strippedText.split("\n");

			for (int i = 8; i < paragraphs.length; i++) {
				int index=0;
						String jrk="";
				if (!paragraphs[i].trim().isEmpty() && paragraphs[i].substring(0, 3).contains("Jrk")) {
					/*System.out.println(paragraphs[i]);*/
					String groupCode=paragraphs[i].substring(17,21);
					jrk=paragraphs[i];
					index=strippedText.indexOf(jrk);
					String groupName="";
					int j=1;
					do{
						if(!paragraphs[i-j].trim().isEmpty()){
							if(Character.isUpperCase(paragraphs[i-j].charAt(3))){
								groupName=paragraphs[i-j];
								if(groupName.contains("(KAUGÕPE)")){
									groupName=groupName.replace("(KAUGÕPE)", "").trim();
								}
							}
						}
						
						j++;
					}while(!paragraphs[i-j].trim().isEmpty());
					/*System.out.println(groupName+" "+groupCode);*/
					
					PreparedStatement preparedStatementGetGroup = conn.prepareStatement("SELECT * FROM \"group\" where name is null and groupcode like ?");
					preparedStatementGetGroup.setString(1, groupCode+"%");
					ResultSet resultGroup = preparedStatementGetGroup.executeQuery();
					while (resultGroup.next()) {
						int groupId=resultGroup.getInt(1);
						PreparedStatement preparedStatementSetGroupName = conn.prepareStatement("UPDATE \"group\" SET name=? where id=?");
						preparedStatementSetGroupName.setString(1,groupName);
						preparedStatementSetGroupName.setInt(2,groupId);
						preparedStatementSetGroupName.executeUpdate();
					}
					
				} else{
					/*System.out.println(paragraphs[i]);*/
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
