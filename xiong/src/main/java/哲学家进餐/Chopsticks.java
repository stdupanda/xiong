package 哲学家进餐;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Chopsticks {
    private String name;
    private int num = -1;

    public Chopsticks(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "_" + num;
    }
}
