package modelEditor.distortions.rhyme;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class Rhymer {
	Hashtable<String,String[]> indexTable;
	Hashtable<String,ArrayList<String>>rhymeTable;
	

	public Rhymer() {
		indexTable = new Hashtable<String,String[]>();
		rhymeTable = new Hashtable<String,ArrayList<String>>();
		 URL url;
		    try {
		      //url = new URL(this.g"modelEditor/distortions/rhyme/phondic.english.txt");
		      InputStream in = getClass().getResourceAsStream("/modelEditor/distortions/rhyme/phondic.english.txt");
		      BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));
		      String line = "";
		      while ((line = buffRead.readLine()) != null){
		    	//System.out.println(line); 
		    	constructHashtable(line);
		      }
		    }
		    catch(Exception e) {System.out.println("fail 1");e.printStackTrace();}

	}
	
	public ArrayList<String> getRhymes(String targetWord){
		String stress = this.getStress(targetWord);
		ArrayList<String> rhymes = new ArrayList<String>();
		if(stress != null)
			if(rhymeTable.containsKey(stress))
				rhymes = (ArrayList<String>) rhymeTable.get(stress).clone();
		rhymes.remove(targetWord);
		return rhymes;
	}
	
	private void constructHashtable(String astring) {
	    StringTokenizer words = new StringTokenizer(astring);
	    String[] word = new String[words.countTokens()];
	    int i = 0;
	
	    while (words.hasMoreTokens()) {
	      word[i] = words.nextToken();
	      i++;
	    }
	    indexTable.put(word[0], word);
	    
	    String stress = getStress(word[0]);
	    
	    if(stress != null){
	    	if( !rhymeTable.containsKey(stress))
	    		rhymeTable.put(stress, new ArrayList<String>());
	    	rhymeTable.get(stress).add(word[0]);
	    }else{
	    	indexTable.remove(word[0]);
	    }
	    
	    /*
	    if (word[0].equals(targetWord)) {
	      stress = word[2].indexOf("'");
	      return word[2].substring(stress);
	    }
	    else { return null; }*/
	  }
	
	private String getStress(String targetWord){
		if(indexTable.containsKey(targetWord)){
			String[] info = indexTable.get(targetWord);
			String info2 = info[2];
			int stress = info2.indexOf("'");
			if(stress<0)
				return null;
		    return info2.substring(stress);
		}
		else{
			return null;
		}
	}
	
	String mhp(String astring, String targetWord) {
	    StringTokenizer words = new StringTokenizer(astring);
	    String[] word = new String[words.countTokens()];
	    int i = 0;
	    int stress = 0;
	    while (words.hasMoreTokens()) {
	      word[i] = words.nextToken();
	      i++;
	    }
	    if (word[0].equals(targetWord)) {
	      stress = word[2].indexOf("'");
	      return word[2].substring(stress);
	    }
	    else { return null; }
	  }
	
	ArrayList<String> mhp2(String theline, String arhyme) {
		ArrayList<String> rhymes = new ArrayList<String>();
		
	    StringTokenizer words2 = new StringTokenizer(theline);
	    String[] wordsinline = new String[words2.countTokens()];
	    int i = 0;
	    while (words2.hasMoreTokens()) {
	      wordsinline[i] = words2.nextToken();
	      i++;
	    }
	    if (wordsinline[2].endsWith(arhyme)) {
	    	rhymes.add(wordsinline[0]);
	    }
	    return rhymes;
	  }

	public static void main(String[] args){
		Rhymer r = new Rhymer();
		System.out.println(r.getRhymes("computer"));
	}
}
