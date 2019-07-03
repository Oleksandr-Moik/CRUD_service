package packege;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class address
 */
@WebServlet("/address/*")
public class address extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */ 
	
    public address() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    GetRequestBody getRequestBody=new GetRequestBody();
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
