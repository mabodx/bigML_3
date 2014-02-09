import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Aggregate {
  //static public String outputFile = "%s_processed.txt";
  static public Pattern uni_pattern = Pattern.compile("(\\w+)\\s+(\\d+)\\s+(\\d+)");
  static public Pattern bi_pattern = Pattern.compile("(\\w+\\s+\\w+)\\s+(\\d+)\\s+(\\d+)");
  static final public int  corpus_year = 1990;
  
  static public void main (String[] args) throws IOException{
    if (args.length<1){
      System.err.println("Type of input file is missing: either 0 or 1");
      System.exit (-1);
    }
    
    int type = 0;
    try{
      type = Integer.parseInt(args[0]);
      if (type > 0){
        type = 1;
      }
    } catch(Exception e){
      System.err.println("Type of input file must an 0 or 1");
      System.exit (-1);
    }
    BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(System.out));
    
    Pattern pattern = type==0? uni_pattern: bi_pattern;
    
    String line = reader.readLine();
    Matcher matcher = pattern.matcher(line);
    String last = "";
    long tot_bfreq = 0, tot_cfreq=0;
    long b_freq = 0, c_freq = 0;
    if (matcher.matches()){
      last = matcher.group(1);
      int year = Integer.parseInt(matcher.group(2));
      long n = Long.parseLong(matcher.group(3));
      
      if (year< corpus_year){
        b_freq += n;
        tot_bfreq += n;
      } else {
        c_freq += n;
        tot_cfreq += n;
      }
    }
    
    while ((line = reader.readLine()) != null){
      matcher = pattern.matcher(line);
      if (matcher.matches()){
        String token = matcher.group(1);
        int year = Integer.parseInt(matcher.group(2));
        long n = Long.parseLong(matcher.group(3));
        
        if (!token.equals(last)){
          String output = String.format("%s %d %d\n", last, b_freq, c_freq);
          writer.write(output);
          b_freq = 0;
          c_freq = 0;
          last = token;
        }
        if (year< corpus_year){
          b_freq += n;
          tot_bfreq += n;
        } else {
          c_freq += n;
          tot_cfreq += n;
        }
      } //endif match
    } // endwhile
    String output = String.format("%s %d %d\n", last, b_freq, c_freq);
    writer.write(output);
    output = type == 0? String.format("* %d %d\n", tot_bfreq, tot_cfreq): 
                        String.format("* * %d %d\n", tot_bfreq, tot_cfreq);
    writer.write(output);
    writer.flush();
    reader.close();
    writer.close();
    
  }
}
