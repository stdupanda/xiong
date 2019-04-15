package 哲学家进餐;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Philosopher implements Runnable {

    private String name;
    private Chopsticks left;
    private Chopsticks right;

    public Philosopher(String name, final Chopsticks left, final Chopsticks right) {
        this.name = name;
        this.left = left;
        this.right = right;

        if (-1 != left.getNum() || -1 != right.getNum()) {
            // 约定左边的是编号较小的，先加锁编号较小的筷子
            if (left.getNum() > right.getNum()) {
                this.left = right;
                this.right = left;
            }
        }
    }

    @Override
    public void run() {
        try {
            for (;;) {
                synchronized (left) {
                    System.out.println(name + " 拿起了 " + left);
                    synchronized (right) {
                        System.out.println(name + " 拿起了 " + right);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
