package modelEditor.distortions.conceptNet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;

import modelEditor.model.Model_Message_posWord;
import modelEditor.model.Model_Message_posWord.PosWord;

import NLP.BooleanObject;
import NLP.StringObject;
import NLP.openNLP.openNLP;

public class RefineCSVFile {

	public RefineCSVFile(String fileName) throws IOException {
		openNLP onlp = new openNLP();
		Model_Message_posWord mode_message_posWord = new Model_Message_posWord();
		
		String fullPath = "/modelEditor/distortions/conceptNet/";
		InputStream in = getClass().getResourceAsStream(fullPath+fileName);
		
		File file = new File("."+fullPath+fileName.replace(".csv", "_contentOnly.csv"));
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));
	      String line = "";
	      String spacer = "\",\"";
	      while ((line = buffRead.readLine()) != null){
	    	//System.out.println(line); 
	    	String subLine = line.substring(1, line.length()-1);
	    	//System.out.println("\t"+subLine);
	    	String[] elements = subLine.split(spacer);
	    	
	    	String word = elements[0];
	    	
	    	if(!word.contains("\'")){
	    		ArrayList<ArrayList<StringObject>> result = onlp.runNLP(word);
	    		ArrayList<StringObject> tokens = result.get(0);
	    		ArrayList<StringObject> tags = result.get(1);
	    		StringObject token = tokens.get(0);
	    		StringObject tag = tags.get(0);
	    		
	    		//StringObject tokens, StringObject tags, StringObject distoredWord, StringObject DistortionTypes, BooleanObject IsDistorted, BooleanObject NewMessageAfterWord
	    		PosWord pword = mode_message_posWord.makePosWord(token,tag);
	    		if(pword.pos_isContentWord()){
	    			output.write(line);
	    			output.newLine();
	    		}
	    		
	    	}
	    	
	    	//constructHashtable(line);
	    	//output.write(str);
	    	//output.newLine();
	    	
	      }
	      output.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String fileName = "forDefinitions.csv";
		String fileName = "ConceptuallyRelatedTo.csv";
		
		try {
			RefineCSVFile fcsvf = new RefineCSVFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
