package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryReportsPerUser extends SealedBaseQuery{

	private static String QUERY_REPORTS_PER_USER = ""+
	"select concat(users.ID,concat(':',users.Name)) as User, count(report.user_id) as Reports"
	+" from report join users on users.ID = report.user_id"
    +" group by report.user_id"
    +" order by users.Name ASC";
	
	public QueryReportsPerUser(Connection ref) {
		super(QUERY_REPORTS_PER_USER, ref);
	}
	
	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		return ps;
		
	}
	
}
