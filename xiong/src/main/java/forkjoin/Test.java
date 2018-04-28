package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
	public static void main(String[] args) throws Exception {

		// 创建随机数组成的数组:

		long[] array = new long[4000000];
		fillRandom(array);
		
		// fork/join task:
		ForkJoinPool fjp = new ForkJoinPool(4); // 最大并发数4
		ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
		long startTime = System.currentTimeMillis();
		Long result = fjp.invoke(task);
		long endTime = System.currentTimeMillis();

		System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
	}

	private static void fillRandom(long[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = ThreadLocalRandom.current().nextLong();
		}
	}
}
