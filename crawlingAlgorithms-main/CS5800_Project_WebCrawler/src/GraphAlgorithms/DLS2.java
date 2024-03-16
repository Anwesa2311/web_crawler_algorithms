package GraphAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DLS2 {
	
	 public static boolean crawlDLS(String seedURL, int maxLevel, String targetURL)
	    {
	        Stack<String> newStack = new Stack<>();
	        Map<String, Integer> urlSet = new HashMap<>();
	        Boolean isGoalNode = false;
	        int count = 0;
	       
	        //int depth = 0;
	        
	        newStack.add(seedURL);
	        urlSet.put(seedURL, 1);
	        
	       
	      
	        
	        System.out.println(".".repeat(80));
	        while(!newStack.isEmpty())
	        {
	        	
	         String currentURL = newStack.pop();
	        
	         System.out.println("Sites crawled: " +currentURL + ", Level = " + urlSet.get(currentURL));
	         count+= 1;
	         if(currentURL.equals(targetURL))
	         {
	             isGoalNode = true;
	             
	             break;
	         }
	         if(urlSet.get(currentURL)<=maxLevel)
	         {
	         String parsedURL = parseURL(currentURL);
	         String regex = "https://(\\w+\\.)*(\\w+)";
	         if(parsedURL!=null)
	         {
	         Pattern pattern= Pattern.compile(regex);
	         Matcher matcher = pattern.matcher(parsedURL);
	         while(matcher.find())
	         {
	             String link = matcher.group();
	             
	             if(!urlSet.containsKey(link))
	             {
	                 urlSet.put(link,urlSet.get(currentURL)+1);
//	                 System.out.println("Current site found to be crawled: " +link);
	                 newStack.add(link);
	             }
	         }
	         }
	         //depth++;
	        }
	        }
	        
	        System.out.println("Total number of sites crawled "+count);
	        System.out.println("\n");
	        
	        
	        displayMap(urlSet);
	        if(isGoalNode == true)
	        {
	        	int l = urlSet.get(targetURL)-1;
	        	System.out.println("The target url has been found in level " +l);
	        	System.out.println(".".repeat(80));
	        	return true;
	        }
	        else
	        {
	        	 System.out.println("Goal Node not found within depth limit");
	        	 System.out.println(".".repeat(80));
	        	 return false;
	        }
	        
	    }
	 
	  private static void displayMap(Map<String, Integer> urlSet) {
		  
		  System.out.println("Total number of sites added to be crawled:- " +urlSet.size());
		  int n = String.valueOf(urlSet.size()).length();
		    int i = 1;
		    System.out.println("Sites added to be crawled");
		    for (String value : urlSet.keySet()) {
		      int padding = n - String.valueOf(i).length();
		      String s = " ".repeat(padding) + i + ". " + value;
		      System.out.println(s);
		      i += 1;
		    }
	    
		  
	      
	        }
	        
	  
	 

	    
	  private static void displayDepth(int level, int maxLevel) {
		    int padding = String.valueOf(maxLevel).length() - String.valueOf(level).length();
		    String s = " ".repeat(padding) + level + " / " + maxLevel;
		    System.out.println("Web Crawler BFS Depth Level: " + s);
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


