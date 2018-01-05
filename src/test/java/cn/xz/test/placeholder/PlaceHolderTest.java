package cn.xz.test.placeholder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import org.junit.Test;

public class PlaceHolderTest {

	@Test
	public void testParse() throws IOException {
		Properties props = new Properties();
		InputStream inStream = PlaceHolderTest.class.getResourceAsStream("/placeholder.properties");
		// props.load(inStream);
		props.load(new InputStreamReader(inStream, "utf-8"));
		String str1 = props.getProperty("push_title");
		System.out.println(str1);
		String str2 = props.getProperty("push_content");
		String ret = MessageFormat.format(str2, " 公交云卡", " 1000 ", "666");
		System.out.println(str2);
		System.out.println(ret);
	}
}
