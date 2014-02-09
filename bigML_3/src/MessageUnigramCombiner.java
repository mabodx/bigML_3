import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessageUnigramCombiner {
  static public Pattern pair_pattern = Pattern.compile("(\\w+)\\s+(\\d+\\s+\\d+)"); // "word Bx Cx"
  static public Pattern bigram_pattern = Pattern.compile("(\\w+)\\s+(\\w+\\s+\\w+)\\s+(\\W)");  //"word1  word1 word2  1/2"
  
  static public void main(String[] args) throws IOException{
    BufferedReader reader = new BufferedReader (new InputStreamReader(System.in)); /* message.txt unigram_processed.txt */
    BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(System.out));
    
    
    String line = reader.readLine();  /* skip reading total word occurrence */
    writer.write(line);
    writer.write('\n');
    Matcher matcher = null;
    line = reader.readLine();
    while(line  !=null){
      matcher = pair_pattern.matcher(line);
      String attr = "", val = "";
      if (matcher.matches()){
        attr = matcher.group(1);
        val = matcher.group(2);
        //System.out.println(attr + val);
      }
      while((line = reader.readLine() )!=null){
        matcher = bigram_pattern.matcher(line);
        if (matcher.matches()){
          //System.out.println(attr + "\t"+matcher.group(1));
//          if (!attr.equals( matcher.group(1))){
//            continue;
//          }
          String phrase = matcher.group(2);
          String flag = matcher.group(3);   /* + for xy, - for yx */
          String comb = String.format("%s %s %s %s\n", phrase, flag, flag, val);
          writer.write(comb);
        } else {
          break;
        }
      }
      
    }
    reader.close();
    writer.close();
  }


}
