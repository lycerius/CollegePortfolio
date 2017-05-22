package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryGetSuspectInformation extends SealedBaseQuery{
	
	private static String qstring = "select s.ID as ID, w.weapon as Weapon, a.items as Items, cr.type as CrimeType, cr.description as Description from suspect s left join weapon w on w.suspect_id=s.id left outer join commit c on c.suspect_id=s.id left outer join crime cr on cr.id=c.crime_id join accessories a on a.suspect_id=s.id  where s.name=? order by c.suspect_id ASC, cr.type ASC;";
	private String name;
	
	public QueryGetSuspectInformation(Connection ref, String suspectName)
	{
		super(qstring, ref);
		name = suspectName;
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		ps.setString(1, name);
		return ps;
	}
	
	

}
