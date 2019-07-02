package packege;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import packege.*;


/**
 * Servlet implementation class user
 */
@WebServlet("/user/*")
public class user extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	GetRequestBody getRequestBody=new GetRequestBody();
    public user() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    getRequestBody.getBody(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    getRequestBody.getBody(req, resp);
	}
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    getRequestBody.getBody(req, resp);
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    getRequestBody.getBody(req, resp);
	}
}
