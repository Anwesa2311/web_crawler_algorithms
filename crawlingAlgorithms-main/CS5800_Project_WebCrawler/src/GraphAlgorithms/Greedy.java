package GraphAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import GraphAlgorithms.Heuristic;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.*;

public class Greedy {
	
	public static Map<String, Long> loadTimeHeuristic = new HashMap<>();
	
	private void calculateHeuristic(String seedURL, int maxLevel)
	{
		 Queue<String> primaryQueue = new LinkedList<>();
		    //HashSet<String> visitedUrls = new HashSet<>();
		    
		    primaryQueue.add(seedURL);
		    long time = 0;
		    loadTimeHeuristic.put(seedURL,time);
		    int level = 0;

		    while (level < maxLevel) {
		      // Create an intermediate queue to store parsed URLs.
		      Queue<String> intermediateQueue = new LinkedList<>();
		      while (!primaryQueue.isEmpty()) {
		        String currentURL = primaryQueue.poll();
//		        System.out.println("Current site found to be crawled: " + currentURL);
		        String parsedURL = parseURL(currentURL);
		        //String regex = "https://(\\w+\\.)*(\\w+)";
		        String regex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
		        Pattern pattern = Pattern.compile(regex);
		        Matcher matcher = pattern.matcher(parsedURL);
		        while (matcher.find()) {
		          String link = matcher.group();
		          try
		          {
		          if (!loadTimeHeuristic.containsKey(link)) {
//		        	  driver.get(link);
		        	  
		        	  HttpURLConnection connection = null;
		        	  URL url = new URL(link);
		        	  long start = System.currentTimeMillis();
		              connection = (HttpURLConnection) url.openConnection();
		              connection.getInputStream();
		              long finish = System.currentTimeMillis();
		        	  long totalTime = finish - start; 
		        	  loadTimeHeuristic.put(link,totalTime);
		        	  intermediateQueue.add(link);

		          
		        }

		          } catch (IOException e) {
		              // TODO: REMOVE. Suppressed for debugging purposes.
		            }catch (Exception e) {
		                e.printStackTrace();
		            }
		          }
		      }
		      primaryQueue = intermediateQueue;
		      level += 1;
		      }
		      
		      System.out.println(loadTimeHeuristic);
		    }
	
	public void bestFirstSearch(String URL, int maxLevel, String targetURL)
	{
	//Map<String, Long> loadTimeHeuristic = new HashMap<>();
	//loadTimeHeuristic = calculateHeuristic(URL, loadTimeHeuristic);
	System.out.println("Calculating heuristic");
	Boolean isGoalNode = false;
	long startTime = System.currentTimeMillis();
    calculateHeuristic(URL,maxLevel);
    long endTime = System.currentTimeMillis();
    System.out.println("heuristic calculation completed in "+ (endTime - startTime) + " ms"); 
	Queue<Heuristic> primaryQueue = new PriorityQueue<Heuristic>(1,new Comparator<>() {public int compare(Heuristic h1, Heuristic h2) {
		return Double.compare(h1.loadingTime, h2.loadingTime);}});
	
	//long seedURLTime = loadTimeHeuristic.get(URL);
	Heuristic obj1 = new Heuristic(URL,0);
	HashSet<String> visitedUrls = new HashSet<>();
	primaryQueue.add(obj1);
    visitedUrls.add(URL);
    int level = 0;
    
    while (level < maxLevel) {
        // Create an intermediate queue to store parsed URLs.
    	Queue<Heuristic> intermediateQueue = new PriorityQueue<Heuristic>(1,new Comparator<>() {public int compare(Heuristic h1, Heuristic h2) {
    		return Double.compare(h1.loadingTime, h2.loadingTime);}});
        while (!primaryQueue.isEmpty()) {
          String currentURL = primaryQueue.poll().link;
          if(currentURL.equals(targetURL))
          {
              isGoalNode = true;
              
              break;
          }
       System.out.println("Current site found to be crawled: " + currentURL);
          String parsedURL = parseURL(currentURL);
          String regex = "https://(\\w+\\.)*(\\w+)";

          Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher(parsedURL);
          while (matcher.find()) {
            String link = matcher.group();

            if (!visitedUrls.contains(link)) {
//                System.out.println("Current site found to be crawled: " + link);
           //System.out.println(link);
           long loadingTime = loadTimeHeuristic.getOrDefault(link,(long) 650);
           Heuristic obj2 = new Heuristic(link,loadingTime);
            intermediateQueue.add(obj2);
            visitedUrls.add(link);
            }
          }
        }
        if(isGoalNode == true)
        {
            
            
            break;
        }
        primaryQueue = intermediateQueue;
        level += 1;
        System.out.println(".".repeat(80));
        displayDepth(level, maxLevel);
        System.out.println("Queued Urls #: " + primaryQueue.size());
        //displayQueue(primaryQueue);
      }
    System.out.println(".".repeat(80));
    System.out.println("Sites Crawled #: " + visitedUrls.size());
    displaySet(visitedUrls);
    if(isGoalNode == true)
    {
    	int l = level+1;
    	System.out.println("The target url has been found in level " +l);
    	System.out.println(".".repeat(80));
    	return;
    	
    }
    else
    {
    	 System.out.println("Goal Node not found within depth limit");
    	 System.out.println(".".repeat(80));
    	 return;
    	 
    }
    
    
    


}
	private static void displayDepth(int level, int maxLevel) {
	    int padding = String.valueOf(maxLevel).length() - String.valueOf(level).length();
	    String s = " ".repeat(padding) + level + " / " + maxLevel;
	    System.out.println("Web Crawler BFS Depth Level: " + s);
	  }

	  private static void displaySet(HashSet<String> urlSet) {
	    int n = String.valueOf(urlSet.size()).length();
	    int i = 1;
	    for (String value : urlSet) {
	      int padding = n - String.valueOf(i).length();
	      String s = " ".repeat(padding) + i + ". " + value;
	      System.out.println(s);
	      i += 1;
	    }
	  }
	
	
	
	private static String parseURL(String currentURL) {
	    StringBuilder stringBuilder = new StringBuilder();
	    try {
	      URL url = new URL(currentURL);
	      BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
	      String urls = "";
	      while ((urls = input.readLine()) != null) {
	        stringBuilder.append(urls);
	      }
	      input.close();
	    } catch (IOException e) {
	      // TODO: REMOVE. Suppressed for debugging purposes.
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return stringBuilder.toString();
	  }
	
	

  }


