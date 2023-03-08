import java.sql.*;
import java.util.*;
public class CustomerBankAccountMgmt {
    public static void main(String[] args){
        String name;
        String id;
        String password;
        String email;
        String lname;
        double deposit;
        Scanner sc = new Scanner(System.in);
        System.out.println("*****************Welcome To First Century Bank*****************");
        char c;
        System.out.println("Are you an existing customer(y/n)?");
        c = sc.next().charAt(0);        
        if(c == 'y' || c== 'Y'){
            login.main(args);
        }
        else{
            System.out.println("Enter Your first name:");
            name = sc.next();
            System.out.println("Enter your Last name:");
            lname = sc.next();
            System.out.println("Create your unique integer id of length 8:");
            id=sc.next();
            System.out.println("Create a Strong password:");
            password=sc.next();
            System.out.println("Enter your Email:");
            email=sc.next();
             try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                 System.out.println("Driver Error!");
            }
            
        try {
            Connection con;
            con = DriverManager.getConnection("jdbc:mysql://localhost/customer","root","");
            System.out.println("Since you just created the account, Enter the amount to deposit:");
            deposit = sc.nextDouble();
            if(deposit>0){
                PreparedStatement pst = con.prepareStatement("insert into signup(id,name,lname,password,email,amt)values(?,?,?,?,?,?)");
                pst.setString(1, id);
                pst.setString(2, name);
                pst.setString(3, lname);
                pst.setString(4, password);
                pst.setString(5, email);
                pst.setDouble(6,deposit);
                pst.executeUpdate();
                System.out.println("Sign up Successful!");
                login.main(args);
            }else{
                System.out.println("Error signing up");
                System.out.println("You cannot deposit amount less than or equal to zero!");
            }
        } catch (SQLException ex) {
            System.out.println("Something went wrong!");
             }
        }   
    }   
 }
  
