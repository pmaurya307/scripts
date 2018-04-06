package cpr003;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ECCBatchProcessing {

	// Create a variable for the connection string.
	private static final String connectionUrl = ;

	private static String userName = System.getProperty("user.name");

	private static Scanner scanner = new Scanner(System.in);

	private static Logger logger = Logger.getLogger(Class.class.getName());

	private static FileHandler fh;

	public static List<String> getXportBatches(Date loadDttm, Integer procesStatId, String batGrpId) {
		List<String> batchIds = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);

			// Create and execute an SQL statement that returns some data.
			String query = ;
			stmt = con.prepareStatement(query);

			

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

	public static List<String> getXportErrorBatches(Date loadDttm, String batGrpId) {
		List<String> batchIds = new ArrayList<>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);

			// Create and execute an SQL statement that returns some data.
			String query = ;
			stmt = con.prepareStatement(query);

			stmt.setDate(1, loadDttm);
			stmt.setString(2, batGrpId);

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

	public static void reprocess() {

		Calendar today = Calendar.getInstance();
		today.set(2018, 1, 25);
		Date loadDttm = new Date(today.getTimeInMillis());
		List<String> batchIds = getXportBatches(loadDttm, 1301,);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		FileWriter fw;
		BufferedWriter bw;

		try {
			fw = new FileWriter("C:\\Users\\" + userName + "\\Documents\\ReprocessedCPRBatches.txt", true);
			bw = new BufferedWriter(fw);

			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);

			bw.write(Calendar.getInstance().getTime() + "\r\n");

			if (batchIds.size() > 0) {
				for (String batchId : batchIds) {
					bw.write(batchId + "\r\n");
					System.out.println("Updating : " + batchId + "\n");

					String sql =;
					stmt = con.prepareStatement(sql);
					stmt.setString(1, batchId);

					stmt.execute();

				}
			} else {
				bw.write("No Records Updated" + "\r\n");
			}

			bw.write("----------------------------------------------" + "\r\n");

			bw.close();
			fw.close();

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

	public static void reprocess() {

		Calendar today = Calendar.getInstance();
		today.set(2018, 1, 25);
		Date loadDttm = new Date(today.getTimeInMillis());
		List<String> batchIds = getXportBatches(loadDttm, 2120,);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		FileWriter fw;
		BufferedWriter bw;

		try {
			fw = new FileWriter("C:\\Users\\" + userName + "\\Documents\\ReprocessedNACDPST01Batches.txt", true);
			bw = new BufferedWriter(fw);

			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl);

			bw.write(Calendar.getInstance().getTime() + "\r\n");

			if (batchIds.size() > 0) {
				for (String batchId : batchIds) {
					bw.write(batchId + "\r\n");
					System.out.println("Updating : " + batchId);

					String sql = ;
					stmt = con.prepareStatement(sql);
					stmt.setString(1, batchId);

					stmt.execute();

				}

			} else {
				bw.write("No Records Updated" + "\r\n");
			}

			bw.write("----------------------------------------------" + "\r\n");
			bw.close();
			fw.close();
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

	@SuppressWarnings("resource")
	public static Map<String, String> getBatchStatusAndError(String batchId) {
		Map<String, String> statusDescMap = null;
		try {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			try {
				// Establish the connection.
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				con = DriverManager.getConnection(connectionUrl);
				statusDescMap = new LinkedHashMap<>();

				// Create and execute an SQL statement that returns some data.
				String query = ;
				stmt = con.prepareStatement(query);
				

				rs = stmt.executeQuery();

				// Iterate through the data in the result set and display it.
				while (rs.next()) {
					String statusCode = rs.getString(1);

					String statusDesc = null;

					query =;
					stmt = con.prepareStatement(query);
					

					rs1 = stmt.executeQuery();

					while (rs1.next()) {
						statusDesc = rs1.getString(1);
					}

					statusDescMap.put(statusCode, statusDesc);
				}

				// Add Error into MAP
				stmt = con.prepareStatement(
						

				rs = stmt.executeQuery();
				while (rs.next()) {
					statusDescMap.put("Error", rs.getString(1));
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
				if (rs1 != null)
					try {
						rs1.close();
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
			// return statusDescMap;
		} catch (Exception e) {

			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		}
		return statusDescMap;
	}

	public static List<String> getBatGrpIds(Date loadDttm) {
		List<String> batGrpIds = null;
		try {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			try {
				// Establish the connection.
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				con = DriverManager.getConnection(connectionUrl);
				batGrpIds = new ArrayList<>();
				// Create and execute an SQL statement that returns some data.
				String query = 

				rs = stmt.executeQuery();

				// Iterate through the data in the result set and display it.
				while (rs.next()) {
					batGrpIds.add(rs.getString(1));
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
				if (rs1 != null)
					try {
						rs1.close();
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
		} catch (Exception e) {

			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		}
		return batGrpIds;

	}

	public static void processByErrorBatchList(List<String> batchIds, Date loadDttm, String batGrpId) {
		// Scanner statusScanner = new Scanner(System.in);
		try {
			Boolean forBreakFlag = false;
			for (String batchId : batchIds) {

				System.out.println("Batch ID : " + batchId + "\n");
				System.out.println("Status \t\t Desc");

				Map<String, String> statusDescMap = getBatchStatusAndError(batchId);

				statusDescMap.forEach((k, v) -> System.out.println(k + "\t\t" + v + "\n"));

				System.out.println("Please enter the update Status or type 'P' to pass or 'E' to exit : ");

				String updateStatus = scanner.next();
				Boolean batchSkipFlag = false;
				while (true) {

					if (updateStatus.matches("[0-9]+")) {

						if (Integer.valueOf(updateStatus) > 500) {
							System.out.println(
									"Status : " + "'" + updateStatus + "'" + " is invalid. Please try again : ");
							updateStatus = scanner.next();
						} else if (updateStatus.equals("500")) {
							System.out.println(
									"Please re-enter the update status or 'E' to Exit. You previously entered : "
											+ updateStatus);
							String nextValue = scanner.next();
							while (true) {

								if (nextValue.toUpperCase().equals("E")) {
									System.out.println("Batch ID : '" + batchId + "' has been skipped." + "\n");
									batchSkipFlag = true;
									break;
								} else if (nextValue.equals(updateStatus)) {
									System.out.println("Batch Id : " + batchId
											+ " has been successfully updated to the status : " + updateStatus + "\n");
									batchSkipFlag = true;
									break;
								} else if (!nextValue.equals(updateStatus)) {
									System.out.println(
											"Status Update Confirmation Failed. Please re-enter update status or 'E' to Exit.");
									nextValue = scanner.next();
								}
							}
						} else {
							if (!statusDescMap.containsKey(updateStatus)) {
								System.out.println(
										"Status : " + "'" + updateStatus + "'" + " is invalid. Please try again : ");
								updateStatus = scanner.next();
							} else {

								System.out.println(
										"Please re-enter the update status or 'E' to Exit. You previously entered : "
												+ updateStatus);
								String nextValue = scanner.next();
								while (true) {

									if (nextValue.toUpperCase().equals("E")) {
										System.out.println("Batch ID : '" + batchId + "' has been skipped." + "\n");
										batchSkipFlag = true;
										break;
									} else if (nextValue.equals(updateStatus)) {
										System.out.println("Batch Id : " + batchId
												+ " has been successfully updated to the status : " + updateStatus
												+ "\n");
										batchSkipFlag = true;
										break;
									} else if (!nextValue.equals(updateStatus)) {
										System.out.println(
												"Status Update Confirmation Failed. Please re-enter update status or 'E' to Exit.");
										nextValue = scanner.next();
									}
								}
							}

						}
					} else if (updateStatus.toUpperCase().equals("P")) {
						System.out.println("Batch ID : " + batchId + " has been passed successfully." + "\n");
						break;
					} else if (updateStatus.toUpperCase().equals("E")) {
						forBreakFlag = true;
						break;
					} else {
						System.out.println("Status : " + "'" + updateStatus + "'" + " is invalid. Please try again : ");
						updateStatus = scanner.next();
					}
					if (batchSkipFlag) {
						break;
					}
				}
				if (forBreakFlag) {
					break;
				}

			}
			// statusScanner.close();
		} catch (

		Exception e) {

			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		}
	}

	public static void processBatchId(String batchId) {

		// Scanner statusScanner = new Scanner(System.in);

		try {

			Map<String, String> statusDescMap = getBatchStatusAndError(batchId);
			System.out.println("Processing batch : " + batchId + "\n");

			System.out.println("Status \t\t Desc");
			statusDescMap.forEach((k, v) -> System.out.println(k + "\t\t" + v));

			System.out.println("Please enter the update Status or type 'P' to pass : ");
			String updateStatus = scanner.next();

			Boolean batchSkipFlag = false;

			while (true) {

				if (updateStatus.matches("[0-9]+")) {

					if (Integer.valueOf(updateStatus) > 500) {
						System.out.println("Status : " + "'" + updateStatus + "'" + " is invalid. Please try again : ");
						updateStatus = scanner.next();
					} else if (updateStatus.equals("500")) {
						System.out.println("Please re-enter the update status or 'E' to Exit. You previously entered : "
								+ updateStatus);
						String nextValue = scanner.next();
						while (true) {

							if (nextValue.toUpperCase().equals("E")) {
								System.out.println("Batch ID : '" + batchId + "' has been skipped." + "\n");
								batchSkipFlag = true;
								break;
							} else if (nextValue.equals(updateStatus)) {
								System.out.println("Batch Id : " + batchId
										+ " has been successfully updated to the status : " + updateStatus + "\n");
								batchSkipFlag = true;
								break;
							} else if (!nextValue.equals(updateStatus)) {
								System.out.println(
										"Status Update Confirmation Failed. Please re-enter update status or 'E' to Exit.");
								nextValue = scanner.next();
							}
						}

					} else {
						if (!statusDescMap.containsKey(updateStatus)) {
							System.out.println(
									"Status : " + "'" + updateStatus + "'" + " is invalid. Please try again : ");
							updateStatus = scanner.next();
						} else {
							System.out.println(
									"Please re-enter the update status or 'E' to Exit. You previously entered : "
											+ updateStatus);
							String nextValue = scanner.next();
							while (true) {

								if (nextValue.toUpperCase().equals("E")) {
									System.out.println("Batch ID : '" + batchId + "' has been skipped." + "\n");
									batchSkipFlag = true;
									break;
								} else if (nextValue.equals(updateStatus)) {
									System.out.println("Batch Id : " + batchId
											+ " has been successfully updated to the status : " + updateStatus + "\n");
									batchSkipFlag = true;
									break;
								} else if (!nextValue.equals(updateStatus)) {
									System.out.println(
											"Status Update Confirmation Failed. Please re-enter update status or 'E' to Exit.");
									nextValue = scanner.next();
								}
							}
						}

					}
				} else if (updateStatus.toUpperCase().equals("P")) {
					System.out.println("Batch ID : " + batchId + " has been passed successfully." + "\n");
					break;
				} else {
					System.out.println("Status : " + "'" + updateStatus + "'" + " is invalid. Please try again : ");
					updateStatus = scanner.next();
				}
				if (batchSkipFlag) {
					break;
				}
			}

			// statusScanner.close();
		} catch (Exception e) {

			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		}

	}

	private static void bulkUpdate(List<String> batchIds, String batGrpId) {
		try {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			ResultSet rs1 = null;
			try {

				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				con = DriverManager.getConnection(connectionUrl);
				// Map<String, String> batchStatusMap = new LinkedHashMap<>();

				for (String batchId : batchIds) {
					// Create and execute an SQL statement that returns some data.
					String query = 
					rs = stmt.executeQuery();
					// Iterate through the data in the result set and display it.
					while (rs.next()) {
						System.out.println("BATCH ID \t\t STATUS");
						System.out.println(batchId + "\t\t  " + rs.getString(1));
						System.out.println();
					}
				}

				System.out.println("Please enter the update status for batches : " + batchIds + ". BAT_GRP_ID : "
						+ batGrpId + " or 'E' to exit bulk update.");
				String status = scanner.next();
				Boolean batchSkipFlag = false;
				while (true) {
					if (status.toUpperCase().equals("E")) {
						break;
					} else {
						if (status.matches("[0-9]+")) {
							if (Integer.valueOf(status) > 500) {
								System.out.println("Update Status cannot be greater than 500. Please try again : ");
								status = scanner.next();
							} else {

								System.out.println(
										"Please re-enter the update status or 'E' to Exit. You previously entered : "
												+ status);
								String nextValue = scanner.next();
								while (true) {

									if (nextValue.toUpperCase().equals("E")) {
										System.out.println("Batch IDs : '" + batchIds + "' has been skipped." + "\n");
										batchSkipFlag = true;
										break;
									} else if (nextValue.equals(status)) {
										System.out.println("Batch IDs : " + batchIds
												+ " has been successfully updated to the status : " + status + "\n");
										batchSkipFlag = true;
										break;
									} else if (!nextValue.equals(status)) {
										System.out.println(
												"Status Update Confirmation Failed. Please re-enter update status or 'E' to Exit.");
										nextValue = scanner.next();
									}
								}
								break;
							}
						} else {
							System.out.println("Invalid Status. Please try again : ");
							status = scanner.next();
						}
					}
					if (batchSkipFlag) {
						break;
					}
				}

			} catch (Exception e) {
				logStackTrace(e);
				System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
			} finally {
				if (rs != null)
					try {
						rs.close();
					} catch (Exception e) {
					}
				if (rs1 != null)
					try {
						rs1.close();
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
		} catch (Exception e) {
			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		}

	}

	public static void checkBatchStatus() {
		try {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			ResultSet rs1 = null;

			try {
				// Establish the connection.
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				con = DriverManager.getConnection(connectionUrl);

				System.out.println("Please enter batch id or 'E' to exit : ");
				String batchId = scanner.next();

				if (batchId.length() > 0 && !batchId.toUpperCase().equals("E")) {
					// Create and execute an SQL statement that returns some data.
					/*
					 * String query =
					 * "select PROCES_STAT_ID from ECCXport_Data.dbo.T_BAT where BAT_ID=?"; stmt =
					 * con.prepareStatement(query); stmt.setString(1, batchId); rs =
					 * stmt.executeQuery(); if (rs.next()) { // Iterate through the data in the
					 * result set and display it. System.out.println("Current Batch Status : " +
					 * rs.getString(1));
					 * 
					 * } else { System.out.println("No records present for : '" + batchId +
					 * "' in ECCXport_Data.dbo.T_BAT"); }
					 */

					Map<String, String> statusDescMap = getBatchStatusAndError(batchId);

					if (statusDescMap.isEmpty()) {
						System.out.println("No records present for : '" + batchId + "' in ");
					} else {
						System.out.println("Status \t\t Desc");
						statusDescMap.forEach((k, v) -> System.out.println(k + "\t\t" + v + "\n"));
					}

				}
				// B33169086
				else if (batchId.toUpperCase().equals("E")) {
					System.out.println("Exit Successful.\n");
				} else {
					System.out.println("Invalid batch id." + "\n");
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
				if (rs1 != null)
					try {
						rs1.close();
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
		} catch (Exception e) {

			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		}

	}

	public static void selector() {
		try {
			System.out.println("Please select one : ");
			System.out.println("'1' - Process stuck batches");
			System.out.println("'2' - Check Batch Status via batch id.");
			System.out.println("'E' - Exit");

			String statusOrProcess = scanner.next();
			while (true) {
				if (statusOrProcess.equals("1")) {
					dummyMain();
					break;
				} else if (statusOrProcess.equals("2")) {
					checkBatchStatus();
					break;
				} else if (statusOrProcess.toUpperCase().equals("E")) {
					break;
				} else {
					System.out.println("Invalid Option.Please try again : ");
					statusOrProcess = scanner.next();
				}
			}

			if (!statusOrProcess.toUpperCase().equals("E")) {
				selector();
			}
		} catch (Exception e) {

			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		}

	}

	public static void logStackTrace(Exception logE) {

		try {
			fh = new FileHandler("C:\\Users\\" + userName + "\\Documents\\Log-"
					+ new Date(Calendar.getInstance().getTimeInMillis()) + ".txt", true);
			logger.addHandler(fh);

			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

			logger.setUseParentHandlers(false);

			logger.warning(logE.toString());

		} catch (SecurityException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void dummyMain() {
		try {
			Calendar today = Calendar.getInstance();
			today.set(2018, 1, 25);
			Date loadDttm = new Date(today.getTimeInMillis());

			List<String> batGrpIds = getBatGrpIds(loadDttm);
			System.out.println("BAT_GRP_IDS having error batches after : '" + loadDttm + "' are : " + batGrpIds + "\n");

			System.out.println("Please enter a  or 'E' to exit : ");

			// Scanner scanner = new Scanner(System.in);
			String batGrpId = scanner.next();

			if (!batGrpId.toUpperCase().equals("E")) {
				while (true) {
					if (batGrpIds.contains(batGrpId.toUpperCase())) {
						break;
					} else {
						System.out.println("Invalid .Please enter again : ");
						batGrpId = scanner.next();
					}

				}
				List<String> batchIds = getXportErrorBatches(loadDttm, batGrpId);
				if (batchIds.size() > 0) {

					System.out.println("There are " + "'" + batchIds.size() + "' Batches" + " in error." + "\n");
					System.out.println("Batch Ids : " + batchIds + "\n");

					System.out.println("Please enter '1' or '2' : ");
					System.out.println("'1' - Process a Batch ID");
					System.out.println("'2' - Go through the whole list one by one");
					System.out.println("'3' - Bulk update");
					System.out.println("'E' - Exit");

					String batchOrList = scanner.next();

					while (true) {
						if (batchOrList.equals("1")) {
							// Scanner getBatchIdScanner = new Scanner(System.in);
							System.out.println("Please enter the batch id index - Range : (0 to "
									+ (batchIds.size() - 1) + ")" + "\n");
							System.out.println("For Example '0' for first batch in the list and '"
									+ (batchIds.size() - 1) + "' for the last batch in the list." + "\n");
							Integer inputBatchIdIndex = scanner.nextInt();

							String batchId = null;
							while (true) {
								if (inputBatchIdIndex >= 0 && inputBatchIdIndex < batchIds.size()) {
									batchId = batchIds.get(inputBatchIdIndex);
									/*
									 * if (batchIds.contains(batchId)) { break; } else {
									 * System.out.println("Batch Id not present in the list.Please try again : ");
									 * inputBatchIdIndex = scanner.nextInt(); }
									 */
									System.out.println("Batch id : '" + batchId + "' selected for processing." + "\n");
									break;
								} else {
									System.out.println("Invalid Index Value.Please try again : ");
									inputBatchIdIndex = scanner.nextInt();
								}
							}

							processBatchId(batchId);

							// scanner.close();
							break;
						} else if (batchOrList.equals("2")) {
							processByErrorBatchList(batchIds, loadDttm, batGrpId);
							break;
						} else if (batchOrList.equals("3")) {
							bulkUpdate(batchIds, batGrpId);
							break;
						} else if (batchOrList.toUpperCase().equals("E")) {
							break;
						} else {
							System.out.println("Invalid Option.Please try again : ");
							batchOrList = scanner.next();

						}
					}
					// batchOrListScanner.close();

					// scanner.close();

				}

				else

				{
					System.out.println("No Error batches for : " + batGrpId + " after : " + loadDttm + "\n");
				}
				// Scanner batchOrListScanner = new Scanner(System.in);
				System.out.println("Press 'C' to Continue batch processing or 'E' to exit to main menu : ");
				String s = scanner.next();
				while (true) {
					if (s.toUpperCase().equals("C")) {
						dummyMain();
						break;
					}

					else if (s.toUpperCase().equals("E")) {
						break;
					} else {
						System.out.println("Invalid Option.Please try again : ");
						s = scanner.next();// br.readLine();
					}

				}
			} else {
				selector();
			}
		} catch (Exception e) {

			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		}

	}

	public static void main(String[] args) {
		try {
			System.out.println("Processing started" + "\n");

			selector();

			scanner.close();

			System.out.println("Done");
		} catch (Exception e) {

			logStackTrace(e);
			System.out.println("Unexpected Error Occured : " + e.getMessage() + "\n");
		} finally {
			System.out.println("Log for exception(if any) present @ : " + "C:\\Users\\" + userName + "\\Documents\\Log-"
					+ new Date(Calendar.getInstance().getTimeInMillis()) + ".txt" + "\n");
		}
	}

}
