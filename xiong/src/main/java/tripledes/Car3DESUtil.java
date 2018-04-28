package tripledes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Car3DESUtil {
	
	private static final Log log = LogFactory.getLog(Car3DESUtil.class);
	
	private static final byte[] Key = "GZJR!!@#-1".substring(0, 8).getBytes();
	private static final String Algorithm = "DES"; // 定义 加密算法,可用
													// DES,DESede,Blowfish

	// 加密字符串
	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 解密字符串
	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
	
	public static String genCarToken(String userName, String mobileTel) throws UnsupportedEncodingException{
		// 添加新安全算法,如果用JCE就要把它添加进去
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		final byte[] keyBytes = Key; // 8字节的密钥
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String szSrc = "15308511538$王家亮$" + sdf.format(new Date());
		String srcStr = mobileTel + "$" + userName + "$" + sdf.format(new Date());
		log.debug("before encrypt: " + srcStr);
		byte[] encodedByteArr = encryptMode(keyBytes, srcStr.getBytes());
		String encodedStr = new String(encodedByteArr);
		log.debug("after encrypt:  " + encodedStr);

		Base64 base64 = new Base64();
		byte[] base64edByteArr = base64.encode(encodedByteArr);
		String base64edStr = new String(base64edByteArr);
		log.debug("after base64:   " + base64edStr);
		String retStr = URLEncoder.encode(base64edStr, "UTF-8");
		log.debug("after URLEncode:" + retStr);
		
		byte[] srcBytes = decryptMode(keyBytes, encodedByteArr);
		log.debug("after decode:   " + (new String(srcBytes)));
		
		return retStr;
	}

	public static void main(String[] args) throws UnsupportedEncodingException { // 添加新安全算法,如果用JCE就要把它添加进去
		String genCarToken = genCarToken("王家亮", "15308511538");
		System.out.println(genCarToken);
	}
}
