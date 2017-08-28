import HelloApp.*; 
import org.omg.CosNaming.*; 
import org.omg.CosNaming.NamingContextPackage.*; 
import org.omg.CORBA.*; 
import org.omg.PortableServer.*; 
import org.omg.PortableServer.POA; 
import java.util.Properties;       

class HelloImpl extends HelloPOA 
{      
	private ORB orb;
	public void setORB(ORB orb_val) 
	{
		orb = orb_val;  
	}
	// implement sayHello() method this definition can be replaced with own method 
	
	String bks[]={"JAVA","DS","AIT","SE"};
	int quantity[]={10,10,10,10};
	
	
	
	public String issueBook(String bookName,int amount)
	{
		int i,flag=0;
		for(i=0;i<bks.length;i++)
		{
			if(bookName.equalsIgnoreCase(bks[i]))
			{
				flag=1;
				break;
			}
		}
			if(flag==1)
			{
				if(quantity[i]==0)
				{
					return "Books not available in Book "+bks[i];	
				}
				else
				{
					quantity[i]=quantity[i]-amount;
					return "Success!!";
				}
			}
		else
		return "Incorrect Account Name";
	}
 
	public String returnBook(String bookName,int amount)
	{
		int i,flag=0;
		for(i=0;i<bks.length;i++)
		{
		if(bookName.equalsIgnoreCase(bks[i]))
			{
				flag=1;
				break;
			}
			}
			if(flag==1)
			{
				if(quantity[i]==10)
				{
					return "Book Already Returned";
				}
				else
				{
				quantity[i]=quantity[i]+amount;
				return "Success!!";
				}
			}
		else
		return "Incorrect Account Name";
	}

 
	
	 
	public String displayBook(int i)
	{
		return (bks[i]+"\t"+quantity[i]);
	}
	// implement shutdown() method 
	public void shutdown() 
	{ 
	orb.shutdown(true); 
	} 
}

public class HelloServer 
{
	public static void main(String args[])  
	{ 
		try 
		{
			// create and initialize the ORB 
			ORB orb = ORB.init(args, null); 
			// get reference to rootpoa & activate the POAManager 
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA")); 
			rootpoa.the_POAManager().activate(); 
			// create servant and register it with the ORB 
			HelloImpl helloImpl = new HelloImpl();
			helloImpl.setORB(orb);
			// get object reference from the servant 
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl); 
			Hello href = HelloHelper.narrow(ref);
			// get the root naming context 
			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			// Use NamingContextExt which is part of the Interoperable  
			// Naming Service (INS) specification. 
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);  
			// bind the Object Reference in Naming 
			String name = "Hello"; 
			NameComponent path[] = ncRef.to_name( name ); 
			ncRef.rebind(path, href); 
			System.out.println("HelloServer ready and waiting ...");  
			// wait for invocations from clients 
			orb.run(); 
		}   
		catch (Exception e) 
		{ 
			System.err.println("ERROR: " + e); 
			e.printStackTrace(System.out); 
		}
		System.out.println("HelloServer Exiting ..."); 
	} 
}	