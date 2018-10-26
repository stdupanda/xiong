package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
    private static final String filePath = "bus.properties";// 编译后位于 classpath 路径下

    /**
     *
     Util.class.getResource("").toString(); // 对应 file:/c:/xxx/WEB-INF/classes/com/site/db/
     Util.class.getResource("/").toString(); //对应 file:/c:/xxx/WEB-INF/classes
     Util.class.getClassLoader().getResource("").toString(); //对应 file:/c:/xxx/WEB-INF/classes
     Thread.currentThread().getContextClassLoader().getResource("").getPath(); //对应 /c:/xxx/WEB-INF/classes

     https://www.cnblogs.com/duoting/p/4141044.html

     */

    public static Properties getProperties() {
        Properties properties = new Properties();
        PropertiesUtil p = new PropertiesUtil();

        try (InputStream inputStream = p.getClass().getClassLoader().getResourceAsStream(filePath);
             InputStreamReader InputStreamReader = new InputStreamReader(inputStream, "utf-8")) {
            properties.load(InputStreamReader);
            return properties;
        } catch (IOException e) {
            LOGGER.error("从文件:{} 加载配置项失败", filePath);
            LOGGER.error("加载配置项失败", e);
            return null;
        }
    }

    public static String getValue(String key) {
        return getUtf8Value(key);
    }

    public static String getUtf8Value(String key) {
        Properties properties = new Properties();
        PropertiesUtil p = new PropertiesUtil();

        try (InputStream inputStream = p.getClass().getClassLoader().getResourceAsStream(filePath);
             InputStreamReader InputStreamReader = new InputStreamReader(inputStream, "utf-8")) {
            properties.load(InputStreamReader);
            return properties.getProperty(key, "");
        } catch (IOException e) {
            LOGGER.error("从文件:{} 读取配置项:{}", filePath, key);
            LOGGER.error("读取配置项失败", e);
            return "";
        }
    }

    public static int getValueInt(String key) {
        try {
            return Integer.valueOf(getUtf8Value(key));
        } catch (Exception e) {
            LOGGER.error("从文件:{} 读取配置项:{}", filePath, key);
            LOGGER.error("读取配置项失败", e);
            return -1;
        }
    }

    public static boolean setValue(String key, String value) {
        Properties properties = new Properties();

        String path = PropertiesUtil.class.getResource("/").toString().replace("file:/", "");
        String tmp = path + filePath;
        LOGGER.debug(path);
        LOGGER.debug(tmp);
        try (InputStream inputStream = new FileInputStream(tmp);
        	InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");) {
            properties.load(reader);
            properties.setProperty(key, value);
            // new FileOutputStream(resourceFile)；运行时会马上覆盖掉原有的内容
            FileOutputStream fileOutputStream = new FileOutputStream(tmp);// 所以必须先 读取、更新 配置信息，再执行此语句；
       		OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
            properties.store(writer, "mod properties.");
            return true;
        } catch (Exception e) {
            LOGGER.error("从文件:{} 更新配置项:{}", filePath, key);
            LOGGER.error("更新配置项失败", e);
            return false;
        }
    }
}
