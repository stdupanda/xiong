package base64;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Comms {
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] encodeBase64 = Base64.encodeBase64("哈哈啊啊啊".getBytes("utf-8"));
		System.out.println(new String(encodeBase64));
	}
}
