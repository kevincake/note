package com.lingchat.imlib.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**    
 *     
 * 项目名称：tianmazu-utils
 * 类名称：MD5Utils.java    
 * 类描述：
 * 创建人：nengbing.zhou
 * 创建时间：2014年12月4日上午12:48:16   
 */
public class MD5Utils {
	
	/**
	 * MD5加密算法实现
	 * 
	 * @param str  明文
	 * @return 密文
	 * @throws Exception
	 */
	public static String getMD5(String str) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = str.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char ch[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密s
				ch[k++] = hexDigits[b >> 4 & 0xf];
				ch[k++] = hexDigits[b & 0xf];
			}
			return new String(ch);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}
	
}
