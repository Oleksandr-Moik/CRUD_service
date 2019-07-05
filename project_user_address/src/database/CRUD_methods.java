package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CRUD_methods {
    
    public void beginGeting(HttpServletRequest request,HttpServletResponse pesponse) {
        System.out.println("begin Geting");
        return;
    }
    
    private Connection con=null;
    private String current_db="";
    private Integer current_id=(Integer)null;
    private Statement stmt=null;
//    private ResultSet rs=null;
    
    public void databaseConect(HttpServletRequest request) {
        con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_project", "root", "root");
            String [] uri_words = request.getRequestURI().split("/", 4);
            current_db=uri_words[2];
            current_id=getID(request);
            stmt=con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void getData() {
        if(con==null)return;
        try {            
            ResultSet rs=stmt.executeQuery(
                             String.format("select * from %s where id=%d",current_db, current_id));  
            
            if(current_db.equals("user"))
                while(rs.next()) {
                    System.out.println(String.format("User id=%d",rs.getInt(1)));
                    System.out.println(String.format("First name: %s",rs.getString(2)));
                    System.out.println(String.format("Last name: %s",rs.getString(3)));
                    System.out.println(String.format("Age: %d",rs.getInt(4)));  
                }
            else if (current_db.equals("address"))
                while(rs.next())  {
                    System.out.println(String.format("Address id=%d",rs.getInt(1)));
                    System.out.println(String.format("Country: %s",rs.getString(2)));
                    System.out.println(String.format("City: %s",rs.getString(3)));
                    System.out.println(String.format("Street: %s",rs.getString(4)));
                }
            con.close();
            
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void updateData() {
        if(con==null)return;
        try {
            
            
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private String getBody(HttpServletRequest request) {
        String bodies="";
        System.out.println("Body:");
        
        if (request.getMethod() == "GET") {
            return "none";
        }
        
        Scanner s = null;
        try {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
            s = scanner.useDelimiter("\\A");
        } catch (Exception e) {
            System.out.println("Excepyion in method 'getBody':\n"+e);
        }
        bodies+=s.hasNext() ? s.next() : "";
        return bodies;        
    }
    
    /*
    private String extractPostRequestBody(HttpServletRequest request) {
        if (request.getMethod() == "GET") {
            return "none";
        }
        
        Scanner s = null;
        try {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
            s = scanner.useDelimiter("\\A");
        } catch (Exception e) {
            System.out.println("Excepyion in method 'getBody':\n"+e);
        }
        
        return s.hasNext() ? s.next() : "";
    }
    */
    
    private String getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String headers="";
        System.out.println("Headers:");
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(String.format("%s = %s", key, value));
        }  
        return headers;
    }
    
    /*
    private String getUrlParameters(HttpServletRequest request) {
        String parameters=null;
        System.out.println("Url Parameters:");
        
        Enumeration<String> params = request.getParameterNames();
        boolean is_param=false;
        while(params.hasMoreElements()){
            String paramName = (String)params.nextElement();
            System.out.println(String.format("\t%s = ",paramName,request.getParameter(paramName)));
            is_param=true;
        }
        if(is_param)return parameters;
        else return "None;";
    }
    */
    /*
    private void responseHTML(HttpServletRequest request,HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            PrintWriter pw  = response.getWriter();
            
            pw.println("<head><head><title>user</title></head><body>");
            
            String uri=request.getRequestURI();
            String method=request.getMethod();
            
            pw.println(String.format("<p><b>URL: </b>%s</p>", uri));
            pw.println(String.format("<p><b>Method: </b>%s</p>",method));
            
            pw.println(getHeaders(request));
            
            pw.println(getBody(request));
            pw.println(String.format("<p>%s</p>",extractPostRequestBody(request)));
    
            pw.println(getUrlParameters(request));
                        
            String requri = request.getPathInfo();
            pw.println(String.format("<p><b>User id:</b> %s</p>", requri));
    
            pw.println("</body></html>");
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    */
    
    private Integer getID(HttpServletRequest request){
        Integer id_i=(Integer)null;
        String [] id_s = request.getPathInfo().split("/", 3);
        
        try{id_i = Integer.valueOf(id_s[1]);}
        catch (NumberFormatException nfe) {System.out.println(nfe);}
        return id_i;
    }
    
    
    
}
