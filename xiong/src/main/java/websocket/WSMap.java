package websocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSMap {
	
	private static final Logger logger = LoggerFactory.getLogger(WSMap.class);
	
	/**
	 * 将 WebSocket 中的 session 存储到此 map 里
	 */
	private static Map<String, Session> WSClients = new ConcurrentHashMap<>();

	/**
	 * 在接收到远程客户端的 WebSocket 连接后，把其session存储到内部 map 中
	 * @param reqId 唯一请求Id
	 * @param session 对应 WebSocket session
	 */
	public static void put(String reqId, Session session) {
		if (!isAdded(reqId)) {
			WSClients.put(reqId, session);
		}
	}

	public static Session get(String reqId) {
		return WSClients.get(reqId);
	}

	public static void remove(String reqId) {
		WSClients.remove(reqId);
	}

	/**
	 * 消息发给所有已存储的远程客户端
	 */
	public static void sendMsgToAll(String message){
		Set<String> keySet = WSClients.keySet();
		for (String key : keySet) {
			Session session = WSClients.get(key);
			try {
				session.getBasicRemote().sendText(message);
				logger.debug("send message({}) to ({}) success!", key, message);
			} catch (IOException e) {
				logger.error("向用户：" + key + "发送消息异常：" + e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}
	/**
	 * 判断是否已存储此连接
	 * 
	 * @param reqId 客户端标识id
	 * @return
	 */
	public static boolean isAdded(String reqId) {
		return WSClients.containsKey(reqId);
	}
	
	/**
	 * 清空已经存储的所有 WebSocket 客户端信息
	 */
	public static void clearMap(){
		WSClients.clear();
	}
}
