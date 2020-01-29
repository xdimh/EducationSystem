package org.qbec.action;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.qbec.interceptor.HibernateUtil;

public class Md5EncryptionUtil {

	private static final Logger LOG = Logger.getLogger(Md5EncryptionUtil.class);
	
	public static String encryptionPassword(String pass) 
	{
		StringBuffer passAfterEncryption = new StringBuffer();
		MessageDigest messageDigest = null;  
		Log.info("开始加密");
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(pass.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			Log.error("没有这个算法");
		}catch (UnsupportedEncodingException e) {
			Log.error("对密码编码错误");
		}
		byte[] passDigestByteArray = messageDigest.digest();
	
		for(int i = 0; i < passDigestByteArray.length; i++)
		{
		 	if (Integer.toHexString(0xFF & passDigestByteArray[i]).length() == 1)  
		 		passAfterEncryption.append("0").append(Integer.toHexString(0xFF & passDigestByteArray[i]));  
            else  
            	passAfterEncryption.append(Integer.toHexString(0xFF & passDigestByteArray[i]));  
		}
		
		return passAfterEncryption.toString();
	}
	
}
