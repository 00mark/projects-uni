package io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extends the LineNumberReader by searching for matches with a given regular
 * expression and counting them while reading.
 * 
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 * 
 */
public class SearchLineReader extends LineNumberReader {

   /**
    * {@code Pattern} to search matches for
    */
   private final Pattern pattern;

   /**
    * total amount of currently found matches with {@code pattern}
    */
   private int count;

   /**
    * 
    * @param in
    *           {@code Reader} to be wrapped by this {@code SearchLineReader}
    * @param search
    *           regular expression to search for
    */
   public SearchLineReader(Reader in, Pattern search) {
      super(in);
      this.pattern = search;
      this.count = 0;
   }

   /**
    * 
    * @param in
    *           {@code Reader} to be wrapped by this {@code SearchLineReader}
    * @param search
    *           regular expression to search for
    * @throws PatternSyntaxException
    *            if {@code search} is not a valid regular expression.
    */
   public SearchLineReader(Reader in, String search) {
      super(in);
      this.pattern = Pattern.compile(search);
      this.count = 0;
   }

   @Override
   public String readLine() throws IOException {

      String line = super.readLine();
      count = 0;

      if (line != null) {
         Matcher m = this.pattern.matcher(line);
         while (m.find()) {
            count++;
         }
      }
      return line;
   }

   /**
    * Get the number of matches with the given regular expression in the latest
    * read line via {@link #readLine()}.
    * 
    * @return number of already found matches in the last call of
    *         {@link #readLine()} with the given regular expression of this
    *         {@code SearchLineReader}
    */
   public int getAmountOfMatches() {
      return this.count;
   }

}
