package hashcode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class HashCodeEqualsTest {

	@Test
	public void hashCodeEqualsTest() {
		System.out.println("equals true, then hashCode the same.");
		String str1 = "1";
		String str2 = "1";
		String strObj = new String("1");
		System.out.println(str1.equals(str2));
		System.out.println(str1.equals(strObj));
		System.out.println(str1.hashCode() + " <> " + str2.hashCode() + " <> " + strObj.hashCode());

		System.out.println(str1 == str2);
		System.out.println(str1 == strObj);

		String data = new String("123");
		System.out.println(data.hashCode());
		data.replace("1", "2");
	}

	@Test
	public void showServerSocket() {
		try {
			ServerSocket serverSocket = new ServerSocket(6666);
			Socket socket = serverSocket.accept();// accept 是阻塞方法，The method
													// blocks until a connection
													// is made.收到请求后程序才会继续进行
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String data = bufferedReader.readLine();
			System.out.println("get data:" + data);
			socket.getOutputStream();
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write("hello," + data);
			bufferedWriter.close();
			bufferedReader.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void asassa() {
		try {
			Socket socket = new Socket("127.0.0.1", 5200);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter write = new PrintWriter(socket.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String readline;
			readline = br.readLine();// 从系统标准输入读入一字符串
			while (!readline.equals("end")) {
				write.println(readline);
				write.flush();
				System.out.println("Client:" + readline);
				System.out.println("Server:" + in.readLine());
				readline = br.readLine(); // 从系统标准输入读入一字符串
			} // 继续循环
			write.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("can not listen to:" + e);// 出错，打印出错信息
		}
	}
}
