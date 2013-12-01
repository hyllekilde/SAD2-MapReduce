/* *************************************** */
/* SEQUENTIAL COMPUTATION OF MOVIE RATINGS */
/* *************************************** */

/*
TODO: measure computation time
TODO: provide file paths as cmd line args
*/

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Sequential {

  private static Map<Integer,String> movies = new HashMap<Integer,String>();                // movie_id -> movie_title
  private static Map<Integer,List<Integer>> ratings = new HashMap<Integer,List<Integer>>(); // movie_id -> list of user ratings
  private static Map<Integer,Double> avgratings = new HashMap<Integer,Double>();            // movie_id -> average rating

  public static void main(String[] args) {
    try {
      parse();
      //testRatingCount();
      solve();
      writeOutput();
    } catch(IOException e) {
      System.out.println(e.toString());
    }
  }

  private static void parse() throws IOException {
    String filename;
    Path path;
    Scanner scanner;
    String line;
    int i;

    // parse movies
    filename = "../../data/1M/movies.dat";
    path = Paths.get(filename);
    scanner = new Scanner(path);
    scanner.useDelimiter(System.getProperty("line.separator"));
    
    i = 0;
    while(scanner.hasNext()) {
      line = scanner.next();
      String[] split = line.split("::");
      movies.put(Integer.parseInt(split[0]), split[1]);
      //System.out.println(movies.get(i));
      i++;
    }

    System.out.println("parsed "+i+" movies");

    //parse ratings
    filename = "../../data/1M/ratings.dat";
    path = Paths.get(filename);
    scanner = new Scanner(path);
    scanner.useDelimiter(System.getProperty("line.separator"));

    i = 0;
    while(scanner.hasNext()) {
      line = scanner.next();
      String[] split = line.split("::");

      if(ratings.containsKey(Integer.parseInt(split[1]))) {
        ratings.get(Integer.parseInt(split[1])).add(Integer.parseInt(split[2]));
      } else {
        ratings.put(Integer.parseInt(split[1]), new ArrayList<Integer>());
        ratings.get(Integer.parseInt(split[1])).add(Integer.parseInt(split[2]));
      }

      i++;
    }

    System.out.println("parsed "+i+" ratings");
  }

  private static void solve() {
    List<Integer> list;
    double sum;
    double avg;

    for (Map.Entry<Integer,List<Integer>> entry : ratings.entrySet()) {
      list = entry.getValue();

      sum = 0;
      for(int rating: list)
        sum = sum+rating;

      avg = sum/list.size();
      avgratings.put(entry.getKey(), avg);
    }

    System.out.println("computed average ratings");
  }

  private static void writeOutput() throws IOException {
    PrintWriter writer = new PrintWriter("Sequential.out", "UTF-8");
    int i = 0;
    
    for (Map.Entry<Integer,Double> entry : avgratings.entrySet()) {
      writer.println(entry.getKey()+": "+entry.getValue());
      i++;
    }

    writer.close();
    System.out.println("written "+i+" elements to output");
  }

  private static void testRatingCount() {
    List<Integer> list;
    int count = 0;

    for (Map.Entry<Integer,List<Integer>> entry : ratings.entrySet()) {
      list = entry.getValue();
      count += list.size();
    }

    System.out.println("rating count: "+count);
  }

}