package 多线程;

public class Demo001内存可见性 {
	public static void main(String[] args) {
		System.out.println("===开始===");
		ThreadDemo demo = new ThreadDemo();
		new Thread(demo).start();
		while (true) {
			if (1 == demo.getFlag()) {
				System.out.println(System.nanoTime() + Thread.currentThread().getName() + ":flag is " + demo.getFlag());
				break;
			}
		}
		System.out.println(System.nanoTime() + "===结束===");
	}
}

// inner class
class ThreadDemo implements Runnable {

	// private volatile int flag = 0;
	private int flag = 0;

	@Override
	public void run() {
		try {
			Thread.sleep(100);// 故意延迟，防止此线程在 main 函数 if 判断之前结束
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		flag = 1;
		System.out.println(System.nanoTime() + Thread.currentThread().getName() + ":set flag to 1 ok");
	}

	public int getFlag() {
		return flag;
	}
}