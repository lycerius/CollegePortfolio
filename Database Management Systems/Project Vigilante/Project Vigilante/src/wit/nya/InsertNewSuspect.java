package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertNewSuspect extends SealedBaseQuery{
	//Race,Gender,LastSeenDate,Height,Name
	private static String query = "insert into suspect values(null, ?, ?, ?, ?, ?, ?)";

	String gender;
	int heightcm;
	String lastSeenDate;
	String name;
	String race;

	public InsertNewSuspect(Connection ref, String name, String race, String gender, String lastSeenDate, int heightcm)
	{
		super(query, ref);
		this.name = name;
		this.gender = gender;
		this.race = race;
		this.lastSeenDate = lastSeenDate;
		this.heightcm = heightcm;

	}

	@Override
	public PreparedStatement applyParameters(PreparedStatement ps) throws Exception {
		ps.setString(1, race);
		ps.setString(2, gender);
		ps.setString(3, lastSeenDate);
		ps.setInt(4, heightcm);
		ps.setString(5, name);
		return ps;
	}



}
