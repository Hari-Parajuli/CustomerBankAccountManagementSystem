import java.sql.*;
import java.util.*;
public class Withdraw {
    public static void main(String[] args) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String id;
        double wAmt;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your ID:");
        id = sc.next();
        System.out.println("Enter withdrawal amount:");
        wAmt = sc.nextDouble();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Error!");
        }
        if (wAmt > 0) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost/customer", "root", "");
                stmt = con.prepareStatement("SELECT amt FROM signup WHERE ID = ?");
                stmt.setString(1, id);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    Double currentNumber = rs.getDouble(1);
                    if (currentNumber >= wAmt) {
                        double newNumber = currentNumber - wAmt;
                        stmt = con.prepareStatement("UPDATE signup SET amt = ? WHERE ID = ?");
                        stmt.setDouble(1, newNumber);
                        stmt.setString(2, id);
                        stmt.executeUpdate();
                        System.out.println("You have withdrawn RS " + wAmt);
                        System.out.println("Your current balance is Rs " + newNumber);
                    } else {
                        System.out.println("You don't have enough balance!");
                    }

                } else {
                    System.out.println("No record found with the specified ID.");
                }
                // Close the resources
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error Fetching the data!");
            }
        } else {
            System.out.println("Invalid withdrawal amount!");
        }
    }
}
