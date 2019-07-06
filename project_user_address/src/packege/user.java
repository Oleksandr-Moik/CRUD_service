package packege;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.CRUD_methods;


/**
 * Servlet implementation class user
 */
@WebServlet("/user/*")
public class user extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

    public user() {
        super();
    }

    CRUD_methods db_work=new CRUD_methods();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        db_work.select(request);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        db_work.update(req);
    }
    
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        db_work.insert(req);
    }
    
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        db_work.delete(req);
    }
}
