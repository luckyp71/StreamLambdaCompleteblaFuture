package example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.Marketing;
import java.util.Map;
import java.util.HashMap;

public class Example5 {
	
	private static List<Marketing> marketingEmployees = new ArrayList<>();

	static {
		IntStream.range(1, 21).forEach(i -> {
			marketingEmployees.add(new Marketing("Employee " +i, 22 + i, 1000 + i, 100 + i, "Address " + i));
		});
	}
	
	public static void main(String args[]) {
		
		Map<String, Long> result = marketingEmployees.stream().map(i -> i.getAddress().replaceAll("[2-9]", "")).
				collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		System.out.println(result);
		
		
//		List<String> list1 = marketingEmployees.parallelStream().map(Marketing::getName).collect(Collectors.toList());
//		list1.parallelStream().forEach(System.out::println);
	}
}
