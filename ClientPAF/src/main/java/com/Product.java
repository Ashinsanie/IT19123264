package com;

import java.sql.*;

public class Product {
	
	public Connection connect()
	{ 
	 Connection con = null; 

	 
	 try 
	 { 
	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/productdb", 
	 "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 	 
	 return con;
	}

	public String readProducts() 
	 { 
		String output = ""; 
	 try
	 { 
		 Connection con = connect(); 
		 
	 if (con == null) 
	 { 
		 return "Error while connecting  to the database for reading."; 
	 } 
	 
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Product Code</th>   <th>Product Category</th><th>Product Name</th><th>Product Price</th>" + "<th>Product Description</th>  <th>Update</th><th>Remove</th></tr>"; 
	 String query = "select * from product"; 
	 Statement stmt = con.createStatement(); 
	 ResultSet rs = stmt.executeQuery(query); 
	 
	 // iterate through the rows in the result set
	 while (rs.next()) 
	 { 
		 String ProductID = Integer.toString(rs.getInt("ProductID")); 
		 String ProductCode = rs.getString("ProductCode");
	
		 String ProductCategory = rs.getString("ProductCategory");
		 String ProductName = rs.getString("ProductName");
		 String ProductPrice = Double.toString( rs.getDouble("ProductPrice")); 
		 String ProductDesc = rs.getString("ProductDesc"); 
		 
	 // Add into the html table
	 output += "<tr><td><input id='hidItemIDUpdate'  name='hidItemIDUpdate'  type='hidden' value='" + ProductID + "'>" + ProductCode + "</td>"; 
	 output += "<td>" + ProductCategory + "</td>";
	 output += "<td>" + ProductName + "</td>"; 
	 output += "<td>" + ProductPrice + "</td>"; 
	 output += "<td>" + ProductDesc + "</td>"; 
	 
	 
	 // buttons
	output += "<td><input name='btnUpdate'  type='button' value='Update'  class='btnUpdate btn btn-secondary'></td>" + "<td><input name='btnRemove'  type='button' value='Remove'  class='btnRemove btn btn-danger'  data-productid='" + ProductID + "'>" + "</td></tr>"; 
	 } 
	 con.close(); 
	 // Complete the html table
	 output += "</table>"; 
	 } 
	 catch (Exception e) 
	 { 
		 output = "Error while reading the items."; 
		 System.err.println(e.getMessage()); 
	 } 
	 return output;
	 }
	
	
	public String insertProduct(String ProductCode,String ProductCategory, String ProductName, String ProductPrice, String ProductDesc) 
			 { 
			 String output = ""; 
			 try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting  to the database for inserting."; 
			 } 
			 // create a prepared statement
			 String query = " insert into product  (`ProductID`,`ProductCode`,`ProductCategory`,`ProductName`,`ProductPrice`,`ProductDesc`)"+ " values (?, ?, ?, ?, ?,?)";
					 PreparedStatement preparedStmt = con.prepareStatement(query); 
					 
					 // binding values
					 preparedStmt.setInt(1, 0); 
					 preparedStmt.setString(2, ProductCode); 
					 preparedStmt.setString(3, ProductCategory); 
					 preparedStmt.setString(4, ProductName);
					 preparedStmt.setDouble(5, Double.parseDouble(ProductPrice)); 
					 preparedStmt.setString(6, ProductDesc); 
					 
					 
					 // execute the statement
					 preparedStmt.execute(); 
					 con.close(); 
					 String newProducts = readProducts(); 
					 output = "{\"status\":\"success\", \"data\": \"" + 
					 newProducts + "\"}"; 
					 } 
					 catch (Exception e) 
					 { 
					 output = "{\"status\":\"error\", \"data\":  \"Error while inserting the item.\"}"; 
					 System.err.println(e.getMessage()); 
					 } 
					 return output; 
					 }
	
	public String updateProduct(String ProductID,String ProductCode,String ProductCategory, String ProductName, String ProductPrice, String ProductDesc) 
	      { 
				String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
			 if (con == null) 
			 { 
					 return "Error while connecting  to the database for updating."; 
			 } 
			 // create a prepared statement
			 String query = "UPDATE product SET  ProductCode=?,ProductCategory=?,ProductName=?,ProductPrice=?,ProductDesc=? WHERE ProductID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setString(1, ProductCode); 
			 preparedStmt.setString(2, ProductCategory);
			 preparedStmt.setString(3, ProductName); 
			 preparedStmt.setDouble(4, Double.parseDouble(ProductPrice)); 
			 preparedStmt.setString(5, ProductDesc); 
			 preparedStmt.setInt(6, Integer.parseInt(ProductID)); 
			 
			// execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newProducts = readProducts(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newProducts + "\"}"; 
			 } 
			 catch (Exception e) 
			 { 
				 output = "{\"status\":\"error\", \"data\":  \"Error while updating the item.\"}"; 
				 System.err.println(e.getMessage()); 
			 } 
			 return output; 
		}
	
	public String deleteProduct(String ProductID) 
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
	 String query = "delete from product where ProductID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(ProductID)); 
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newProduct = readProducts(); 
	 output = "{\"status\":\"success\", \"data\": \"" + newProduct + "\"}"; 
	 } 
	 catch (Exception e) 
	 { 
	 output = "{\"status\":\"error\", \"data\":  \"Error while deleting the item.\"}"; 
	 System.err.println(e.getMessage()); 
	 } 
	 return output; 
	 }
}
	

