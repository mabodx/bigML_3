import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Aggregate {

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
    
    long tot_bfreq = 0, tot_cfreq=0;
    long b_freq = 0, c_freq = 0;
    String line = reader.readLine();
    String[] tks = line.split("\\s+");
    //System.out.println(Arrays.toString(tks));
    String last = tks.length==3? tks[0]: String.format("%s-%s", tks[0], tks[1]);
    //String last = tks[0];

    int year = Integer.parseInt(tks[1+type]);
    long n = Long.parseLong(tks[2+type]);

    if (year< corpus_year){
      b_freq += n;
      tot_bfreq += n;
    } else {
      c_freq += n;
      tot_cfreq += n;
    }
    
    
    while ((line = reader.readLine()) != null){
      tks = line.split("\\s+");
      //System.out.println(Arrays.toString(tks));
      String token = tks.length==3? tks[0]: String.format("%s-%s", tks[0], tks[1]);
      year = Integer.parseInt(tks[1+type]);
      n = Long.parseLong(tks[2+type]);
      
      if (!token.equals(last)){
        String output = String.format("%s\t0\t%d\t%d\n", last, b_freq, c_freq);
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
  
    } // endwhile
    String output = String.format("%s\t0\t%d\t%d\n", last, b_freq, c_freq);
    writer.write(output);
    output = type == 0? String.format("*\t%d\t%d\n", tot_bfreq, tot_cfreq): 
                        String.format("**\t%d\t%d\n", tot_bfreq, tot_cfreq);
    writer.write(output);
    writer.flush();
    reader.close();
    writer.close();
    
  }
}
