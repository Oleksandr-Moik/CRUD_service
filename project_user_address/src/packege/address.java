package packege;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.CRUD_methods;

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
    
    CRUD_methods db_work=new CRUD_methods();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        db_work.databaseConect(request);
        db_work.getData();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        db_work.databaseConect(req);
        db_work.updateData();
    }
    
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        db_work.databaseConect(req);
        db_work.beginGeting(req, resp);
    }
    
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        db_work.databaseConect(req);
        db_work.beginGeting(req, resp);
    }

}
