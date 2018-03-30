package example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.Marketing;

public class Example4 {

	private static List<Marketing> marketingEmployees = new ArrayList<>();

	static {
		IntStream.range(1, 21).forEach(i -> {
			marketingEmployees.add(new Marketing("Employee " +i, 22 + i, 1000 + i, 100 + i, "Address " + i));
		});
	}
	
	public static void main (String args[]) throws Exception {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		// Get Employee Name
		CompletableFuture<String> name = CompletableFuture.supplyAsync(()->{
			// Enter employee name with Employee 1 - 20
			System.out.print("Enter employee name: ");
			String inputName = "";
			try {
				inputName = input.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			final String eName = inputName;
			String mName = "";
			for(Marketing e: marketingEmployees) {
				if(e.getName().equalsIgnoreCase(eName)) {
					mName = e.getName();
					break;
				}
				else {
					mName = eName+ " is not an employee here";
				}
			}	
			return mName;
		});
		
		// Get employee address based on the result of name future
		CompletableFuture<String> address = CompletableFuture.supplyAsync(()->{
			String eAddress = "";
			for(Marketing e: marketingEmployees) {
				try {
					if(e.getName().equalsIgnoreCase(name.get())) {
						eAddress = e.getAddress();
						break;
					} else {
						eAddress = "Employee identity not found";
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			}
			return eAddress;
		});
		
		// Get employee salary based on the result of address future
		CompletableFuture<Integer> salary = CompletableFuture.supplyAsync(()->{
			Integer eSalary = null;
			for(Marketing e: marketingEmployees) {
				try {
					if(e.getAddress().equalsIgnoreCase(address.get())) {
						eSalary = e.getSalary();
						break;
					} else {
						eSalary = null;
					}
				}
				catch(InterruptedException ie) {
					ie.printStackTrace();
				}
				catch(ExecutionException ee) {
					ee.printStackTrace();
				}
			}
			return eSalary;
		});
		
		// Example of using thenApplyAsync
		CompletableFuture<? extends Object> result = name.thenApplyAsync(i -> {
			try {
				return address.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return i;
		}).thenApplyAsync(i ->{
			try {
				return salary.get();
			}
			catch(InterruptedException ie) {
				ie.printStackTrace();
			}
			catch(ExecutionException ee) {
				ee.printStackTrace();
			}
			return i;
		});
		
		// Example of using thenCombineAsync
		CompletableFuture<String> result2 = name.thenCombineAsync(address, (a,b) ->{
			return "Name: "+a+"\nAddress: "+b;
		}).thenCombineAsync(salary, (a, b)-> {
			return a+"\nSalary: "+b;	
		});
		
		System.out.println("\nResult of using thenApplyAsync: "+result.get());
		System.out.println("\nResult of using thenCombineAsync: \n"+result2.get());
		
		System.out.println();
		

	}
}
