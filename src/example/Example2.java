package example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;			

public class Example2 {

	public static void main(String args[]) throws InterruptedException, ExecutionException {

		ExecutorService ex = Executors.newWorkStealingPool(9);

		// CompletableFuture with runAsync
		// runAsync method is used when we'd like to run background task asynchronously
		// which doesn't return any value
		CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
			System.out.println("Run from " + Thread.currentThread().getName());
		});

		// CompletableFuture with runAsync method and executor
		CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> {
			System.out.println("Run from " + Thread.currentThread().getName());
		}, ex);

		cf1.get();
		cf2.get();

		// CompletableFuture with supplyAsync
		// supplyAsync method is used when we'd like to run background task
		// asynchronously which return value
		CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
			return "Run cf3 from " + Thread.currentThread().getName();
		}, ex);

		CompletableFuture<String> cf4 = CompletableFuture.supplyAsync(() -> {
			return "Run cf4 from " + Thread.currentThread().getName();
		}, ex);

		System.out.println(cf3.get());
		System.out.println(cf4.get());

		// Attach callback that will be executed on completion using thenCompose
		CompletableFuture<String> result1 = cf3.thenCompose(i-> cf4);

		// Attach callback that will be executed on completion using thenCombineAsync
		CompletableFuture<String> result2 = cf3.thenCombineAsync(cf4, (a,b) ->{
			return a+" and "+b;
		},ex);
		
		// Attach callback that will be executed on completion using thenApplyAsync
		CompletableFuture<String> result3 = cf3.thenApplyAsync(i-> {
			try {
				return cf4.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return i;
		});
		
		
		System.out.println(result1.get());
		System.out.println(result2.get());
		System.out.println(result3.get());
	}

}
