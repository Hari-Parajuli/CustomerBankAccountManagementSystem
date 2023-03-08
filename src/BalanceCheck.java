import java.sql.*;
import java.util.*;
public class BalanceCheck {
    public static void main(String[] args) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String id;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your ID:");
        id = sc.next();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Error!");
        }
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/customer","root","");
            String query = "SELECT amt FROM signup WHERE id = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                double amt = rs.getDouble("amt");
                System.out.println("Your available balance is RS : " + amt);
            } else {
                System.out.println("No matching ID found");
            }
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Error fetching the data!");
        }     
    }
}
