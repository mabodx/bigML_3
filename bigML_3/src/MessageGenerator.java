import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessageGenerator {
  
  static public void main(String[] args) throws IOException{
    BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(System.out));
    
    
    String line = null; /* skip reading total occurrence */
  
    while((line = reader.readLine()) !=null){
      String [] tks = line.split("\\s+");
      String [] phrases = tks[0].split("-");
      if (phrases.length<2)
        continue;
      String comb = String.format("%s\t%s %s\t1\n", phrases[0], phrases[0], phrases[1]); /* + for xy, - for yx */
      writer.write(comb);
      comb = String.format("%s\t%s %s\t2\n", phrases[1], phrases[0], phrases[1]); /* + for xy, - for yx */
      writer.write(comb);
              
    }
   
    reader.close();
    writer.close();
  }

}
