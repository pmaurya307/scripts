package mscv001;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class MSCVUpdate {
	// Create a variable for the connection string.
	private static final String connectionUrl = "jdbc:sqlserver://GA016VSQL0E7;"
			+ "databaseName=ECCXport_Data_Hst;user=uzpisq1;password=imgwrite";

	private static String  userName= System.getProperty("user.name");

	public static List<String> getBatchIdsByArchvDttm(String batGrpId, Date archvDttm, Date toDttm) {
		List<String> batchIds = new ArrayList<>();
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);

			// Create and execute an SQL statement that returns some data.
			stmt = con.prepareStatement(
					"select BAT_ID from T_BAT where BAT_GRP_ID=? and ARCHV_DTTM>=? and ARCHV_DTTM<?");

			stmt.setString(1, batGrpId);
			stmt.setDate(2, archvDttm);
			stmt.setDate(3, toDttm);

			rs = stmt.executeQuery();

			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				batchIds.add(rs.getString(1));
			}
		}

		// Handle any errors that may have occurred.
		catch (Exception e) {
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
		return batchIds;
	}

	
	public static Boolean backup(List<String> batchIds) {
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql=null;
		Boolean isBackupCompleted=false;
		try {
			
			
			Date date=new Date(Calendar.getInstance().getTimeInMillis());
			String filename="C:\\Users\\"+userName+"\\Documents\\MSCV001\\MSCVBackup-"+date+".xls";
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("T_BAT_DATA");
						
			
			System.out.println("Writing header");

			HSSFRow rowhead=   sheet.createRow((short)0);
			
			
			
			rowhead.createCell((short) 0).setCellValue("BAT_ID");
			rowhead.createCell((short) 1).setCellValue("BAT_GRP_ID");
			rowhead.createCell((short) 2).setCellValue("FLD_ID");
			rowhead.createCell((short) 3).setCellValue("FLD_VAL_TXT");
			

			
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);
			int i=1;
			
			for (String batchId : batchIds) {
				sql="SELECT * FROM T_BAT_DATA WHERE BAT_ID=?";
				stmt = con.prepareStatement(sql);
				stmt.setString(1, batchId);
				rs = stmt.executeQuery();
				
				System.out.println("Backing up : "+ batchId);
				while (rs.next()) {
					
					HSSFRow row=   sheet.createRow((short)i);
					row.createCell((short) 0).setCellValue(rs.getString(1));
					row.createCell((short) 1).setCellValue(rs.getString(2));
					row.createCell((short) 2).setCellValue(rs.getInt(3));
					row.createCell((short) 3).setCellValue(rs.getString(4));
					i++;
				}
			}
			

						

			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			hwb.close();
			System.out.println("Your excel file has been generated!");
			isBackupCompleted=true;

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
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
		
		return isBackupCompleted;
	}
	@SuppressWarnings("resource")
	public static void updateSlaDttm(Calendar today,Calendar toDate) {

		String slaDeadline = null;
		toDate.add(Calendar.DATE, 1);
		List<String> batchIds = getBatchIdsByArchvDttm("MSCV001", new Date(today.getTimeInMillis()),
				new Date(toDate.getTimeInMillis()));
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;

		try {
			FileWriter fw;
			BufferedWriter bw;
			String logFilePath ="C:\\Users\\"+userName+"\\Documents\\MSCV001\\Log-"+new Date(today.getTimeInMillis())+".txt";
			fw = new FileWriter(logFilePath);
			bw = new BufferedWriter(fw);
			
			Boolean isBackupCompleted=backup(batchIds);
			
			
			if (isBackupCompleted) {
				
				// Establish the connection.
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				con = DriverManager.getConnection(connectionUrl);

				for (String batchId : batchIds) {
					// Create and execute an SQL statement that returns some data.
					stmt = con.prepareStatement("select fld_val_txt from T_BAT_DATA where BAT_ID=? and fld_id=1078");
					stmt.setString(1, batchId);

					rs = stmt.executeQuery();

					bw.write("Updating : " + batchId + "\r\n");

					System.out.println(batchId);

					while (rs.next()) {

						slaDeadline = rs.getString(1);
					}

					bw.write("Old SLA DTTM : " + slaDeadline + "\r\n");

					System.out.println("Old SLA DTTM : " + slaDeadline);

					Integer endIndex = slaDeadline.indexOf(".");
					if (endIndex >= 0) {
						slaDeadline = slaDeadline.substring(0, endIndex);
						bw.write("New SLA DTTM : " + slaDeadline + "\r\n");
						System.out.println("New SLA DTTM : " + slaDeadline);

						String sql = "update t_bat_data set fld_val_txt=? where bat_id=? and fld_id=1078";
						stmt = con.prepareStatement(sql);
						stmt.setString(1, slaDeadline);
						stmt.setString(2, batchId);

						stmt.execute();
						bw.write("Completed" + "\r\n");
						System.out.println("Update Completed for : " + batchId);
					} else {
						bw.write("Update Not Needed" + "\n");
						System.out.println("Update Not Needed for : " + batchId);
					}

				}
				//stmt.close();
				bw.close();
				fw.close();
			}
			else {
				bw.write("Something went wrong"+"\r\n");
			}
		}

		// Handle any errors that may have occurred.
		catch (Exception e) {
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
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Calendar today = Calendar.getInstance();		
		Calendar toDate = Calendar.getInstance();
		
		
		
		File makeFolder= new File("C:\\Users\\"+userName+"\\Documents\\MSCV001");
		if(!makeFolder.exists()) {
			makeFolder.mkdir();
		}
		
		//updateSlaDttm(today,toDate);
		
		System.out.println("Done");
		System.out.println("Logs and Backup present @ "+"C:\\Users\\"+userName+"\\Documents\\MSCV001");
		
	}
}
