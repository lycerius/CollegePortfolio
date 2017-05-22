package wit.nya;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;

public class InsertNewUser extends SealedBaseQuery{
	
	private static MessageDigest md;
	private static String qstring = "insert into users values(null, ?,  ?, null, ?, ?)";
	private String userName;
	private String userPassword;
	
	static
	{
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	private SecureRandom random = new SecureRandom();
	
	public InsertNewUser(Connection ref, String username, String password)
	{
		super(qstring,ref);
		userName = username;
		userPassword = password;
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		ps.setString(1, userName);
		ps.setString(2, userName);
		int salt = random.nextInt();
		byte[] hash = md.digest((salt+userPassword).getBytes());
		String antihash = Base64.getEncoder().encodeToString(hash);
		ps.setInt(3, salt);
		ps.setString(4, antihash);
		return ps;
		
	}
	
	
	
	

}
