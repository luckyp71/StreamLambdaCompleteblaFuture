import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.io.IOException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

import java.util.Collection;
import java.util.ArrayList;


public class Example3 {
	static ExecutorService ex = Executors.newWorkStealingPool(4);
	
	public static void main (String args[]) throws InterruptedException, ExecutionException, CancellationException {
		
		Collection<CompletableFuture<String>> docs = new ArrayList<>();
		
		LongStream.range(1, 5).forEach(i -> {
			
		try {
			docs.add(getDoc("http://www.oracle.com/technetwork/java/index.html"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	});
		
		docs.parallelStream().forEach(i -> {
			
			try {
				int c = 0;
				while(!i.isDone()) {
					c++;
					System.out.println("Processing under progress on stage "+c);
					TimeUnit.MILLISECONDS.sleep(100);
					if(c==200 && !i.isDone()) {
						System.out.println("timout");
						i.cancel(true);
					}
				}
				
				System.out.println(i.get());
				System.out.println(i.isDone());
				ex.shutdown();
			}
			catch(InterruptedException ie) {
				ie.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
		

	}
	
	public static CompletableFuture<String> getDoc(String url) throws InterruptedException{
		return CompletableFuture.supplyAsync(() ->{
			
			Document doc = null;
			
			try {
				System.out.println("Running task from thread "+Thread.currentThread().getName());
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			return String.valueOf(doc);
		},ex);
		
	}
}
	
	

