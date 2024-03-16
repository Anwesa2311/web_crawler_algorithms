package GraphAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DFS {
  public static void crawlDFS(String seedURL, int maxLevel, String targetURL) {
    List<Stack<String>> stacks = new ArrayList<>();
    List<HashSet<String>> visitedUrls = new ArrayList<>();
    Boolean isGoalNode = false;

    // Initialize stacks.
    Stack<String> _st = new Stack<>();
    _st.push(seedURL);
    stacks.add(_st);

    // Initialize hash sets for visited urls per level.
    HashSet<String> _hs = new HashSet<>();
    for (int i = 0; i < maxLevel + 1; i++) {
      _hs = new HashSet<>();
      visitedUrls.add(_hs);
    }

    // Record seed url as visited.
    _hs = visitedUrls.get(0);
    _hs.add(seedURL);
    visitedUrls.set(0, _hs);

    while (stacks.size() > 0) {
      // Get stack at top level
      Stack<String> stack = stacks.get(stacks.size() - 1);
//      displayStack(stack);
      // If stack is empty remove and then continue
      if (stack.size() == 0) {
        stacks.remove(stacks.size() - 1);
        continue;
      }
      String currentURL = stack.pop();
      System.out.println("Sites crawled "+currentURL);
      if(currentURL.equals(targetURL))
      {
          isGoalNode = true;
          
          break;
      }
      Stack<String> intermediateStack = new Stack<>();

      // If creating a new stack would go beyond the max level then continue.
      if (stacks.size() - 1 >= maxLevel) {
        continue;
      }

      // Crawl the webpage of the current URL and add them to the intermediate stack and visited.
      HashSet<String> intermediateVisitedUrls = visitedUrls.get(stacks.size());
      String parsedURL = parseURL(currentURL);
      String regex = "https://(\\w+\\.)*(\\w+)";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(parsedURL);
      while (matcher.find()) {
        String link = matcher.group();
        boolean exists = false;
        for (HashSet<String> v : visitedUrls) {
          if (v.contains(link)) {
            exists = true;
            break;
          }
        }
        if (!exists) {
          intermediateStack.push(link);
          intermediateVisitedUrls.add(link);
        }
      }
      stacks.add(intermediateStack);
      visitedUrls.set(stacks.size() - 1, intermediateVisitedUrls);
    }
    for (int i = 0; i < visitedUrls.size(); i++) {
      System.out.println(".".repeat(80));
      System.out.println("Sites Crawled at depth " + i + " #: " + visitedUrls.get(i).size());
//      displaySet(visitedUrls.get(i));
    }
    if(isGoalNode == true)
    {
    	//int l = level +1;
    	System.out.println("The target url has been found");
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