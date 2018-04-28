package 线程;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class 异常问题 {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Run1());
        t.start();
        Lock lock = new ReentrantLock();
        lock.lock();
        t.wait();
        lock.unlock();
    }

}

class Run1 implements Runnable {

    @Override
    public void run() {
        System.out.println("111");
    }

}