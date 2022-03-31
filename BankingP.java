package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Bank{
	private int accno;
	private String acc_Holder_Name;
	private String acc_type;
	private long Balance;
	Scanner sc = new Scanner(System.in);
	
	//Method to Create Account
	void create_account() throws ClassNotFoundException, SQLException            
	{
		    //Getting Account Number from User Input
		    System.out.print("Enter Account No within 4 numbers: ");  
	        accno = sc.nextInt(); 
	      
	        //Getting Account Type from User Input
	        System.out.print("Enter Account type: ");  
	        acc_type = sc.next();  
	      
	        //Getting Account Name from User Input
	        System.out.print("Enter Name: ");  
	        acc_Holder_Name = sc.next();  
	      
	        //Getting Account Balance from User Input
	        System.out.print("Enter Balance: ");  
	        Balance = sc.nextLong(); 
	        
	       //Creating connection to Database
	 	   Class.forName("com.mysql.cj.jdbc.Driver");
	 	   Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankacc","root","#Therigokul0705");
	 	 
	 	  //Passing the Insert query and inserting the details in Database
	 	   PreparedStatement ps = con.prepareStatement("INSERT INTO details VALUES(?,?,?,?)");
		   ps.setInt(1, accno);
		   ps.setString(2, acc_type); 
		   ps.setString(3, acc_Holder_Name);
		   ps.setLong(4, Balance);
		   int i = ps.executeUpdate();
		   System.out.println("Welcome to Banking Services /n Your Account has Been Created");
	        
	      
	}
	
	//Method to Show account details
	public void show_account() throws ClassNotFoundException, SQLException 
	{  
		//Creating connection to Database
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankacc","root","#Therigokul0705");
	  
		//Passing the SELECT * query to get all account holders details
		PreparedStatement ps = con.prepareStatement("SELECT * FROM details");
        ResultSet rs = ps.executeQuery();
		
		while(rs.next()) 
		{
			System.out.println("The account number is: "+ rs.getString(1));
			System.out.println("The name is: "+ rs.getString(3));
			System.out.println("The account type is: "+ rs.getString(2));
			System.out.println("The balance is: "+ rs.getString(4));
		}
    
	}
	
	
	
	  
	  
//	  void search_account() throws ClassNotFoundException, SQLException {
//		  int accno;
//		  int vaccno = 0;
//		  System.out.println("Enter the account number you want to search: ");
//		  accno=sc.nextInt();
//		  Class.forName("com.mysql.cj.jdbc.Driver");
//			Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankacc","root","#Therigokul0705");
//			System.out.println("Connected Successfully");
//			PreparedStatement stmt=con1.prepareStatement("SELECT * FROM details WHERE account_no=?");
//			stmt.setInt(1, accno);
//			
//			ResultSet rs = stmt.executeQuery();
//			
//			while(rs.next()) 
//			{
//				vaccno=rs.getInt(1);
//			}
//			
//			if(vaccno==accno) 
//			{
//					System.out.println("The account number is: "+ rs.getString(1)+ ","+ "The name is: "+ rs.getString(3));
//					System.out.println("The account type is: "+ rs.getString(2)+"," + " The balance is: "+ rs.getString(4));
//				}
//				else 
//				{
//					System.out.println("The account Number is not correct Please Retry");
//				}
//	  }
//	  
	  //Method to check balance
	  void check_balance() throws ClassNotFoundException, SQLException {
		  int accno; 
		  int vaccno = 0;
		  
		  //Getting Account number from user
		  System.out.println("Enter the account number you want to search: ");
		  accno=sc.nextInt();
		  
		  //Creating connection to Database
		  Class.forName("com.mysql.cj.jdbc.Driver");
		  Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankacc","root","#Therigokul0705");
		  
		  //Passing the query to take the balance from specific account holder
		  PreparedStatement stmt=con1.prepareStatement("SELECT balance FROM details WHERE account_no=?");
		  stmt.setInt(1, accno);
          ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				vaccno=rs.getInt(1);
			}
			System.out.println("The balance is: "+ vaccno);
	  }
	  
	  
	  //Method to Withdraw account
	  public void withdraw() throws ClassNotFoundException, SQLException 
	  {
		  int user_input;
		  int verify = 0;
		  int bal = 0,no = 0;
		  long amt = 0;
		  
		  //Getting Account Number from user Input
		  System.out.println("Enter the account number you want to withdraw: ");
		  user_input=sc.nextInt();
		  
		  //Creating connection to Database
		  Class.forName("com.mysql.cj.jdbc.Driver");
		  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankacc","root","#Therigokul0705");
		
		  //Passing the query to get respective account details
		   PreparedStatement stmt=con.prepareStatement("SELECT * FROM details WHERE account_no=?");
		   stmt.setInt(1, user_input);
		   ResultSet rs = stmt.executeQuery();
			
     		while(rs.next()) 
			{
				verify=rs.getInt(1);
			}
			
				if(verify==user_input) 
				{
					PreparedStatement ps = con.prepareStatement("SELECT balance FROM details WHERE account_no=?");
					ps.setInt(1, user_input);
					ResultSet rs1 = ps.executeQuery();

					
					while(rs1.next()) 
					
					{
					 bal=rs1.getInt(1);
					}
					
					System.out.println(bal);
					
					System.out.println("Enter the amount you want to withdraw: "); 
		            amt = sc.nextLong();
		            
		            //Getting Password which is same as Account Number
			        System.out.println("Enter the Password: ");
		            no = sc.nextInt();
				}
				
				else
				{
					System.out.println("The account number is Incorrect");
				}
		         if (bal >= amt) 
					{ 
		        	     //Creating connection to Database
			        	Class.forName("com.mysql.cj.jdbc.Driver");
			 			Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankacc","root","#Therigokul0705");
			 			
			 			
			 			//Paasing the query to update the balance after  withdraw 
			 			PreparedStatement stmt1=con1.prepareStatement("UPDATE details SET balance=balance-? WHERE account_no=?");
			 			stmt1.setLong(1, amt);
			 			stmt1.setInt(2, no);
			 			int i=stmt1.executeUpdate();
			 			
			 			con.close();
			 			bal = (int) (bal - amt);  
			            System.out.println("Balance after withdrawal: " + bal);  
			        } 
					
					else 
			        {  
			            System.out.println("Your balance is less than " + amt + "\tTransaction failed...!!" );  
			        }  
					
				}
	  
	  //Method to Deposit the ammount
	  public void deposit() throws SQLException, ClassNotFoundException {
		  int verify = 0;
		  
		  //Getting the account number from user Input
		  System.out.println("Enter the account number you want to deposit: ");
		  int user_input = sc.nextInt();
		  Class.forName("com.mysql.cj.jdbc.Driver");
		  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankacc","root","#Therigokul0705");
			
		  //Passing the query to get respective account details
		   PreparedStatement stmt=con.prepareStatement("SELECT * FROM details WHERE account_no=?");
		   stmt.setInt(1, (int) user_input);
			ResultSet rs = stmt.executeQuery();
			
     	
			while(rs.next()) 
			{
				verify=rs.getInt(1);
			}
			
				if(verify==user_input) 
				{
					PreparedStatement ps = con.prepareStatement("SELECT balance FROM details WHERE account_no=?");
					ps.setInt(1, user_input);
					ResultSet rs1 = ps.executeQuery();

					
					int bal = 0;
					while(rs1.next()) 
					
					{
					 bal=rs1.getInt(1);
					}
					

					 System.out.println("Enter the amount you want to deposit: ");  
				        long amt = sc.nextLong();  
				        System.out.println("Enter the Password to deposit: ");
				        int password = sc.nextInt();
				        if(user_input==password)
				        {
				        Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankacc","root","#Therigokul0705");
						
						PreparedStatement stmt1=con1.prepareStatement("UPDATE details SET balance=balance+? WHERE account_no=?");
						stmt1.setLong(1, amt);
						stmt1.setInt(2, password);
						int i=stmt1.executeUpdate();
						
						
						PreparedStatement ps1 = con1.prepareStatement("SELECT balance FROM details WHERE account_no=?");
			     	    ps1.setInt(1, password);
			 			ResultSet rs11 = ps1.executeQuery();

						
						while(rs11.next()) 
						{
						 bal=rs11.getInt(1);
						}  
						System.out.println("The balance Amount is: "+ bal);
						con1.close();
				        }
				        else {
				        	System.out.println("The password is Incorrect Please Try Again");
				        }
				}
				
				else
				{
					System.out.println("The account number is Incorrect Please Try Again");
				}
	  }
			
}




		  
	  

	  
	  
public class BankingP {

	public static void main(String[] args) throws ClassNotFoundException, SQLException 
	{
		// TODO Auto-generated method stub
		
		Bank b = new Bank();

		Scanner sc = new Scanner(System.in);  

        int n = 0;  
        Bank C[] = new Bank[n];  

        int ch;  
        do {  
            System.out.println("\n ***Banking System Application***");  
            System.out.println("1.Create your Account \n  2. Display all account details   \n 3.Withdraw \n 4.Deposit \n 5.Check Balance \n 6.Exit");  
            System.out.println("Enter your choice: ");  
            ch = sc.nextInt();  
                switch (ch) {  
                    case 1:  
                       b.create_account(); 
                       break;  
                       case 2:  
                        b.show_account();
                        break;  
//                       case 3:  
//                    	b.search_account();
//                       break;  
                    case 3:  
                        b.withdraw();
                        break;  
                    case 4:  
                        b.deposit();  
                        break;  
                    case 5:
                    	b.check_balance();
                    	break;
                    case 6:
                    	System.out.println("See you Soon..!");
                    	break;
                }  
            }  
            while (ch != 5);  
        }  

        }

