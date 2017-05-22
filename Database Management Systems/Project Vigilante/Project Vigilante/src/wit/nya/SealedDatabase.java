package wit.nya;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import com.mysql.jdbc.Connection;


public class SealedDatabase {

	public enum CRIMETYPE{
		CARTHEFT;
	}


	private static enum PRIVCHECK
	{
		GENDER_SUSPECTS,REPORTS_PER_STREET,REPORTS_PER_USER,SUSPECT_INFO,USER_PRIV;
	}

	static{try{Class.forName("com.mysql.jdbc.Driver");}catch(Exception e){}}
	private String accessPrivs;
	private Connection connection;
	private int myUserId = -1;
	private String password;
	private String username;
	private MessageDigest md;

	public SealedDatabase(String username, String password, String dbPassword) throws SQLException, NoSuchAlgorithmException 
	{
		this.username = username;
		this.password = password;
		md = MessageDigest.getInstance("SHA-256");
		connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost","root",dbPassword);
		java.sql.PreparedStatement s = connection.prepareStatement("use vigilante");
		s.executeQuery();	
	}


	public boolean checkPrivs(PRIVCHECK QueryType)
	{
		switch(QueryType)
		{
		case REPORTS_PER_STREET: return accessPrivs.equalsIgnoreCase("registered");
		case GENDER_SUSPECTS: return accessPrivs.equalsIgnoreCase("registered");
		case SUSPECT_INFO: return accessPrivs.equalsIgnoreCase("admin");
		case REPORTS_PER_USER: return accessPrivs.equalsIgnoreCase("admin");
		case USER_PRIV: return accessPrivs.equalsIgnoreCase("admin");
		default: return false;
		}
	}


	/*public void createNewCarTheft(String description, String carModel, String carBrand, String license, String suspectName)
	{
		try
		{
			//First we check for suspect existence
			Integer sid = getSuspectByName(suspectName);
			//Suspect exists
			if(sid != -1)
			{

			}
			//Suspect does not exist
			else
			{

			}
		}catch(Exception e)
		{

		}

	}*/

	/**
	 * 
	 * @return
	 */
	public ResultSet getCarTheftLocations()
	{
		QueryCarTheftLocations qgs = new QueryCarTheftLocations(connection);
		try {
			return qgs.prepareStatement().executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @return
	 */
	public ResultSet getMyPrivilages()
	{
	
		QueryGetPrivsForUser qgpfu = new QueryGetPrivsForUser(connection, myUserId);
		try {
			return qgpfu.prepareStatement().executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public ResultSet getReportsPerStreet()
	{
		QueryReportsForStreet qrps = new QueryReportsForStreet(connection);
		try {
			return qrps.prepareStatement().executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @return
	 */
	public ResultSet getReportsPerUser()
	{
		QueryReportsPerUser qrpu = new QueryReportsPerUser(connection);
		try {
			return qrpu.prepareStatement().executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}


	/*private Integer getSuspectByName(String name)
	{
		
	}*/


	/**
	 * 
	 * @return
	 */
	public ResultSet getSuspectInfo(String byName)
	{
		QueryGetSuspectInformation qgsi = new QueryGetSuspectInformation(connection, byName);
		try {
			
			return qgsi.prepareStatement().executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}


	public boolean login() throws SQLException
	{

		QueryGetPasswdInfo pwiq = new QueryGetPasswdInfo(connection, username);
		ResultSet rs = pwiq.prepareStatement().executeQuery();

		if(rs.next())
		{

			String pwh = rs.getString("pwh");
			Integer osalt = rs.getInt("salt");

			byte[] mypasswordhash = md.digest((osalt+password).getBytes());
			byte[] therealhash = Base64.getDecoder().decode(pwh);

			for(int i = 0; i < therealhash.length; i++)
			{
				if(therealhash[i] != mypasswordhash[i])
				{
					return false;
				}
			}

			System.out.println("Access Granted");
			QueryGetIdForEmail qgife = new QueryGetIdForEmail(connection, username);

			rs = qgife.prepareStatement().executeQuery();
			if(rs.next())
			{
				myUserId = rs.getInt(1);

			}
			return true;

		}
		else
		{
			return false;
		}


	}

	public boolean register()
	{
		QueryCheckIfUserExists exists = new QueryCheckIfUserExists(connection, username);
		try{
			ResultSet rs = exists.prepareStatement().executeQuery();
			rs.next();
			if(rs.getInt(1) > 0)
			{
				return false;
			}
		}catch(Exception e){e.printStackTrace(); System.exit(-1);}
		InsertNewUser inu = new InsertNewUser(connection, username, password);
		
		try {
			inu.prepareStatement().execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
			return false;
		}
	


	}
	
	public String getUsername(){
		return username;
	}

}
