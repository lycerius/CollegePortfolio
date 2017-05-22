package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryGetPasswdInfo extends SealedBaseQuery {
	
	private static String GET_PW_INFO = "select salt,pwh from users where email=?";
	private String email;
	
	public QueryGetPasswdInfo(Connection ref, String email)
	{
		super(GET_PW_INFO, ref);
		this.email = email;
		
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception{
		ps.setString(1, email);
		return ps;
	}

}
