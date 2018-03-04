
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;

public class Example2 {

	public static void main (String args[]) throws InterruptedException, ExecutionException{
		
		ExecutorService ex = Executors.newWorkStealingPool();
		
		// CompletableFuture with runAsync
		// runAsync method is used when we'd like to run background process which doesn't return any value
		CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() ->{
			System.out.println("Run from "+Thread.currentThread().getName());
		});
		
		// CompletableFuture with runAsync method and executor
		CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() ->{
			System.out.println("Run from "+Thread.currentThread().getName());
		}, ex);
		
		cf1.get();
		cf2.get();
		
		// CompletableFuture with supplyAsync
		// supplyAsync method is used when we'd like to run background process which return value
		CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
			return "Run from "+Thread.currentThread().getName();
		});
		
		CompletableFuture<String> cf4 = CompletableFuture.supplyAsync(() ->{
			return "Run from "+Thread.currentThread().getName();
		},ex);
		
		System.out.println(cf3.get());
		System.out.println(cf4.get());
		
	}
	
}
