package http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpUtil {

	private static Logger logger = Logger.getLogger(HttpUtil.class);
	
	/**
	 * 
	 * @param reqURL 请求地址
	 * @param data 请求参数应该是 name1=value1&name2=value2 的形式
	 * @param contentType 
	 * @return
	 */
	public static String sendPost(String reqURL, String data, String contentType) {
		logger.info("Request url : " + reqURL);
		logger.debug("Request data : " + data);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost method = new HttpPost(reqURL);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(90000).setConnectTimeout(30000).build();// 设置请求和传输超时时间
		method.setConfig(requestConfig);
		StringEntity entity = new StringEntity(data, "UTF-8");//
		entity.setContentEncoding("UTF-8");
		entity.setContentType(contentType);
		method.setEntity(entity);
		try {
			HttpResponse resp = httpClient.execute(method);
			logger.info("Send request to service !");
			HttpEntity HttpResp = resp.getEntity();
			String respData = EntityUtils.toString(HttpResp);
			logger.info("Get resp date : " + respData);
			return respData;
		} catch (Exception e) {
			logger.error("Send error! The message :" + e.getMessage());
			logger.error("请求数据 ：" + data);
			return null;
		} finally {
			method.releaseConnection();
		}
	}
	
	/**
	 * 
	 * @param reqURL 请求参数应该是 name1=value1&name2=value2 的形式
	 * @return
	 */
	public static String sendGet(String reqURL) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(reqURL);
			HttpResponse resp= httpClient.execute(httpGet);
			String respData = EntityUtils.toString(resp.getEntity());
			logger.info("Get resp date : " + respData);
			return respData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JsonObject sendHttps(String reqURL, String data, boolean ifVerifySslCert) {
		logger.debug("reqUrl : " + reqURL);
		logger.debug("data : " + data);
		JsonObject jsonObject;
		CloseableHttpClient httpClient;
		if (StringUtils.isNotBlank(reqURL) && reqURL.startsWith("https")) {
			logger.debug("connecting https URL.");
			logger.debug("read config if_verify_ssl_cert:" + ifVerifySslCert);
			if (!ifVerifySslCert) {
				// 不验证 ssl 证书
				// 没有服务器证书，采用自定义 信任机制
				try {
					SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
						@Override
						public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
							return true;// 信任所有
						}
					}).build();
					SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext,
							new HostnameVerifier() {
						@Override
						public boolean verify(String s, SSLSession sslSession) {
							return true;
						}
					});
					httpClient = HttpClients.custom().setSSLSocketFactory(factory).build();
				} catch (Exception e) {
					logger.error("ssl config error!", e);
					httpClient = HttpClients.createDefault();
				}
			} else {
				logger.debug("需要验证 ssl 证书.");
				logger.debug("暂未实现加载自定义 ssl 证书.");
				httpClient = HttpClients.createDefault();
			}
		} else {
			httpClient = HttpClients.createDefault();
		}
		HttpPost method = new HttpPost(reqURL);

		StringEntity entity = new StringEntity(data, "utf-8");//
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		method.setEntity(entity);

		try {
			HttpResponse resp = httpClient.execute(method);
			logger.info("sendHttp -- Recv Protocal !");
			HttpEntity HttpResp = resp.getEntity();
			String charset = EntityUtils.toString(HttpResp, "utf-8");
			logger.debug("received result:" + charset);
			jsonObject = new JsonParser().parse(charset).getAsJsonObject();
		} catch (Exception e) {
			logger.error("sendHttp -- Send error !,The message :"
					+ e.getMessage());
			return null;
		} finally {
			method.releaseConnection();
		}
		return jsonObject;
	}
}
