package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryGetIdForEmail extends SealedBaseQuery{
	private static String GET_ID_FOR_EMAIL = "select id from users where email=?";
	
	private String email;
	
	public QueryGetIdForEmail(Connection ref, String email)
	{
		super(GET_ID_FOR_EMAIL, ref);
		this.email = email;
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		ps.setString(1, email);
		return ps;
	}

}
