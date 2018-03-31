package example;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Collection;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.net.URL;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.MalformedURLException;

public class Example6 {

	private static ExecutorService ex = Executors.newFixedThreadPool(4);

	public static void main (String args[]) {
		List<String> urls = Arrays.asList("https://www.oreilly.com","https://www.google.com","https://www.facebook.com",
											"https://www.packtpub.com/");
		getDocs(urls).parallelStream().forEach(i -> {		
			try {
				int c = 0;
				while(!i.isDone()) {
					c++;
					System.out.println("Stage "+c);
					TimeUnit.SECONDS.sleep(2);
					// Set timeout = 20 seconds
					if(c == 10 && !i.isDone()) {
						i.cancel(true);
						ex.shutdown();
						System.out.println("Timeout");
					}
				}
				
			String title = i.get().title();
			Elements links = i.get().select("a[href]");
		
			// Display the web title
			System.out.println(title);
			
			// Display all listed links of each web as well as its IP
			links.stream().forEach(link -> {
					try {					
						String l = link.attr("abs:href");
						URL url = new URL(l);
						InetAddress ip = InetAddress.getByName(url.getHost());
						System.out.println(l+" IP: "+ip.getHostAddress());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
			});
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			catch(ExecutionException e) {
				e.printStackTrace();
			}
		});
		ex.shutdown();
	}

	public static Collection<CompletableFuture<Document>>getDocs(List<String> urls){
			Collection <CompletableFuture<Document>> getDocs = new ArrayList<>();
			urls.parallelStream().forEach(i ->{
				getDocs.add(getDoc(i));
			});
			return getDocs;
	}
		
	public static CompletableFuture<Document> getDoc(String url){
		return CompletableFuture.supplyAsync(()->{
			Document doc = null;
			try {
				doc = Jsoup.connect(url).get();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			return doc;
		},ex);
	}
}
