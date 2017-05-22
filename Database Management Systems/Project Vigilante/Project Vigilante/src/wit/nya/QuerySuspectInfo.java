package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QuerySuspectInfo extends SealedBaseQuery {
	
	private static String QUERY_SUSPECT_INFO = "select s.ID as suspectId, w.weapon as weapon, a.items as items, cr.type as crimeType, cr.description as crimeDescription  from suspect s left join weapon w on w.suspect_id=s.id  left outer join commit c on c.suspect_id=s.id left outer join crime cr on cr.id=c.crime_id  join accessories a on a.suspect_id=s.id order by suspect_id ASC, crime_type ASC;";

	public QuerySuspectInfo(Connection ref) {
		super(QUERY_SUSPECT_INFO, ref);
	}
	
	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		return ps;
		
	}
}
