package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestCollection {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> synchronizedMap = Collections.synchronizedMap(map);
		System.out.println(synchronizedMap);
		List<Integer> list = new ArrayList<>();
		Collections.sort(list);
		Set<Integer> set = new HashSet<>();
		set.add(1);
		set.add(1);
		System.out.println(set.size());
	}
}
