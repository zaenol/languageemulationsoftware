package NLP.openNLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import NLP.Gen_NLP;
import NLP.StringObject;

import opennlp.maxent.MaxentModel;
import opennlp.maxent.io.SuffixSensitiveGISModelReader;
import opennlp.tools.lang.english.SentenceDetector;
import opennlp.tools.lang.english.Tokenizer;
import opennlp.tools.lang.english.TreebankParser;
import opennlp.tools.parser.chunking.Parser;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.parser.*;
import opennlp.tools.postag.POSDictionary;
import opennlp.tools.postag.POSTaggerME;

/*
 * http://bulba.sdsu.edu/jeanette/thesis/PennTags.html
 * http://sagarsunkle.spaces.live.com/blog/cns!E07F3B561597E4EE!495.entry
 * http://danielmclaren.net/2007/05/11/getting-started-with-opennlp-natural-language-processing
 * http://www.ims.uni-stuttgart.de/projekte/CorpusWorkbench/CQP-HTMLDemo/PennTreebankTS.html
 * 
 */

public class openNLP implements Gen_NLP{
	boolean debugOut = false;

	opennlp.tools.lang.english.Tokenizer tokenizer;
	SentenceDetectorME sdetector;
	
	POSTaggerME tagger;
	
	boolean loaded = false;
	
	public openNLP() {
		
		try {
			tokenizer = new opennlp.tools.lang.english.Tokenizer("NLP/openNLP/tokenize/EnglishTok.bin.gz");
			sdetector =  new SentenceDetector("NLP/openNLP/sentdetect/EnglishSD.bin.gz");
			
			boolean useTagDict = true;
			boolean useCaseInsensitiveTagDict = false;

			
			int beamSize = Parser.defaultBeamSize;
			double advancePercentage = Parser.defaultAdvancePercentage;
			opennlp.tools.parser.Parser parser = TreebankParser.getParser(
			        "NLP/openNLP/parser", useTagDict, useCaseInsensitiveTagDict,
			        beamSize, advancePercentage);
			
			
			String tagBiGz = "NLP/openNLP/postag/tag.bin.gz";
			String tagdict = "NLP/openNLP/postag/tagdict";
			
			
			SuffixSensitiveGISModelReader modelReader = (new SuffixSensitiveGISModelReader(new File(tagBiGz)));
			MaxentModel model = modelReader.getModel();
			
			
			POSDictionary dictionary = new POSDictionary(tagdict);
			
			tagger = new POSTaggerME(model,dictionary);
			loaded = true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public boolean isLoaded() {
		return loaded;
	}


	public ArrayList<ArrayList<StringObject>> runNLP(String paragraph) {
		ArrayList<ArrayList<StringObject>> result = new ArrayList();;
		ArrayList<StringObject> all_tokens = new ArrayList<StringObject>();
		ArrayList<StringObject> all_tags = new ArrayList<StringObject>();
		result.add(all_tokens);
		result.add(all_tags);
		
		try {
			String[] sents = sentences(paragraph);
			
			for(String sent:sents){
				//tokenize(sent);
				if(debugOut)
					System.out.println(sent);
				List<String> tokens = tokenizer(sent);
				List<String> tags = tag(tokens);
				for(int i=0; i<tokens.size();i++){
					all_tokens.add(new StringObject(tokens.get(i)));
					all_tags.add(new StringObject(tags.get(i)));
				}
				if(debugOut)
					for(Object o:tags)
						System.out.println("\t\t"+o);
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(all_tokens.size()==0){
			all_tokens.add(new StringObject(paragraph));
			all_tags.add(new StringObject("FAIL"));
		}
			
		return result;
	}
	
	public String[] sentences(String paragraph) throws IOException{
		
		 
		// the sentence detector and tokenizer constructors
		// take paths to their respective models
		
		//SentenceDetectorME sdetector =  new SentenceDetector("NLP/openNLP/sentdetect/EnglishSD.bin.gz");
		//Tokenizer tokenizer = new Tokenizer("NLP/openNLP/tokenize/EnglishTok.bin.gz");
		 
		// the parser takes the path to the parser models
		// directory and a few other options
		
		/*
		boolean useTagDict = true;
		boolean useCaseInsensitiveTagDict = false;

		int beamSize = Parser.defaultBeamSize;
		double advancePercentage = Parser.defaultAdvancePercentage;
		opennlp.tools.parser.Parser parser = TreebankParser.getParser(
		        "NLP/openNLP/parser", useTagDict, useCaseInsensitiveTagDict,
		        beamSize, advancePercentage);
		*/
		 
		// break a paragraph into sentences
		
		String[] sents = sdetector.sentDetect(paragraph);
		return sents;
	}
	
	public List<String> tokenizer(String sent) throws IOException{
		
		 
		// tokenize brackets and parentheses by putting a space on either side.
		// this makes sure it doesn't get confused with output from the parser
		//sent = untokenizedParenPattern1.matcher(sent).replaceAll("$1 $2");
		//sent = untokenizedParenPattern2.matcher(sent).replaceAll("$1 $2");
		 
		// get the tokenizer to break apart the sentence
		
		
		
		String[] tokens = tokenizer.tokenize(sent);
		 
		// build a string to parse as well as a list of tokens
		StringBuffer sb = new StringBuffer();
		List<String> tokenList = new ArrayList<String>();
		for (int j = 0; j < tokens.length; j++)
		{
		    //String tok = convertToken(tokens[j]);
			String tok = tokens[j]+"";
		    tokenList.add(tok);
		    sb.append(tok).append(" ");
		    if(debugOut)
		    	System.out.println("\t\t"+tok);
		}
		String text = sb.substring(0, sb.length() - 1).toString();
		if(debugOut)
			System.out.println("\t"+text);
		return tokenList;
	}
	
	public List tag(List<String> tokenList) throws IOException, InvalidFormatException{
		/*
		String tagBiGz = "NLP/openNLP/postag/tag.bin.gz";
		String tagdict = "NLP/openNLP/postag/tagdict";
		
		
		
		SuffixSensitiveGISModelReader modelReader = (new SuffixSensitiveGISModelReader(new File(tagBiGz)));
		MaxentModel model = modelReader.getModel();
		*/
		
		//InputStream in = getClass().getResourceAsStream("modelEditor/openNLP/postag/tagdict");
		
		//InputStream in = new FileInputStream(tagBiGz);
		
		//POSDictionary dictionary = new POSDictionary(tagdict);
		
		//POSTaggerME tagger = new POSTaggerME(model,dictionary);
		
		List tags = tagger.tag(tokenList);
		return tags;
		

		
		//return null;
	}
	


	public boolean isDebugOut() {
		return debugOut;
	}

	public void setDebugOut(boolean debugOut) {
		this.debugOut = debugOut;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String paragraph  = "This isn't the greatest example sentence in the world because I've seen better.  Neither is this one.  This one's not bad, though.";
		// TODO Auto-generated method stub
		openNLP lonlp = new openNLP();
		lonlp.setDebugOut(true);
		lonlp.runNLP(paragraph);
	}

}