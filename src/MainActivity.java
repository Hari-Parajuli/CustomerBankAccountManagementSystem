import java.sql.*;
import java.util.*;
public class MainActivity {
    public static void main(String[] args) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int choice;
        String id;
        double dpAmt;
        Scanner sc = new Scanner(System.in);
        while(true){
        System.out.println("********Our Services********");
        System.out.println(" 1) Deposit\n 2) Withdraw\n 3) Transfer\n 4) Balance Check\n 5) Exit");
        System.out.println("****************************");
        System.out.println("Enter your choice: ");
        choice = sc.nextInt();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Error!");
        }
        switch(choice){
            case 1:
            {
                System.out.println("Enter your ID:");
                id = sc.next();
                System.out.println("Enter amount to deposit:");
                dpAmt= sc.nextDouble();
                if(dpAmt>0){
                    try{
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/customer","root","");
                        stmt = con.prepareStatement("SELECT amt FROM signup WHERE ID = ?");
                        stmt.setString(1, id);
                        rs = stmt.executeQuery();

                        if (rs.next()) {
                            Double currentNumber = rs.getDouble(1);
                            double newNumber = currentNumber + dpAmt;
                            stmt = con.prepareStatement("UPDATE signup SET amt = ? WHERE ID = ?");
                            stmt.setDouble(1, newNumber);
                            stmt.setString(2, id);
                            stmt.executeUpdate();

                            System.out.println("You have deposited RS "+dpAmt);
                            System.out.println("Your current balance is Rs "+newNumber);
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
                    System.out.println("You cannot deposit amount less than or equal to zero!");
                }
                break;
            }
            case 2:
            {
                Withdraw.main(args);
                break;
            }
            case 3:
            {
                Transfer.main(args);
                break;
            }
            case 4:
            {
                BalanceCheck.main(args);
                break;
            }
            case 5:
            {
                System.out.println("********Thank You For Using Our Services********");
                System.exit(0);
                break;
            }
            default:
            {
                System.out.println("Enter a valid choice!");
            }
        }
        
        }
    }
}
