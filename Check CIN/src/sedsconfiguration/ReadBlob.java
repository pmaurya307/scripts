package sedsconfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReadBlob {
	
	public static void rdblb(String query) {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(");
			stmt = con.prepareStatement(query);
			
			rs = stmt.executeQuery();
//			Blob blob=null;
			while (rs.next()) {
				System.out.println(rs.getObject(1));
//				blob=rs.getBlob(1);
				
			}
			
//			
//			byte bytes[]=blob.getBytes(1, (int)blob.length());
//			for (byte b : bytes) {
//				System.out.println(blob.);
//			}
//			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (con != null)
				try {
					con.close();
				} catch (Exception e) {
				}

		}

	}
	
	public static void main(String[] args) {
		int i = 1;
		while (i<48005) {
			String query= "";
			
			rdblb(query);
			i+=2000;
		}
		System.out.println("Done");
	}
}
