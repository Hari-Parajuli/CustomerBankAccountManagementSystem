import java.sql.*;
import java.util.*;
public class login {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String id;
        String pass;
        System.out.println("********Enter Your Login Credentials********");
        System.out.println("Enter your Id:");
        id=sc.next();
        System.out.println("Enter your Password:");
        pass=sc.next();
         try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println(ex);
            }
            
        try {
            Connection con;
            con = DriverManager.getConnection("jdbc:mysql://localhost/customer","root","");
           String sql = "Select * from signup where id=? and password=?";
           PreparedStatement pst = con.prepareStatement(sql);
           pst.setString(1, id);
           pst.setString(2, pass);
           ResultSet rs = pst.executeQuery();
           if(rs.next()){
               System.out.println("Logged in Successfully!");
               MainActivity.main(args);
           }
           else{
               System.out.println("ID or Password did not match!");
           }
           
        } catch (SQLException ex) {
            System.out.println("Error Fetching the data!");
        }
    }
}
