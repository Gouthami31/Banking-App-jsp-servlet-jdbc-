package com.banking;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateName extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int num=Integer.parseInt(request.getParameter("num"));
		PrintWriter out=response.getWriter();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection can=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
			PreparedStatement ps=can.prepareStatement("select name from account where num=?");
			ps.setInt(1, num);		
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				String name=rs.getString(1);
				out.println("<html> <body>");
				out.println("<form action='UpdateName' method='post'>");
				out.println("num=<input type='text' value='"+num+ "'name='num'/><br/>");
				out.println("name=<input type='text' value='"+name+"' name='name'/><br/>");
				out.println("<input type='submit' value='update'/>");
				out.println("</form>");
				out.println("</body> </head>");
			}
			else {
				out.println("<p>Invalid user/password</p>");
				RequestDispatcher rd=request.getRequestDispatcher("/login.html");
				rd.include(request,response);
			}
			can.close();
		}
		catch(Exception e) {
			out.println(e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int num=Integer.parseInt(request.getParameter("num"));
		String name=request.getParameter("name");
		PrintWriter out=response.getWriter();
		
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection can=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");
			PreparedStatement ps=can.prepareStatement("update account set name=? where num=?");
			ps.setString(1, name);
			ps.setInt(2,num);
			int count=ps.executeUpdate();
			if(count>0) {
				out.println("<h1> name updated succesfully</h1>");
			}
			else 
			{
			   out.println("<h1>failed to update</h1>");
	         }
			can.close();
		}
		catch(Exception e) 
		{
			 out.println(e.getMessage());
		}
	
    }
}