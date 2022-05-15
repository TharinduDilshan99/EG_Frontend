package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.Part;

public class Bill {	
	public Connection connect()
	{ 
		 Connection con = null; 
		 
		 try { 
			 Class.forName("com.mysql.jdbc.Driver"); 
			 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/electrogrid","root", "19990121"); 
			 //For testing
			 System.out.print("Successfully connected"); 
		} catch(Exception e){ 
			 e.printStackTrace(); 
		} 
		return con; 
	}
	
//insert bills
	
	public String insertBill(String UserName, String UserAddress, int UnitCount, String BillAmount, String DueAmount, String Date) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
			
			
			
			// create a prepared statement
			String query = " insert into bill( `UserName`, `UserAddress`, `UnitCount`, `BillAmount`, `DueAmount`, `Date`) values( ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			
			// binding values
			preparedStmt.setString(1, UserName);
			preparedStmt.setString(2, UserAddress);
			preparedStmt.setInt(3, UnitCount);
			preparedStmt.setDouble(4, Double.parseDouble(BillAmount));
			preparedStmt.setDouble(5, Double.parseDouble(DueAmount));
			preparedStmt.setString(6, Date);
			
			
			
			
			//execute the statement
			 preparedStmt.execute(); 
			 con.close(); 

			 String bill = readBill();
			 output = "{\"status\":\"success\", \"data\": \"" + bill + "\"}";
		}catch (Exception e) { 
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the bill.\"}";
			 System.err.println(e.getMessage()); 
		} 
		return output;
		
	}
	
	
	//reading bills
	public String readBill() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			
			// Prepare the html table to be displayed
			output = "<table border='1' class='table table-bordered'>"
					+ "<tr>"
					+ "<th>User Name</th>"
					+ "<th>User Address</th>"
					+ "<th>Unit Count</th>"
					+ "<th>Bill Amount</th>"
					+ "<th>Due Amount</th>"
					+ "<th>Date</th>"
					+ "<th>Update</th>"
					+ "<th>Delete</th>"
					+ "</tr>";

			String query = "select * from bill";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String BillID = Integer.toString(rs.getInt("BillID"));
				String UserName = rs.getString("UserName");
				String UserAddress = rs.getString("UserAddress");
				String UnitCount = Integer.toString(rs.getInt("UnitCount"));
				String BillAmount = Double.toString(rs.getDouble("BillAmount"));
				String DueAmount = Double.toString(rs.getDouble("DueAmount"));
				String Date = rs.getString("Date");
				
				
				// Add into the html table
				output += "<tr>";
				output += "<td>" + UserName + "</td>";
				output += "<td>" + UserAddress + "</td>";
				output += "<td>" + UnitCount + "</td>";
				output += "<td>" + BillAmount + "</td>";
				output += "<td>" + DueAmount + "</td>";
				output += "<td>" + Date + "</td>";
				
				//buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-billid='" + BillID + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='" + BillID + "'></td></tr>";
				
			}
			
			con.close();
			output += "</table>";
			
		}catch(Exception e) {
			output = "Error while reading";
			System.out.println(e.getMessage());
		}
		return output;
	}
	

	//updating bills
	public String updateBill(int BillID, String UserName, String UserAddress, int UnitCount, String BillAmount, String DueAmount, String Date)

	{
		String output = "";
		
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE bill SET UserName=?, UserAddress=?, UnitCount=?, BillAmount=?, DueAmount=?, Date=? WHERE BillID = ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, UserName);
			preparedStmt.setString(2, UserAddress);
			preparedStmt.setInt(3, UnitCount);
			preparedStmt.setString(4, BillAmount);
			preparedStmt.setString(5, DueAmount);
			preparedStmt.setString(6, Date);
			preparedStmt.setInt(7, BillID);
			
			// execute the statement
			 preparedStmt.execute();
			con.close();	
			
			String bill = readBill();
			 output = "{\"status\":\"success\", \"data\": \"" + bill + "\"}";
		}catch (Exception e) { 
			output = "{\"status\":\"error\", \"data\": \"Error while updating the bill.\"}"; 
			 System.err.println(e.getMessage()); 
		} 
		return output;
	}
	
	
	//deleting bills
	public String deleteBill(int BillID) 
	 { 
	        String output = ""; 
	 try
	 { 
		       Connection con = connect(); 
		       
		       if (con == null) 
		      {
		    	   return "Error while connecting to the database for deleting."; 
		      } 
		       
		     // create a prepared statement
		     String query = "delete from bill where BillID=?"; 
		     
		     PreparedStatement preparedStmt = con.prepareStatement(query); 
		     
		     // binding values
		     preparedStmt.setInt(1, BillID); 
		     
		    // execute the statement
		    preparedStmt.execute(); 
		    con.close(); 
		    
		    String bill = readBill();
		     output = "{\"status\":\"success\", \"data\": \"" + bill + "\"}"; 
		} 
		catch (Exception e) 
		{ 
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the bill.\"}"; 
			System.err.println(e.getMessage()); 
		} 
		return output;
	}
	
	
}