package WebCrawler;

import GraphAlgorithms.BFS;
import GraphAlgorithms.DFS;
import GraphAlgorithms.DLS;
import GraphAlgorithms.DLS2;
import GraphAlgorithms.Greedy;
import GraphAlgorithms.IDS;

public class WebCrawlerImpl {
	
	public static void main(String[] args)
	{
	BFS b = new BFS(); 
	long startTime = System.currentTimeMillis();
		
	BFS.crawlBFS("https://basuanw.sites.northeastern.edu/", 2, "https://fonts.googleapis.com");
	long endTime = System.currentTimeMillis();
	System.out.println("Time taken in BFS= " + ((endTime - startTime))+ "ms");
		
//		DFS d = new DFS(); 
//	long startTime = System.currentTimeMillis();
////			
//	DFS.crawlDFS("https://basuanw.sites.northeastern.edu/", 4, "https://api.github.com");
//	long endTime = System.currentTimeMillis();
//	System.out.println("Time taken in DFS= " + ((endTime - startTime))+ "ms");
//			
		
//		IDS i = new IDS(); 
//      long startTime = System.currentTimeMillis();
//		IDS.crawlIDS("https://basuanw.sites.northeastern.edu/", 4, "https://api.github.com");
//		long endTime = System.currentTimeMillis();
//		System.out.println("Time taken in IDS= " + ((endTime - startTime))+ "ms");
		
//		long startTime = System.currentTimeMillis();
//	DLS2.crawlDLS("https://basuanw.sites.northeastern.edu/", 4, "https://api.github.com");
//	long endTime = System.currentTimeMillis();
//	System.out.println("Time taken in DLS= " + ((endTime - startTime))+ "ms");
//		
//		Greedy g = new Greedy();
//		long startTime = System.currentTimeMillis();
//		g.bestFirstSearch("https://basuanw.sites.northeastern.edu/", 4
//				, "https://api.github.com");
//		long endTime = System.currentTimeMillis();
//		System.out.println("Time taken in Greedy= " + (endTime - startTime)+ "ms");

	}
}