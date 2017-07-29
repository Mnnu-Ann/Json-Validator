import java.io.*;
import java.util.*;

public class Validator {
	
	public static void main(String[] args) throws IOException, ParseException {
		int i;
		char c;
		//Reads file
		FileReader fr = new FileReader("/Users/MinnuAnn/eclipse-workspace/Json/src/test.json");
		ArrayList<Character> jsonList = new ArrayList<Character>();
		//Copying file to Arraylist by avoiding spaces
		while ((i = fr.read()) != -1) {
			c = (char) i;
			if (!Character.isWhitespace(c)) {
				jsonList.add(c);
			}
		}
		fr.close();
		//json file validation
		Validate s = new Validate();
		int index = s.matchValidate(jsonList, 0);
		
		if ((index+1) == jsonList.size())
			System.out.println("Valid JSON" );
		else
			System.out.println("Invalid JSON" );
	}
}

class ParseException extends Exception{
	ParseException(String s)
	{
		super(s);
	}
}

class Validate{

	public int matchValidate(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index)=='{'){    
			index++;
			index = matchObject(js,index);
		}
		if(js.get(index)=='['){   
			index++;
			index = matchArray(js,index);
		}
		return index;
	}
	
	
	private int matchObject(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index) != '}') {
			index=matchMember(js,index);
		}
		if (js.get(index) != '}') {
			throw new ParseException("Missing } for Object at location "+index);
		}
		return index;
	}
	
	private int matchMember(ArrayList<Character> js, int index) throws ParseException {
		
		index = matchPair(js,index);
		while(js.get(index)!='}'){
			if(js.get(index)!=','){
				throw new ParseException("Invalid JSON \n Missing , or } in Member at location "+index);
			}
			index++;
			if (index >= js.size()) {
				throw new ParseException("Invalid JSON \n Missing } for Member at location "+index);
			}
			index=matchPair(js,index);
		}
		return index;
	}


	private int matchPair(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index) == '"') {
		    index = matchString(js,index);
			if(js.get(index) != ':') {
				throw new ParseException("Invalid JSON \n Missing : in pair at location "+index);
			}
			index++;
			if (index >= js.size()) {
				throw new ParseException("Invalid JSON \n Missing } in pair at location "+index);
			}
			index = matchValue(js,index);
		}
		else {
			throw new ParseException("Invalid JSON \n Missing pair in pair at location "+ index);
		}
		
		return index;
	}


	public int matchArray(ArrayList<Character> js,int index) throws ParseException
	{    
		if(js.get(index)!=']'){
		    index=matchValue(js,index);
		    
			while(js.get(index)!=']'){
				if(js.get(index)!=','){
					throw new ParseException("Invalid JSON \n Missing , or ] in array at location "+index);
				}
				index++;
				if (index >= js.size()) {
					throw new ParseException("Invalid JSON \n Missing ] for array at location "+index);
				}
				index=matchValue(js,index);
			}
			
		}
		if (js.get(index) != ']') {
			throw new ParseException("Inavalid JSON \n Missing ] for array at location "+index);
		}

		return index;
	}


	private int matchValue(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index) >='0' && js.get(index) <='9' )
			index = matchNumber(js,index);
		else if(js.get(index) == '-' || js.get(index) == '+') {
			index++;
			index = matchNumber(js,index);
		}
		else if(js.get(index) == '"')
			index = matchString(js,index);
		else if(js.get(index) =='{') {
			index++;
			index = matchObject(js,index);
			if ((index+1) >= js.size()) {
				throw new ParseException("Invalid JSON \n Missing symbol after } at location "+index);
			}
			else
				index++;
		}
		else if(js.get(index) == '[') {
			index++;
			index = matchArray(js,index);
			if ((index+1) >= js.size()) {
				throw new ParseException("Invalid JSON \n Missing symbol after ] at location "+index);
			}
			else
				index++;
		}
		else if(js.get(index) == 't')
			index = matchTrue(js,index);
		else if(js.get(index) == 'f')
			index = matchFalse(js,index);
		else if(js.get(index) == 'n')
			index = matchNull(js,index);
		else
			throw new ParseException("Invalid JSON \n Syntax Error at  "  + index);
		return index;
	}


	private int matchNull(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index) == 'f') {
			index++;
			if(js.get(index) == 'a') {
				index++;	
				if(js.get(index) == 'l') {
					index++;	
					if(js.get(index) == 's') {
						index++;	
						if(js.get(index) == 'e') {
							index++;	
						}
						else {
							throw new ParseException("fals not followed by e");
						}
					}
					else {
						throw new ParseException("fal not followed by s");
					}
				}
				else {
					throw new ParseException("fa not followed by l");
				}
			}
			else {
				throw new ParseException("f not followed by a");
			}
		}
		return index;
	}


	private int matchFalse(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index) == 'n') {
			index++;
			if(js.get(index) == 'u') {
				index++;	
				if(js.get(index) == 'l') {
					index++;	
					if(js.get(index) == 'l') {
						index++;	
					}
					else {
						throw new ParseException("nul not followed by l");
					}
				}
				else {
					throw new ParseException("nu not followed by l");
				}
			}
			else {
				throw new ParseException("n not followed by u");
			}
		}
		return index;
	}


	private int matchTrue(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index) == 't') {
			index++;
			if(js.get(index) == 'r') {
				index++;	
				if(js.get(index) == 'u') {
					index++;	
					if(js.get(index) == 'e') {
						index++;	
					}
					else {
						throw new ParseException("tru not followed by e");
					}
				}
				else {
					throw new ParseException("tr not followed by u");
				}
			}
			else {
				throw new ParseException("t not followed by r");
			}
		}
		return index;
	}


	private int matchString(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index) == '"')
			index++;
		while(js.get(index)>='!'&&js.get(index)<='~' ){ 
			if(js.get(index)=='\\'){ 
				index++;
				if(js.get(index)=='"'||js.get(index)=='\\'||js.get(index)=='/'||js.get(index)=='b'||js.get(index)=='f'||js.get(index)=='n'||js.get(index)=='r'||js.get(index)=='t'){
					index++;
					continue;
				}
				else{
					throw new ParseException("Invalid JSON \n invalid character at location "+index);
				}
			}
			if(js.get(index)=='"'){ 
				index++;
				break;
			}
			index++;if (index >= js.size()) {
				throw new ParseException("Invalid JSON \n Missing \" for String at location "+index);
			}
		}
		return index;

	}


	private int matchNumber(ArrayList<Character> js, int index) throws ParseException {
		if(js.get(index) >='0' && js.get(index) <='9' ) {
			while(js.get(index) >='0' && js.get(index) <='9' ) {
				index++;
		    }
		}
		if(js.get(index) == '.') {
			index++;
			if(js.get(index) >='0' && js.get(index) <='9' ) {
				while(js.get(index) >='0' && js.get(index) <='9' ) {
					index++;
			    }
			}
			else
				throw new ParseException("Invalid JSON \n Missing digit / digits after .  at location " + index);
		}
		if(js.get(index) == 'e' || js.get(index) == 'E') {
			index++;
			if (js.get(index) == '+' || js.get(index) == '-')
				index++;
			if(js.get(index) >='0' && js.get(index) <='9' ) {
				while(js.get(index) >='0' && js.get(index) <='9' ) {
					index++;
			    }
			}
			else
				throw new ParseException("Invalid JSON \n Missing digit / digits after e or E  at location " + index);
		}
		return index;
	}

}

