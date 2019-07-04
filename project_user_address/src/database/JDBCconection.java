package database;

import java.sql.*;

public class JDBCconection {

    public Connection databaseConect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/api_project", "root", "root");
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from user");  
            
            while(rs.next())  
              System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
            
            con.close();  
//            System.out.println("Success sql db ... ");

        } catch (Exception e) {

            e.printStackTrace();

        }
        
        return null; 
    }
    
}
