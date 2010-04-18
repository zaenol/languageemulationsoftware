package modelEditor.distortions.spelling;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JaSpellWidget {

	SpellChecker spellCheck;
	
	public JaSpellWidget(){
		spellCheck = new SpellChecker();
		
		String lang = "english.txt";
		
		String preDataDict = "/modelEditor/distortions/spelling/";
		
		String dataDir = "dict";
		
		String misspells = dataDir + "/common-misspells.txt";
		String jargon = dataDir + "/jargon.txt";
		
		dataDir += "/" + lang;
		
		try {
			spellCheck.initialize(preDataDict+dataDir,preDataDict+misspells,preDataDict+jargon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isRealWord(String aWord){
		ArrayList<String> nearWords = findSimilarWords(aWord);
		for(String s:nearWords){
			if(s.equalsIgnoreCase(aWord))
				return true;
		}
		return false;
	}
	
	public ArrayList<String> findSimilarWords(String aWord){
		ArrayList<String> similarWords = new ArrayList<String>();
		
		List results = spellCheck.findMostSimilarList(aWord);
		//for(Object o:results)
		//	System.out.println(">> "+o);
		
		similarWords.addAll(results);
		
		return similarWords;
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JaSpellWidget jsw;
		try {
			jsw = new JaSpellWidget();
			System.out.println(jsw.findSimilarWords("block"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
