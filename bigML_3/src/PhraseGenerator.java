import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhraseGenerator {
  
  static long tot_Bx = 0, tot_Cx = 0, tot_Bxy = 0, tot_Cxy = 0;
  static int top = 20;
  
  
  static private class PhraseScore{
    String phrase;
    double score;
    double phraseness;
    double informativeness;
    public PhraseScore(String p, double s, double ph, double info){
      phrase = p;
      score = s;
      phraseness = ph;
      informativeness = info;
    }
  }
  
  static private class PSComparator implements Comparator<PhraseScore>{

    @Override
    public int compare(PhraseScore ps0, PhraseScore ps1) {
      if (ps0.score < ps1.score){
        return -1;
      }
      if (ps0.score > ps1.score){
        return 1;
      }
      return 0;
    }
    
  }
  static private String parseAttrValPair(String line, long[] freq){
    String[] tks = line.split("\\s+");
    freq[0] = Long.parseLong(tks[tks.length-2]);
    freq[1] = Long.parseLong(tks[tks.length-1]);
    return tks[0];
  }
  static double KL(double p, double q){
    if (p<=0 || q<=0) 
      return -Double.MAX_VALUE;
    return p* Math.log(p/q);
  }
  
  static private double getPhraseness(long x, long y, long xy){
    double px = (double) x/ tot_Cx;
    double py = (double) y/ tot_Cx;
    double pxy = (double) xy/ tot_Cxy;
    return KL(pxy, px*py);
  }
  
  static private double getInformativeness(long Cxy, long Bxy){
    double pCxy = (double) Cxy/ tot_Cxy;
    double pBxy = (double) Bxy/ tot_Bxy;
    return KL(pCxy, pBxy);
  }
  
  static public void main(String [] args) throws IOException{
    BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
    //BufferedReader reader = new BufferedReader (new FileReader("tmp"));
    BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(System.out));
    
    PriorityQueue<PhraseScore> hfPhrases = new PriorityQueue<PhraseScore>(top, new PSComparator());
    
    String line = reader.readLine();
    long[] freq = new long[2];
    parseAttrValPair(line, freq);
    tot_Bx = freq[0];
    tot_Cx = freq[1];

    
    
    line = reader.readLine();
    parseAttrValPair(line, freq);
    tot_Bxy = freq[0];
    tot_Cxy = freq[1];
    
    while ((line = reader.readLine()) != null){
      parseAttrValPair(line, freq);
      long Bxy = freq[0];
      long Cxy = freq[1];

      
      line = reader.readLine();
      parseAttrValPair(line,  freq);
      long By = freq[0];
      long Cy = freq[1];
      
      line = reader.readLine();
      String phrase = parseAttrValPair(line, freq);
      //System.out.println(phrase);
      long Bx = freq[0];
      long Cx = freq[1];
      
      double phraseness = getPhraseness(Cx, Cy, Cxy);
      double informativeness = getInformativeness(Cxy, Bxy);
      double score = phraseness + informativeness;
      
      if (hfPhrases.size() < top){
        
        hfPhrases.add(new PhraseScore(phrase, score, phraseness, informativeness));
      } else{
        PhraseScore ps = hfPhrases.peek();
        if (score > ps.score){
          hfPhrases.poll();
          hfPhrases.add(new PhraseScore(phrase, score, phraseness, informativeness));
        } //endif
      } //endif
    } //endwhile
    LinkedList<PhraseScore> list = new LinkedList<PhraseScore> ();
    while (!hfPhrases.isEmpty()){
      list.push(hfPhrases.poll());
    }
    while (!list.isEmpty()){
      PhraseScore ps = list.pop();
      String [] words = ps.phrase.split("-");
      String output = String.format("%s %s\t%f\t%f\t%f", words[0], words[1], ps.score, ps.phraseness, ps.informativeness);
      System.out.println(output);
      
    }
  }
}
