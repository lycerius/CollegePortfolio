package wit.ncp;

public class StringHasher {
	
	public static int hash(String str)
	{
		int result = 0;
		for(int i = 0; i < str.length(); i++) result += str.charAt(i)*i;
		return result;
	}

}
