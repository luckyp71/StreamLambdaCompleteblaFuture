import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.io.IOException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Collection;
import java.util.ArrayList;

public class Example3 {
	static ExecutorService ex = Executors.newWorkStealingPool(4);

	public static void main(String args[]) throws InterruptedException, ExecutionException, CancellationException {

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
				while (!i.isDone()) {
					c++;
					System.out.println("Processing is on progress at stage " + c);
					TimeUnit.MILLISECONDS.sleep(100);
					if (c == 200 && !i.isDone()) {
						System.out.println("timout");
						i.cancel(true);
					}
				}				
				String patternString = "(.{1}[a]{1}.+[f]{1}.{2})(\\w+.+html{1})";
				Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(i.get());
				
				while(matcher.find()) {
					System.out.println(matcher.group(2));
					System.out.println("is the thread done? "+i.isDone());	
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
	}

	public static CompletableFuture<String> getDoc(String url) throws InterruptedException {
		return CompletableFuture.supplyAsync(() -> {

			Document doc = null;

			try {
				System.out.println("Running task from thread " + Thread.currentThread().getName());
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Elements urls = doc.select("a[href]");
			return String.valueOf(urls);
		}, ex);

	}
}
