package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerSocketChannelDemo {
	private static final int PORT = 8080;
	private static final int TIMEOUT = 3000;
	private static final int BUFFER_SIZE = 512;

	public static void main(String[] args) throws IOException, InterruptedException {
		Selector selector = null;
		ServerSocketChannel ssc = null;
		selector = Selector.open();
		ssc = ServerSocketChannel.open();
		ssc.bind(new InetSocketAddress(PORT));
		ssc.configureBlocking(false);
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		while (true) {
			if (0 >= selector.select(TIMEOUT)) {
//			if (0 >= selector.select()) {
				System.out.print("=");
				continue;
			}
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = (SelectionKey) iterator.next();
				if (selectionKey.isAcceptable()) {
					handleAccept(selectionKey);
				}
				if (selectionKey.isReadable()) {
					handleRead(selectionKey);
				}
				if (selectionKey.isWritable() && selectionKey.isValid()) {
					handleWrite(selectionKey);
				}
				if (selectionKey.isConnectable()) {
					System.out.println("selectionKey is Connectable");
				}
			}
			// TimeUnit.SECONDS.sleep(1);
			System.out.println(".");
		}
	}

	private static void handleAccept(SelectionKey selectionKey) throws IOException {
		ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
		SocketChannel sc = ssc.accept();
		if (null != sc) {
			sc.configureBlocking(false);
			sc.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUFFER_SIZE));
		}else {
			System.out.println("handleAccept:  ssc.accept() 返回空值！");
		}
	}

	private static void handleRead(SelectionKey selectionKey) throws IOException {
		SocketChannel sc = (SocketChannel) selectionKey.channel();
		ByteBuffer buf = (ByteBuffer) selectionKey.attachment();
		int read = 0;
		while (0 < (read = sc.read(buf))) {
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.println(buf.get());
			}
			buf.clear();
		}
		if (-1 == read) {
			sc.close();
		}
	}
	
	private static void handleWrite(SelectionKey selectionKey) throws IOException {
		ByteBuffer buf = (ByteBuffer) selectionKey.attachment();
		buf.flip();
		SocketChannel sc = (SocketChannel) selectionKey.channel();
		while (buf.hasRemaining()) {
			sc.write(buf);
		}
		buf.compact();
	}
}
