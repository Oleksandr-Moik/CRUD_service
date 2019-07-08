package database;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

//@SuppressWarnings("unchecked")
public class CRUD_methods {
    
    public void beginGeting(HttpServletRequest request,HttpServletResponse pesponse) {
        System.out.println("begin Geting");
        return;
    }
    
    private Connection con=null;
    private String current_table=null;
    private Integer current_id=null;
    
    private void dbConnect(HttpServletRequest request) {
        con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_project", "root", "root");
            String reqURI=request.getRequestURI();
//            substring(1,str.length) //test
//            if(!reqURI.equals("/")){con=null; return;
            String [] uri_words = reqURI.split("/", 4);
            current_table=uri_words[2];
            current_id=getID(request);
//            System.out.println(getBody(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void select(HttpServletRequest request) {
        dbConnect(request);
        if(con.equals(null))return;
        try {
            String query=String.format("SELECT * FROM %s WHERE id=?", current_table);
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setLong(1, current_id);
            
            ResultSet rs=stmt.executeQuery();
            
            if(current_table.equals("user"))
                while(rs.next()) {
                    System.out.println("*USER*");
                    System.out.println(String.format("User id=%d",rs.getInt(1)));
                    System.out.println(String.format("First name: %s",rs.getString(2)));
                    System.out.println(String.format("Last name: %s",rs.getString(3)));
                    System.out.println(String.format("Age: %d",rs.getInt(4)));  
                }
            else if (current_table.equals("address"))
                while(rs.next())  {
                    System.out.println("*ADDRESS*");
                    System.out.println(String.format("Address id=%d",rs.getInt(1)));
                    System.out.println(String.format("Country: %s",rs.getString(2)));
                    System.out.println(String.format("City: %s",rs.getString(3)));
                    System.out.println(String.format("Street: %s",rs.getString(4)));
                }
//            con.close();
        }
        catch (Exception e) {
            System.out.println("sel "+e);
        }
    }
    
    public void update(HttpServletRequest request) {
        dbConnect(request);
        if(con==null)return;
        
        try {
            System.out.println("Last parameters:");
            select(request);
            
            JSONObject object=getJsonParameters(request);
            
            if(current_table.equals("user")) {
                PreparedStatement stmt=con.prepareStatement("UPDATE user SET first_name=?, last_name=?, age=? WHERE id=?");
                stmt.setString(1, object.getString("first_name"));                
                stmt.setString(2, object.getString("last_name"));
                stmt.setInt(3, object.getInt("age"));
                stmt.setInt(4, current_id);
                
                stmt.executeUpdate();
            }
            else if (current_table.equals("address")) {
                PreparedStatement stmt=con.prepareStatement("UPDATE address SET country=?, city=?, street=? WHERE id=?");
                stmt.setString(1, object.getString("country"));                
                stmt.setString(2, object.getString("city"));
                stmt.setString(3, object.getString("street"));
                stmt.setInt(4, current_id);
                
                stmt.executeUpdate();
            }
            
            System.out.println("New parameters:");
            select(request);
            
//            con.close();
        }
        catch (Exception e) {
            System.out.println("up "+e);
        }
    }
    
    public void insert(HttpServletRequest request, HttpServletResponse response) {
        dbConnect(request);
        if(con==null)return;
        
        try{
            JSONObject object=getJsonParameters(request);
            
            if(current_table.equals("user")) {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO user (first_name, last_name, age) VALUES (?, ?, ?)");
                stmt.setString(1, object.getString("first_name"));                
                stmt.setString(2, object.getString("last_name"));
                stmt.setInt(3, object.getInt("age"));
                
                stmt.executeUpdate();
                
                
                stmt=con.prepareStatement("SELECT id FROM user WHERE first_name=? and last_name=?");
                stmt.setString(1, object.getString("first_name"));
                stmt.setString(2, object.getString("last_name"));
                
                // for redirect
                ResultSet rs=stmt.executeQuery();
                Integer id_new=null; 
                while(rs.next())id_new=rs.getInt("id");
                String str = request.getRequestURI();
                response.sendRedirect(str.substring(0, str.length()-1)+id_new);
                
                System.out.println(String.format("User %s %s, created.",
                                                 object.getString("first_name"),
                                                 object.getString("last_name")));
            }
            else if (current_table.equals("address")) {
                PreparedStatement stmt = con.prepareStatement("INSERT INTO address (country, city, street) VALUES (?, ?, ?)");
                stmt.setString(1, object.getString("country"));                
                stmt.setString(2, object.getString("city"));
                stmt.setString(3, object.getString("street"));
                
                stmt.executeUpdate();
                
                stmt=con.prepareStatement("SELECT id FROM address WHERE country=? and city=? and street=?");
                stmt.setString(1, object.getString("country"));                
                stmt.setString(2, object.getString("city"));
                stmt.setString(3, object.getString("street"));
                
                ResultSet rs=stmt.executeQuery();
                Integer id_new=null; 
                while(rs.next())id_new=rs.getInt("id");
                String str = request.getRequestURI();
                response.sendRedirect(str.substring(0, str.length()-1)+id_new);
                
                System.out.println(String.format("Address %s, %s, %s, created",
                                                 object.getString("country"),
                                                 object.getString("city"),
                                                 object.getString("street")));
            }
//            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void delete(HttpServletRequest request) {
        dbConnect(request);
        if(con==null)return;
        
        try {
            if(current_table.equals("user")) {
                PreparedStatement stmt=con.prepareStatement("SELECT first_name, last_name FROM user WHERE id=?");
                stmt.setInt(1, current_id);
                ResultSet rs=stmt.executeQuery();
                
                String first_name=null;
                String last_name=null;
                while(rs.next())  {
                    first_name=rs.getString("first_name");
                    last_name=rs.getString("last_name");
                }

                stmt = con.prepareStatement("DELETE FROM user WHERE id=?");
                stmt.setInt(1, current_id);
                
                stmt.executeUpdate();
                
                System.out.println(String.format("User %s %s, deleted.",first_name,last_name));
            }
            else if (current_table.equals("address")) {
                PreparedStatement stmt=con.prepareStatement("SELECT country, city, street FROM address WHERE id=?");
                stmt.setInt(1, current_id);
                ResultSet rs=stmt.executeQuery();
                
                String[] address=new String[3];
                while(rs.next())  {
                    address[0]=rs.getString("country");
                    address[1]=rs.getString("city");
                    address[2]=rs.getString("street");
                }
                
                stmt = con.prepareStatement("DELETE FROM address WHERE id=?");
                stmt.setInt(1, current_id);
                
                stmt.executeUpdate();
                System.out.println(String.format("Address %s, %s, %s, deleted.",address[0],address[1],address[2]));
            }
            
//            con.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
       
    }
    
    private Integer getID(HttpServletRequest request){
        Integer id_i=null;  
        
        
        try{
            String [] id_s = request.getPathInfo().split("/", 3);
            id_i = Integer.valueOf(id_s[1]);
        }catch (Exception e) {
            System.out.println(e);
        }
        return id_i;
    }
    
    private JSONObject getJsonParameters(HttpServletRequest request) {
        if(request.getMethod().equals("GET"))return null;
        try {
            StringBuilder buffer=new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while((line=reader.readLine())!=null) {
                buffer.append(line);
            }
            
            String bodyJSON=buffer.toString();
            JSONObject obj = new JSONObject(bodyJSON);
            
            /*
            Iterator<String> keys=obj.keys();
            while(keys.hasNext()) {
                String key=keys.next().toString();
                var param=obj.get(key);
                System.out.println(key+": "+param);
            }
            */
            
            return obj;
            
        }catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
    
    
    /*
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
    */
    
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
}
