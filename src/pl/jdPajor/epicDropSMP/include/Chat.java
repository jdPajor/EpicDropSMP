package pl.jdPajor.epicDropSMP.include;

import java.util.regex.Pattern;

public class Chat {
	
	public static boolean isAlphaNumeric( String s) {
        return s.matches("^[a-zA-Z0-9_]*$");
    }
	
	public static boolean isInteger( String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
	}

}
