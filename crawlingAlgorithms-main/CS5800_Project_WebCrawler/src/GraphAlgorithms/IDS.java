package GraphAlgorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDS {
  public static void crawlIDS(String seedURL, int maxLevel, String targetURL) {
    
	  int level =1;
	  
	  System.out.println(".".repeat(80));

    while (level <=maxLevel) {
    	
    	System.out.println("Iteration#:-"+level);
    	if(DLS2.crawlDLS(seedURL, level, targetURL))
    	{
    		break;
    	}
    	level += 1;
     
    }
      
      
      
      
      
    }
    
  

 
}
