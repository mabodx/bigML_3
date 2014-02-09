import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessageGenerator {
  static public Pattern bi_pattern = Pattern.compile("(\\w+)\\s+(\\w+)\\s+\\d+\\s+\\d+");
  
  static public void main(String[] args) throws IOException{
    BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(System.out));
    
    
    String line = null; /* skip reading total occurrence */
    Matcher matcher = null;
    while((line = reader.readLine()) !=null){
      matcher = bi_pattern.matcher(line);
      if (matcher.matches()){
        String tk1 = matcher.group(1);
        String tk2 = matcher.group(2);
        //String info = matcher.group(3);
        String comb = String.format("%s %s %s +\n", tk1, tk1, tk2); /* + for xy, - for yx */
        writer.write(comb);
        comb = String.format("%s %s %s -\n", tk2, tk1, tk2); /* + for xy, - for yx */
        writer.write(comb);
                
      }
    }
    reader.close();
    writer.close();
  }

}
