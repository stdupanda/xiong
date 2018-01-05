
/**
 * aaa
 * @author gsx
 *
 */
public class zzzzzz {

	public static void main(String[] args) {
		final Demo demo = new Demo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				demo.a();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				demo.b();
			}
		}).start();
	}
	
}

class Demo {
	Integer num = 1;
	public synchronized void a() {
		try {
			System.out.println("a 方法开始等待一秒");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("a");
	}
	
	public synchronized void b() {
//		synchronized(Demo.class.getClass()) {
			System.out.println("b");
//		}
	}
}