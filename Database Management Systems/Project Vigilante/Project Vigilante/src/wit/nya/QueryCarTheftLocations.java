package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QueryCarTheftLocations extends SealedBaseQuery{

	//Gender, Height, Model, Brand, CountOfCars
	private static String qstring = "SELECT car_theft.License as License ,Car_Theft.Model as Model, Car_Theft.Brand as Brand, location.Street_Name as \"Street Name\", location.BuildingNumber as \"Building#\", location.ZipCode as Zip FROM car_theft JOIN crime ON car_theft.ID = crime.ID JOIN report ON report.RID = crime.ID JOIN location ON location.ID = report.RID ORDER BY Car_Theft.Type";
	
	public QueryCarTheftLocations(Connection ref) {
		super(qstring, ref);
	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		return ps;

	}
}
