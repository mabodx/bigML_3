import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessageUnigramCombiner {
  //static public Pattern pair_pattern = Pattern.compile("(\\w+)\\s+(\\d+\\s+\\d+)"); // "word Bx Cx"
  //static public Pattern bigram_pattern = Pattern.compile("(\\w+)\\s+(\\w+\\s+\\w+)\\s+(\\W)");  //"word1  word1 word2  1/2"
  
  static public void main(String[] args) throws IOException{
    BufferedReader reader = new BufferedReader (new InputStreamReader(System.in)); /* message.txt unigram_processed.txt */
    BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(System.out));
    
    
    String line = reader.readLine();  /* skip reading total word occurrence */
    writer.write(line);
    writer.write('\n');
    
    line = reader.readLine();
    while(line  !=null){
      String [] pair = line.split("\\s+");
      String attr = pair[0];
      String bx = pair[2], cx = pair[3];
      
      while((line = reader.readLine() )!=null){
        
        String [] msg = line.split("\t");
        if (msg.length > 3) 
          break;
        String flag = msg[2];
        String[] phrases = msg[1].split("\\s+");
        String combo = String.format("%s-%s\t%s\t%s\t%s\n", phrases[0], phrases[1], flag, bx, cx);
        writer.write(combo);
      }
      
    }
    reader.close();
    writer.close();
  }


}
