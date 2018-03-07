package 线程;

public class 测试join {
	public static void main(String[] args) {
		Thread t = new Thread(new R1());
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("ok");
	}
}

class R1 implements Runnable{

	@Override
	public void run() {
		System.out.println("t1");
		try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
}