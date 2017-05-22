package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateSuspectAttatchToCrime extends SealedBaseQuery {
	private static String query = "insert into commit values(?,?)";
	private int crimeid;
	private int suspectid;
	
	public UpdateSuspectAttatchToCrime(Connection ref, int crimeid, int suspectid)
	{
		super(query,ref);
		this.crimeid = crimeid;
		this.suspectid = suspectid;
		
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		ps.setInt(1, crimeid);
		ps.setInt(2, suspectid);
		return ps;
	}
}	
