package wit.nya;

import java.sql.Connection;
import java.sql.PreparedStatement;


public abstract class SealedBaseQuery {

	private String qstring;
	private Connection ref;


	public SealedBaseQuery(String qstring, Connection ref)
	{
		this.qstring = qstring;
		this.ref = ref;
	}

	public PreparedStatement prepareStatement()
	{
		try {
			return applyParameters(ref.prepareStatement(qstring));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public abstract PreparedStatement applyParameters(PreparedStatement ps) throws Exception;

}
