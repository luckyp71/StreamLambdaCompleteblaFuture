
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.Arrays;
import java.util.Collection;

public class Example1 {

	public static void main(String args[]) {

		// For loop example with LongStream
		System.out.println("For loop example with LongStream");
		LongStream.range(1, 6).forEach(i -> System.out.println(i));

		// For loop example with IntStream
		System.out.println("For loop example with IntStream");
		IntStream.range(1, 6).forEach(i -> System.out.println(i));

		// Data processing using stream and lambda
		// Creating collection
		Collection<String> col = Arrays.asList("a", "b", "c", "z", "G", "T", "K");

		// Display all collection using stream and lambda
		System.out.println("Display all elements of collection without any filter");
		col.stream().forEach(i -> System.out.println(i));

		// Display and sort ascending the elements of collection with stream and method reference
		System.out.println("Display and sort all elements of collection with stream and method reference");
		col.stream().sorted((a,b) -> a.compareToIgnoreCase(b)).forEach(System.out::println);
		
		// Display, filter, and sort ascending the element of collection with "a" and "b" as parameters
		System.out.println("Display, filter, and sort the element of collection with \"a\" and \"b\" as parameters");
		col.stream().filter(i -> i.equalsIgnoreCase("a") || i.equalsIgnoreCase("b")
				).sorted((a,b) -> a.compareToIgnoreCase(b)).forEach(System.out::println);
		
		// Display, filter, and sort descending the element of collection with "a" and "b" as parameters
		System.out.println("Display, filter, and sort the element of collection with \"a\" and \"b\" as parameters");
		col.stream().filter(i -> i.equalsIgnoreCase("a") || i.equalsIgnoreCase("b")
				).sorted((a,b) -> b.compareToIgnoreCase(a)).forEach(System.out::println);
	}

}
