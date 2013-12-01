/* ************************************** */
/* STREAMING COMPUTATION OF MOVIE RATINGS */
/* ************************************** */

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Streaming {

  private static List<UserRating> stream = new ArrayList<UserRating>();
  private static Map<Integer,Integer> ratings = new HashMap<Integer,Integer>();

  public static void main(String[] args) {
      try {
        parse();
        solve();
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