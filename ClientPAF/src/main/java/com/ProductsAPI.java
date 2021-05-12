package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductsAPI
 */
@WebServlet("/ProductsAPI")
public class ProductsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Product productObj =new Product();
       
    public ProductsAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String output = productObj.insertProduct(request.getParameter("ProductCode"),
				request.getParameter("ProductCategory"),
				request.getParameter("ProductName"), 
				request.getParameter("ProductPrice"), 
				request.getParameter("ProductDesc")); 
				response.getWriter().write(output); 
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	// Convert request parameters to a Map
			private static Map getParasMap(HttpServletRequest request)
			{
			 Map<String, String> map = new HashMap<String, String>();
			try
			 {
			 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			 String queryString = scanner.hasNext() ?
			 scanner.useDelimiter("\\A").next() : "";
			 scanner.close();
			 String[] params = queryString.split("&");
			 for (String param : params)
			 { 
			
			String[] p = param.split("=");
			 map.put(p[0], p[1]);
			 }
			 }
			catch (Exception e)
			 {
			 }
			return map;
			}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		 Map paras = getParasMap(request);
		 String output = productObj.updateProduct(paras.get("hidItemIDSave").toString(),
		 paras.get("ProductCode").toString(),
		 paras.get("ProductCategory").toString(),
		 paras.get("ProductName").toString(),
		 paras.get("ProductPrice").toString(),
		 paras.get("ProductDesc").toString());
		 response.getWriter().write(output);
		
		// TODO Auto-generated method stub
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map paras = getParasMap(request);
		 String output = productObj.deleteProduct(paras.get("ProductID").toString());
		response.getWriter().write(output);
		// TODO Auto-generated method stub
	}

}
