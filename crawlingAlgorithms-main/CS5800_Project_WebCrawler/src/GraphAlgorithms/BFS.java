package GraphAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BFS {
  public static void crawlBFS(String seedURL, int maxLevel, String targetURL) {
    Queue<String> primaryQueue = new LinkedList<>();
    HashSet<String> visitedUrls = new HashSet<>();
    Boolean isGoalNode = false;
    primaryQueue.add(seedURL);
    visitedUrls.add(seedURL);
    int level = 0;

    System.out.println(".".repeat(80));
    displayDepth(level, maxLevel);
    System.out.println("Queued Urls #: " + primaryQueue.size());
    displayQueue(primaryQueue);

    while (level < maxLevel) {
      // Create an intermediate queue to store parsed URLs.
      Queue<String> intermediateQueue = new LinkedList<>();
      while (!primaryQueue.isEmpty()) {
        String currentURL = primaryQueue.poll();
        if(currentURL.equals(targetURL))
        {
            isGoalNode = true;
            
            break;
        }
        int l = level +1;
        System.out.println("Current site found to be crawled: " + currentURL + " level = " + l);
        String parsedURL = parseURL(currentURL);
        String regex = "https://(\\w+\\.)*(\\w+)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parsedURL);
        while (matcher.find()) {
          String link = matcher.group();

          if (!visitedUrls.contains(link)) {
//              System.out.println("Current site found to be crawled: " + link);
            intermediateQueue.add(link);
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
      displayQueue(primaryQueue);
    }
    System.out.println(".".repeat(80));
    System.out.println("Sites Crawled #: " + visitedUrls.size());
    displaySet(visitedUrls);
    if(isGoalNode == true)
    {
    	int l = level +1;
    	System.out.println("The target url has been found in level "+l);
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

  private static void displayQueue(Queue<String> queue) {
    int n = String.valueOf(queue.size()).length();
    int i = 1;
    for (String value : queue) {
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
