package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryUserPriv extends SealedBaseQuery{

	private static String QUERY_USER_PRIV = "select privilege.Type as privilegeType from privilege where privilege.ID as privilegeId in (select userspriv.PrivID from userspriv, users where users.ID=userspriv.UserID and users.ID = ?) order by privilege.Type ASC";

	public QueryUserPriv(Connection ref) {
		super(QUERY_USER_PRIV, ref);
	}
	
	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		return ps;
		
	}
	
}
