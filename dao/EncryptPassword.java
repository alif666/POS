package dao;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class EncryptPassword {
	private String encrypt_pwd;

	public String encrypt(String pwd){
			MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
				md.update(pwd.getBytes());
				 byte[] bytes = md.digest();
		         //This bytes[] has bytes in decimal format;
		         //Convert it to hexadecimal format
		         StringBuilder sb = new StringBuilder();
		         for(int i=0; i< bytes.length ;i++)
		         {
		             sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		         }
		         //Get complete hashed password in hex format
		         encrypt_pwd = sb.toString();
			} catch (NoSuchAlgorithmException e) {
				
				e.printStackTrace();
			}			
	         return encrypt_pwd;
	         //System.out.println(generatedPassword);
		} 
}

