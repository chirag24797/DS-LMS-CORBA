import HelloApp.*; 
import org.omg.CosNaming.*; 
import org.omg.CosNaming.NamingContextPackage.*; 
import org.omg.CORBA.*; 
import java.util.*;

public class HelloClient 
{ 
	static Hello helloImpl; 
	public static void main(String args[]) 
	{ 
		try 
		{  
			// create and initialize the ORB 
			ORB orb = ORB.init(args, null); 
			// get the root naming context
			org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
			// Use NamingContextExt instead of NamingContext. This is  
			// part of the Interoperable naming Service.
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef); 
			// resolve the Object Reference in Naming 
			String name = "Hello"; 
			helloImpl = HelloHelper.narrow(ncRef.resolve_str(name)); 
			System.out.println("Obtained a handle on server object: " + helloImpl); 
			
			
			Scanner sc=new Scanner(System.in);
			//LMSInterface lms=(LMSInterface)Naming.lookup("//localhost/LMSImpl");
			String bookName=null;
		    int amount;
			System.out.println("\nLibrary Management System\n");
			System.out.println("Available Books:\n1. JAVA\n2. DS\n3. AIT\n4. SE");
			System.out.println("1. Book Issue\n2. Book Return\n3. Display Status\n4.Exit");
			System.out.println("Enter choice:");
			int ch=sc.nextInt();
		    do
			{
			switch(ch)
			{
				case 1:
				System.out.println("Enter Name:");
				bookName=sc.next();
			    System.out.println("Enter No. of Books to Issue");
				amount=sc.nextInt();
				System.out.println(helloImpl.issueBook(bookName,amount));
				
				break;

				case 2:
				System.out.println("Enter Name:");
				bookName=sc.next();
				System.out.println("Enter No. of Books to Return");
				amount=sc.nextInt();
				System.out.println(helloImpl.returnBook(bookName,amount));
				
				break;

				case 3:
			    System.out.println("The Current Status");
				System.out.println("Book  \tStatus");
				for(int i=0;i<4;i++)
				{
					System.out.println(helloImpl.displayBook(i));
				}
				break;	
			}
			System.out.println("1. Book Issue\n2. Book Return\n3. Display Status\n4.Exit");
			System.out.println("Enter choice:");
			ch=sc.nextInt();
		    
		}while(ch<4); 
			
			
			
			helloImpl.shutdown(); 
		}
		catch (Exception e) 
		{ 
			System.out.println("ERROR : " + e) ; 
			e.printStackTrace(System.out); 
		} 
	} 
}