package modelEditor.distortions.conceptNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Random;

public class ConceptuallyRelatedIndex {

	Hashtable<String,ArrayList<String>> database;
	
	String line = "";
    String spacer = "\",\"";
    
    Random random;
	
	public ConceptuallyRelatedIndex() {
		database = new Hashtable<String,ArrayList<String>>();
		
		random = new Random();
		
		String fullPath = "/modelEditor/distortions/conceptNet/";
		String fileName = "ConceptuallyRelatedTo.csv";
		InputStream in = getClass().getResourceAsStream(fullPath+fileName);
		
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));
		
		
		try {
			buffRead.readLine();
			
			while ((line = buffRead.readLine()) != null){
		    	
		    	String subLine = line.substring(1, line.length()-1);
		    	
		    	String[] elements = subLine.split(spacer);
		    	
		    	String word = elements[0];
		    	String relation = elements[1];
		    	
		    	if(!database.containsKey(word))
		    		database.put(word, new ArrayList<String>());
		    	database.get(word).add(relation);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//header line
	    
		
	}
	
	public boolean containsWord(String word){
		return database.containsKey(word);
	}
	
	public String getConceptuallyRelatedTo(String word){
		if(database.containsKey(word)){
			ArrayList<String> relations = database.get(word);
			if(relations.size()==1)
				return relations.get(0);
			else{
				boolean notFound = true;
				while(notFound){
					String relation = relations.get(random.nextInt(relations.size()));
					if(!relation.equalsIgnoreCase(word)){
						notFound = false;
						return relation; 
					}
				}
			}
			
		}
		return word;
	}

}