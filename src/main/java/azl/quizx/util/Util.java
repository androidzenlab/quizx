package azl.quizx.util;


import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class Util {
	/*
	public static String getFileExtension(String fileName){
		if (fileName != null && fileName.indexOf(".") > -1) {
			int pos = fileName.indexOf(".");
			return fileName.substring(pos);
		} else {
			return "";
		}		
	}*/
	
	//use this to generate password for Spring security
	private static void passwordEncoder(String pswd){
		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		String encodedPswd = encoder.encode(pswd);
		System.out.println("pswd=" + encodedPswd);
	}

	public static void main(String[] args) throws Exception{
		passwordEncoder("monday99");
	}
}