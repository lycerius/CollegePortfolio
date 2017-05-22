package wit.nya;

public class Token {
	
	public enum TYPE{
		NUMBER,STRING,WORD,SYMBOL;
	}
	
	public final String data;
	public final TYPE type;
	
	public Token(String data,TYPE type)
	{
		this.data = data;
		this.type = type;
	}
	
	

}
