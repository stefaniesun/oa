package xyz.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.xmlbeans.impl.util.Base64;

public class EncryptionUtil{
	
	/**
	 * @param inputText
	 * @return 32位字符串
	 */
	public static String md5(String inputText){
		return encrypt(inputText, "md5");
	}
	
	/**
	 * @param inputText
	 * @return 40位字符串
	 */
	public static String sha(String inputText){
		return encrypt(inputText, "sha-1");
	}

	private static String encrypt(String inputText, String algorithmName){
		if ((inputText == null) || ("".equals(inputText.trim()))) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}

		if ((algorithmName == null) || ("".equals(algorithmName.trim()))) {
			algorithmName = "md5";
		}

		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte[] s = m.digest();
			return hex(s);
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	private static String hex(byte[] arr){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i)
			sb.append(Integer.toHexString(arr[i] & 0xFF | 0x100).substring(1, 3));
		return sb.toString();
	}
	
	
	/**
	*	实际加密方法
	*
	*	@param source	待加密内容
	*	@param key	秘钥  
	*	@return	AES + BASE64
	*	@throws Exception 参数异常
	*/ 
	public static String doEncrypt(String source,String key)throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(),"AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec(new byte[16]);
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(source.getBytes("UTF-8"));
		// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		return new String(Base64.encode(encrypted));
	}
	
	/**
	*	实际解密方法
	*
	*	@param source	待解密内容
	*	@param key	秘钥
	*	@return	AES + BASE64
	 * @throws Exception 
	*	@throws Exception 参数异常
	*/ 
	public static String doDecrypt(String source,String key) throws Exception { 
		byte[] raw;
		raw = key.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(new byte[16]);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv); 
		byte[] encrypted1 = Base64.decode(source.getBytes("UTF-8"));// 先用base64解密
		byte[] original = cipher.doFinal(encrypted1); 
		String originalString = new String(original); 
		return originalString;
	}
	
	public static String getKeyFor16_firstFlag(String firstFlag){
		String returnStr = "";
		int lastLength = 16;
		if(!"".equals(firstFlag)){
			lastLength = 16-firstFlag.length();
		}
		returnStr= random(lastLength);
        return firstFlag+returnStr.toLowerCase();
	}
	
	public static String getKeyFor16(){
		return random(16).toLowerCase();
	}
	
	public static String random(int length){//传入的字符串的长度
        StringBuilder builder = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            
            int r = (int) (Math.random()*3);
            int rn1=(int)(48+Math.random()*10);
            int rn2=(int)(65+Math.random()*26);
            int rn3=(int)(97+Math.random()*26);
            
            switch(r){
            case 0:   
                builder.append((char)rn1);
                break;
            case 1:
                builder.append((char)rn2);
                break;
            case 2:
                builder.append((char)rn3);
                break;
            }
        }
        return builder.toString();
    }   
}