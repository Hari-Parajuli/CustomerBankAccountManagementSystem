import java.sql.*;
import java.util.*;
public class Transfer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String senderId, receiverId;
        double transferAmount;
        System.out.println("Enter your ID:");
        senderId = sc.next();
        System.out.println("Enter receiving ID:");
        receiverId = sc.next();
        System.out.println("Enter Amount to transfer:");
        transferAmount = sc.nextDouble();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Error!");
        }
        if(transferAmount>0){
            try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/customer","root","");
            String sql = "SELECT * FROM signup WHERE id=?";
            PreparedStatement pstSender = con.prepareStatement(sql);
            pstSender.setString(1, senderId);
            rs = pstSender.executeQuery();
            if (rs.next()) {
                PreparedStatement pstReceiver = con.prepareStatement(sql);
                pstReceiver.setString(1, receiverId);
                rs = pstReceiver.executeQuery();
                if (rs.next()) {
                    stmt = con.prepareStatement("SELECT amt FROM signup WHERE id=" + senderId);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        double senderAmt = rs.getDouble("amt");
                        if (senderAmt >= transferAmount) {
                            double newSenderAmt = senderAmt - transferAmount;
                            stmt.executeUpdate("UPDATE signup SET amt=" + newSenderAmt + " WHERE id=" + senderId);
                            stmt = con.prepareStatement("SELECT amt FROM signup WHERE id=" + receiverId);
                            rs = stmt.executeQuery();
                            if (rs.next()) {
                                double receiverAmt = rs.getDouble("amt");
                                double newReceiverAmt = receiverAmt + transferAmount;
                                stmt.executeUpdate("UPDATE signup SET amt=" + newReceiverAmt + " WHERE id=" + receiverId);
                                stmt = con.prepareStatement("SELECT amt FROM signup WHERE id=" + senderId);
                                rs = stmt.executeQuery();
                                if (rs.next()) {
                                    System.out.println("Amount transferred successfully!");
                                    System.out.println("You've transferred RS "+transferAmount+" to "+receiverId+" ID");
                                    System.out.println("Your available Balance is RS : " + rs.getDouble("amt"));
                                }
                            } else {
                                System.out.println("Receiving user's ID not found");
                            }
                        } else {
                            System.out.println("You don't have enough funds available!");
                        }
                    } else {
                        System.out.println("You have entered wrong ID");
                    }
                } else {
                    System.out.println("Receiver ID not found!");
                }
            } else {
                System.out.println("You entered your ID wrong!");
            }
            rs.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error Fetching the Data!");
            }
        }else{
            System.out.println("Transfer Amount cannot be less than or equal to zero!");
        }
    }
}
