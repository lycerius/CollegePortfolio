package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryCheckIfUserExists extends SealedBaseQuery{

	private static String qstring = "select count(*) from users where email=?";
	private String username;
	public QueryCheckIfUserExists(Connection ref, String username) {
		super(qstring,ref);
		this.username = username;
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		ps.setString(1, username);
		return ps;
	}



}
