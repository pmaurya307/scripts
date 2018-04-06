package checkCIN;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckCIN {

	public static List<String> readFileInList(String fileName) {

		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
		}

		catch (IOException e) {

			// do something
			e.printStackTrace();
		}
		return lines;
	}

	public Boolean checkCINThor(String cin) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection();
		PreparedStatement stmt = con.prepareStatement("select cin from t_clt where cin = ?");
		stmt.setString(1, cin);
		ResultSet rs = stmt.executeQuery();
		try {

			// here sonoo is database name, root is username and password


			while (rs.next()) {
				if (String.valueOf(rs.getInt(1)) == null) {
					return false;
				} else {
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
			con.close();
		}
		return false;
	}

	public static void main(String[] args) {
		List<String> l = readFileInList("C:\\Users\\uipm48\\Desktop\\Notepad++ Files\\CINCheck\\TestCins.txt");
		
		System.out.println("Total Count : "+l.size());
		
		List<String> foundCin = new ArrayList<>();
		List<String> notFoundCin = new ArrayList<>();
		try {
			CheckCIN checkCIN = new CheckCIN();
			for (int i = 0; i < l.size(); i++) {

				String cin = l.get(i);
				System.out.println("Checking : "+cin+" Count :"+(i+1));
				Boolean checkResult = checkCIN.checkCINThor(cin);
				if (checkResult) {
					foundCin.add(cin);
				} else {
					notFoundCin.add(cin);
				}

			}
			
			FileWriter fwfound = new FileWriter("C:\\Users\\uipm48\\Desktop\\CINCheck\\Found.txt");
			BufferedWriter bwfound = new BufferedWriter(fwfound);
			
			FileWriter fwNotFound = new FileWriter("C:\\Users\\uipm48\\Desktop\\CINCheck\\NotFound.txt");
			BufferedWriter bwNotFound = new BufferedWriter(fwNotFound);

			bwNotFound.write("Count : "+ notFoundCin.size()+"\n");
			bwfound.write("Count : "+ foundCin.size()+"\n");

			for (String string : notFoundCin) {
				bwNotFound.write(string+"\n");
			}

			
			
			for (String string : foundCin) {
				bwfound.write(string+"\n");
			}
			
			bwfound.close();
			bwNotFound.close();
			
			System.out.println("Done");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
