package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryReportsForStreet extends SealedBaseQuery{
	private static String QUERY_REPORTS_FOR_STREET = "select location.Street_Name as Street, count(report.crime_id) as Reports from report join location on location.ID = report.location_id group by location.Street_Name order by Street ASC";
				//Street,ReportCount

	
	public QueryReportsForStreet(Connection ref) {
		super(QUERY_REPORTS_FOR_STREET, ref);
		
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		return ps;
		
	}
	
	

}
