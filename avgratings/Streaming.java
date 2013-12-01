/* ************************************** */
/* STREAMING COMPUTATION OF MOVIE RATINGS */
/* ************************************** */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Streaming {

  private static List<UserRating> stream = new ArrayList<UserRating>();
  private static Map<Integer,Integer> ratings = new HashMap<Integer,Integer>();
  private static Map<Integer,Integer> counters = new HashMap<Integer,Integer>();
  private static Map<Integer,Double> avgratings = new HashMap<Integer,Double>();

  public static void main(String[] args) {
    try {
      parse();
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

    UserRating userrating;

    //parse ratings
    filename = "../../data/1M/ratings.dat";
    path = Paths.get(filename);
    scanner = new Scanner(path);
    scanner.useDelimiter(System.getProperty("line.separator"));

    i = 0;
    while(scanner.hasNext()) {
      line = scanner.next();
      String[] split = line.split("::");

      userrating = new UserRating(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
      stream.add(userrating);

      i++;
    }

    System.out.println("parsed "+i+" ratings");
  }

  private static void solve() {
    Double avgrating;
    int i = 0;

    for (UserRating userrating : stream) {
      if(ratings.containsKey(userrating.getMovieId())) {
        ratings.put(userrating.getMovieId(), ratings.get(userrating.getMovieId()) + userrating.getRating());
        counters.put(userrating.getMovieId(), counters.get(userrating.getMovieId()) + 1);
      } else {
        ratings.put(userrating.getMovieId(), userrating.getRating());
        counters.put(userrating.getMovieId(), 1);
      }
      i++;
    }
    
    System.out.println("solved "+i+" elements in stream");

    for (Map.Entry<Integer,Integer> entry : ratings.entrySet()) {
      avgrating = entry.getValue() / (double)counters.get(entry.getKey());
      avgratings.put(entry.getKey(), avgrating);
    }

    System.out.println("computed average ratings");
  }

  private static void writeOutput() throws IOException {
    PrintWriter writer = new PrintWriter("Streaming.out", "UTF-8");
    
    for (Map.Entry<Integer,Double> entry : avgratings.entrySet())
      writer.println(entry.getKey()+": "+entry.getValue());

    writer.close();
    System.out.println("written output to file");
  }

  private static class UserRating {

    private int movieid;
    private int rating;

    public UserRating(int movieid, int rating) {
      this.movieid = movieid;
      this.rating = rating;
    }

    public int getMovieId() {
      return movieid;
    }

    public int getRating() {
      return rating;
    }

  }

}