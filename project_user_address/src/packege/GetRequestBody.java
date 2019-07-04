package packege;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.JDBCconection;

import java.sql.*;

public class GetRequestBody {
    
    
    
    public void getBody(HttpServletRequest request,HttpServletResponse response) throws IOException {

        JDBCconection conect=new JDBCconection();
        Connection con = conect.databaseConect();
        
        response.setContentType("text/html");
        PrintWriter pw  = response.getWriter();
        
        pw.println("<head><head><title>user</title></head><body>");
        
        pw.println(String.format("<p><b>URL: </b>%s</p>", request.getRequestURI()));
        pw.println(String.format("<p><b>Method: </b>%s</p>",request.getMethod()));

//        pw.println("<p><b>Headers: </b></p>");
//        Enumeration<String> headerNames = request.getHeaderNames();
//        
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//
//            pw.println(String.format("<p>%s = %s</p>", key, value));
//        }

        pw.println("<p><b>Body: </b></p>");
        pw.println(String.format("<p>%s</p>",extractPostRequestBody(request)));

        pw.println("<p><b>Url Parameters: </b></p>");
        
        Enumeration<String> params = request.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = (String)params.nextElement();
            pw.println(String.format("<p>\t%s = </p>",paramName,request.getParameter(paramName)));
        }
        
        String requri = request.getPathInfo();
        pw.println(String.format("<p>user id: %s</p>", requri));

        pw.println("</body></html>");
    }
    private void getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("Headers:");
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(String.format("%s = %s", key, value));
        }   
    }
    
    private String extractPostRequestBody(HttpServletRequest request) {
        if (request.getMethod() == "GET") {
            return "none";
        }
        
        Scanner s = null;
        try {
//          BufferedReader 
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
            s = scanner.useDelimiter("\\A");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.hasNext() ? s.next() : "";
    }
}
