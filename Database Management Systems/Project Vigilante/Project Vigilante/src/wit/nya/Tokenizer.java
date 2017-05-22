package wit.nya;

import java.util.ArrayList;

public class Tokenizer {
	
	private final char[] source;
	private int ptr = 0;
	private static final char[] delims = 
			" `~!@#$%^&*()_+-{}[]|\\=;:\"'?/><.,".toCharArray();
	
	
	public Tokenizer(String source)
	{
		this.source = source.toCharArray();
	}
	
	public Token[] compileTokens()
	{
		ptr = 0;
		boolean negativity = true;
		ArrayList<Token> tokens = new ArrayList<Token>();
		while(eatSpaces())
		{
			
			char c = source[ptr];
			if(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')
			{
				
				tokens.add(new Token(getWord(),Token.TYPE.WORD));
				negativity = false;
			}
			
			else if(c >= '0' && c <= '9' || c == '-' && negativity)
			{
				tokens.add(new Token(getNumber(),Token.TYPE.NUMBER));
				negativity = false;
			}
			else if(c == '"' || c == '\'')
			{
				tokens.add(new Token(getString(),Token.TYPE.STRING));
				negativity = false;
			}
			else if(c != '"' && c != '\'' && isDelim(c))
			{
				tokens.add(new Token(getSymbol(),Token.TYPE.SYMBOL));
				negativity = true;
			}
			
		}
		return tokens.toArray(new Token[0]);
	}
	
	private String getString()
	{
		char lookfor = source[ptr];
		ptr++;
		StringBuilder string = new StringBuilder();
		while(ptr < source.length)
		{
			if(source[ptr] != lookfor) string.append(source[ptr]);
			else break;
			ptr++;
		}
		ptr++;
		return stringOrNull(string.toString());
	}
	
	private String getNumber()
	{
		boolean decimal = false;
		StringBuilder number = new StringBuilder();
		if(source[ptr] == '-') {number.append('-');ptr++;}
		while(ptr < source.length)
		{
			
			if(source[ptr] == '.' && !decimal)
			{
				decimal = true;
				number.append('.');
			}
			else if(source[ptr] >= '0' && source[ptr] <= '9')
			{
				number.append(source[ptr]);
			}
			else break;
			
			ptr++;
		}
		return stringOrNull(number.toString());
	}
	
	private String getWord()
	{
		StringBuilder word = new StringBuilder();
		while(ptr < source.length) 
		{
			if(!isDelim(source[ptr])) word.append(source[ptr]);
			else break;
			ptr++;
		}
		return stringOrNull(word.toString());
	}
	
	private String getSymbol()
	{
		String symbol = null;
		if(isDelim(source[ptr])) {
			 symbol = Character.toString(source[ptr]);
		}
		ptr++;
		return stringOrNull(symbol);
	}
	
	private boolean eatSpaces(){
		while(ptr < source.length && (source[ptr] == ' ' 
				|| source[ptr] == '\n' 
				|| source[ptr] == '\t'))  
			
				ptr++;
		return ptr < source.length;
	}
	
	private boolean isDelim(char c)
	{
		for(char k : delims) {
			if(c == k) return true;
		}
		return false;
	}
	
	private static String stringOrNull(String s)
	{
		if(s.length()==0 || s == null) return null;
		else return s;
	}
	
	
	

}
