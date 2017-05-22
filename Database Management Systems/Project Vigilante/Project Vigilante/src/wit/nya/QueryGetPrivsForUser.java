package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryGetPrivsForUser extends SealedBaseQuery{
	
	private static String QUERY_USER_PRIV = "select privilege.Type as \"Privilege\" from privilege where privilege.ID in (select userspriv.PrivID from userspriv, users where users.ID=userspriv.UserID and users.ID = ?) order by privilege.Type ASC";
	
	private int uid;
	
	public QueryGetPrivsForUser(Connection ref, int uid)
	{
		super(QUERY_USER_PRIV, ref);
		this.uid = uid;
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		ps.setInt(1, uid);
		return ps;
	}
	
	
}
