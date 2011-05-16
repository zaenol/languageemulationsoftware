package modelEditor.distortions.conceptNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Random;

import modelEditor.abstractClasses.AC_Element;

public class DefinitionIndex {

	Hashtable<String,ArrayList<String>> database;
	
	String line = "";
    String spacer = "\",\"";
    
    Random random;
	
	public DefinitionIndex() {
		database = new Hashtable<String,ArrayList<String>>();
		
		random = new Random();
		
		String fullPath = "/modelEditor/distortions/conceptNet/";
		String fileName = "forDefinitions_contentOnly.csv";
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
	
	
	public String getDescription(String word){
		if(database.containsKey(word)){
			String sDescription = AC_Element.generateparalinguisticOrEmpty(2);
			
			ArrayList<String> relations = database.get(word);
			
			if(relations.size()>0){
				int max = 7;
				if(relations.size()<max)
					max = relations.size();
				
				int drawCount = 1;
				
				if(max<=1)
					drawCount = 1;
				else{
					drawCount = random.nextInt(max-1)+1;
				}
				ArrayList<String> chosenWords = new ArrayList<String>();
				
				
				while(chosenWords.size()<drawCount){
					String chosenWord = getDescriptiveWord(word);
					if(!chosenWords.contains(chosenWord)){
						chosenWords.add(chosenWord);
						sDescription += " "+chosenWord+" "+AC_Element.generateparalinguistic();
					}					
				}
				
				
				return sDescription;
			}
		}
		return word;
	}
	
	/*
	private String generateparalinguistic(){
		int i = random.nextInt(3);
		if(i==0)
			return "...";
		else if(i==1)
			return "um...";
		else
			return "uh...";
	}*/
	
	private String getDescriptiveWord(String word){
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
