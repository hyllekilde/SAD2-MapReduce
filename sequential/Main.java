import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Iterator;

public class Main {

    private static Map<Integer,String> movies = new HashMap<Integer,String>();    // movies:  movie_id -> movie_title
    private static Map<Integer,List<Integer>> ratings = new HashMap<Integer,List<Integer>>(); // ratings: movie_id -> user_rating

    public static void main(String[] args) {
      try {
        parse();
        //testRatingCount();
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

        //System.out.println(Integer.parseInt(split[1])+" -> "+ratings.get(Integer.parseInt(split[1])));
        i++;
      }

      System.out.println("parsed "+i+" ratings");
    }

    private static void solve() {
      
    }

    private static void testRatingCount() {
      Iterator<Map.Entry<Integer,List<Integer>>> it = ratings.entrySet().iterator();
      int count = 0;
      while (it.hasNext()) {
        Map.Entry<Integer,List<Integer>> entry = it.next();
        List<Integer> list = entry.getValue();
        count += list.size();
        it.remove(); // avoids a ConcurrentModificationException
      }
      System.out.println(count);
    }

}