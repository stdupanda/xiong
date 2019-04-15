package 哲学家进餐;

public class 进餐测试 {
    public static void main(String[] args) {
        Chopsticks c1 = new Chopsticks("c1");
        Chopsticks c2 = new Chopsticks("c2");
        Chopsticks c3 = new Chopsticks("c3");
        Chopsticks c4 = new Chopsticks("c4");
        Chopsticks c5 = new Chopsticks("c5");
        Philosopher p1 = new Philosopher("p1", c1, c2);//会死锁
//        Philosopher p1 = new Philosopher("p1", c1, c2);//不会死锁
        Philosopher p2 = new Philosopher("p1", c2, c3);
        Philosopher p3 = new Philosopher("p1", c3, c4);
        Philosopher p4 = new Philosopher("p1", c4, c5);
        Philosopher p5 = new Philosopher("p1", c5, c1);
        new Thread(p1).start();
        new Thread(p2).start();
        new Thread(p3).start();
        new Thread(p4).start();
        new Thread(p5).start();
    }
}
