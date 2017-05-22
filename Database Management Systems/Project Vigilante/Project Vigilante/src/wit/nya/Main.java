package wit.nya;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class Main {

	private static Scanner input;
	private static SealedDatabase sealedDB;


	public static void error(String error)
	{
		System.err.println("ERROR: "+error);
	}

	public static String formatSize(String str, int toSize)
	{
		//return str;
		if(toSize < str.length()) return str;
		int sizeTo = toSize - str.length();
		return str + (new String(new char[sizeTo])).replace('\0', ' ');
	}



	/**
	 * The actual front end. 
	 * Gets user input and executes the desired queries
	 * @throws SQLException 
	 */
	public static boolean frontEnd() throws SQLException{

		
		Token[] nextLine;

		try
		{

			System.out.println("type 'help' for help");
			
			while(true)
			{
				//Retrieve the next command line input
				nextLine = nextCommand();
				
				//0 length means empty line
				if(nextLine.length == 0) continue;
				
				//Quit the application
				else if(nextLine[0].data.equalsIgnoreCase("quit")) quit();
				//Display help information
				else if(nextLine[0].data.equalsIgnoreCase("help"))
				{
					log("Available Commands:");
					log("help                   - displays this prompt");
					log("query                  - executes a query");
					log("query -help | query -h - Gets the queries available");
					log("quit                   - quits the application");
					log("logout                 - logs out, allowing you to log into another user");
				}
				//Execute a query
				else if(nextLine[0].data.equalsIgnoreCase("query") && nextLine.length >= 2)
				{
					
					
						if(nextLine[1].data.equals("-"))
						{
							if(nextLine[2].data.equals("help") || nextLine[2].data.equals("h"))
							{
								log("Available queries:");
								log("getcartheftlocations   - Gets the Car Thefts by Location");
								log("getstreetreports       - Gets the Crime Reports by Street");
								log("getsuspectinfo [name]  - Gets the Info for a suspect");
								log("getreportheatmap       - Gets the amount of reports per user");
								log("getmyprivs             - Gets your current privilege set");
							}
							else
							{
								log("Invalid flag argument '"+nextLine[2].data);
							}
						}
						else
						{
							ArrayList<String> params = new ArrayList<String>();
							
							for(int i = 2; i < nextLine.length; i++)
							{
								params.add(nextLine[i].data);
							}
							
							executeQuery(nextLine[1].data, params.toArray(new String[0]));
						}
						
					
					
				}
				else if(nextLine[0].data.equalsIgnoreCase("logout"))
				{
					log("Logged Out!");
					return true;
				}
				else
				{
					log("Unknown Command '"+nextLine[0].data+"'");
				}
				
			}
			
		}catch(NoSuchElementException e)
		{
			return false;
		}
		

	}

	/**
	 * Entry point into the program.
	 * Gathers user information data to login to the database
	 * @throws SQLException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static void hello(String root) throws SQLException, NoSuchAlgorithmException{

		String inputS;
		

		do
		{
			System.out.print("Would you like to login or register as a new user? (new | login | quit): ");
			inputS = input.nextLine();
			if(inputS.equalsIgnoreCase("new"))
			{
				System.out.print("NEW Username: ");
				String username = input.nextLine();
				System.out.print("NEW Password: ");
				String password = input.nextLine();
				sealedDB = new SealedDatabase(username, password, root);
				boolean success = sealedDB.register();
				if(!success)
				{
					log("Username ("+username+") already exists!");
					continue;
				}
				break;
			}
			else if(inputS.equalsIgnoreCase("login"))
			{
				System.out.print("Username: ");
				String username = input.nextLine();
				System.out.print("Password: ");
				String password = input.nextLine();
				sealedDB = new SealedDatabase(username, password, root);
				if(!sealedDB.login())
				{
					log("Invalid Username/Password combo");
					continue;
				}
				break;
			}
			else if(inputS.equalsIgnoreCase("quit"))
			{
				quit();
			}
			else
			{
				System.out.println("Invalid Choice \""+inputS+"\"");
			}
		}while(true);

	}

	public static void log(String msg)
	{
		System.out.println(msg);
	}

	/**
	 * Entry into the program<br>
	 * The program will execute differently depending on arguments that are supplied:
	 * <list>
	 * 	<li> if the first argument = -f; the input will be read from a file
	 * 	<li> if the argument list is > 0; then the program will execute using the argument list as input
	 * 	<li> if the argument list = 0; the program will retrieve input from the user
	 * </list>
	 * @param args Command line arguments, follow directions above
	 */
	public static void main(String[] args) {

		try{
			//User has given us some directions
			if(args.length != 0){
				//Use file
				if(args[0].equalsIgnoreCase("-f"))
				{
					if(args.length != 2)
					{
						error("Use File flag requires expects 2 args, got "+args.length);
						System.exit(-1);
					}
					else
					{
						File f = new File(args[1]);
						if(!f.exists())
						{
							error("File "+args[1]+" doesn't exist!");
							System.exit(-1);
						}
						else
						{
							input = new Scanner(new FileInputStream(f));


						}
					}

				}
				//Use command line arguments
				else
				{
					StringBuilder builder = new StringBuilder();
					for(int i = 0; i < args.length; i++)
					{
						builder.append(args[i]);
						builder.append('\n');
					}

					input = new Scanner(new StringReader(builder.toString()));

				}
			}
			else // Use input from user
			{
				input = new Scanner(System.in);
			}

		
			if(args.length == 3) input = new Scanner(System.in);
			
			System.out.println("Welcome to the Project Vigilante Front End!");
			System.out.print("Does the root account for your MySQL db have a password? (yes | default: no): ");
			String inputS = input.nextLine();
			String root = "";
			if(inputS.equalsIgnoreCase("yes"))
			{
				System.out.print("Please input your root password: ");
				root = input.nextLine();
			}
			do
			{
				hello(root);
				if(args.length == 3) input = new Scanner(System.in);
			}while(frontEnd());
			
			input.close();
		}catch(Exception e)
		{
			error("ERROR: an error has occured:");
			e.printStackTrace(System.err);
			System.exit(-1);
		}

	}


	public static String printOutResults(ResultSet set)
	{
		try
		{


			ResultSetMetaData meta = set.getMetaData();
			StringBuilder builder = new StringBuilder();
			int columncount = meta.getColumnCount();
			int[] columnSizer = new int[columncount];

			for(int i = 0; i < columncount; i++)
			{
				columnSizer[i] = meta.getColumnLabel(i+1).length()+1;
			}

			String[] columnNames = new String[columncount];

			ArrayList<ArrayList<String>> printResultSets = new ArrayList<ArrayList<String>>();
			//String[][] printResultSets = new String[set.getFetchSize()][columncount];
			for(int i = 1; i <= columncount; i++)
			{
				columnNames[i-1] = meta.getColumnLabel(i); 
			}
			
			int currentSet = 0;
		
			while(set.next())
			{
				
				printResultSets.add(new ArrayList<String>());
				for(int i = 1; i <= columncount; i++)
				{
					Object obj = set.getObject(i);
					String str;
					if(obj == null) str = "NULL";
					else str = set.getObject(i).toString();
					
					
					if(str.length() >= columnSizer[i-1]) columnSizer[i-1] = str.length()+1;
					printResultSets.get(currentSet).add(str);
				}
				currentSet++;
				
			}
			for(int i = 0; i < columncount; i++)
			{
				builder.append(' '+formatSize(columnNames[i], columnSizer[i])+"=");
			}
			builder.append('\n');
			
			//int length = 0;
			//for(int i : columnSizer) length += i;
			
			//String columnSplicer = new String(new char[length]).replace('\0', '-');
			for(int i = 0; i < currentSet; i++)
			{
				
				
				for(int f = 0; f < columncount; f++)
				{
					
					builder.append(' '+formatSize(printResultSets.get(i).get(f),columnSizer[f])+"=");
				}
				builder.append('\n');
				//builder.append('\n'+columnSplicer+'\n');
			}
			return builder.toString();
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public static Token[] nextCommand(){
		System.out.print(sealedDB.getUsername()+"@vigilante: ");
		String inputS = input.nextLine();
		Tokenizer tokenizer = new Tokenizer(inputS);
		return tokenizer.compileTokens();
	}
	
	public static void quit()
	{
		System.out.println("Bye!");
		System.exit(0);
	}
	
	public static boolean executeQuery(String which, String[] args) throws SQLException
	{
		if(which.equalsIgnoreCase("getcartheftlocations"))
		{
			ResultSet rs = sealedDB.getCarTheftLocations();
			System.out.println("===============================================");
			System.out.println("= Car Theft Locations                         =");
			System.out.println("===============================================");
			
			String result = printOutResults(rs);
			System.out.print(result);
			System.out.println("================================================");
			return true;
		}
		else if(which.equalsIgnoreCase("getstreetreports"))
		{
			ResultSet rs = sealedDB.getReportsPerStreet();
			System.out.println("===============================================");
			System.out.println("= Reports Per Street                          =");
			System.out.println("===============================================");
			String toPrint = printOutResults(rs);
			log(toPrint);
			System.out.println("================================================");
			return true;
		}
		else if(which.equalsIgnoreCase("getsuspectinfo"))
		{
			if(args.length == 1)
			{
				String suspectName = args[0];
				ResultSet rs = sealedDB.getSuspectInfo(suspectName);
				System.out.println("===============================================");
				System.out.println("= Suspect Info for '"+suspectName+"'");
				System.out.println("===============================================");
				String toPrint = printOutResults(rs);
				log(toPrint);
				System.out.println("================================================");
				return true;
			}
			return false;
		}
		else if(which.equalsIgnoreCase("getreportheatmap")){
			ResultSet rs = sealedDB.getReportsPerUser();
			System.out.println("===============================================");
			System.out.println("= Reports Per User                            =");
			System.out.println("===============================================");
			String toPrint = printOutResults(rs);
			log(toPrint);
			System.out.println("================================================");
			return true;
			
		}
		else if(which.equalsIgnoreCase("getmyprivs"))
		{
			ResultSet rs = sealedDB.getMyPrivilages();
			System.out.println("===============================================");
			System.out.println("= My Privilages                               =");
			System.out.println("===============================================");
			String toPrint = printOutResults(rs);
			log(toPrint);
			System.out.println("================================================");
			return true;
			
		}
				
		else
		{
			log("Unavailable query '"+which+"'");
			return false;
		}
	}

}
