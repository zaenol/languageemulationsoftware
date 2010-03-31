package modelEditor.openNLP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

public class learningOpenNLP {

	public learningOpenNLP() {
		try {
			String[] sents = sentences();
			for(String sent:sents){
				//tokenize(sent);
				System.out.println(sent);
				List<String> tokens = tokenizer(sent);
				List tags = tag(tokens);
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
	}
	
	public String[] sentences() throws IOException{
		String paragraph  = "This isn't the greatest example sentence in the world because I've seen better.  Neither is this one.  This one's not bad, though.";
		 
		// the sentence detector and tokenizer constructors
		// take paths to their respective models
		SentenceDetectorME sdetector =
		    new SentenceDetector("modelEditor/openNLP/sentdetect/EnglishSD.bin.gz");
		Tokenizer tokenizer = new Tokenizer("modelEditor/openNLP/tokenize/EnglishTok.bin.gz");
		 
		// the parser takes the path to the parser models
		// directory and a few other options
		boolean useTagDict = true;
		boolean useCaseInsensitiveTagDict = false;

		int beamSize = Parser.defaultBeamSize;
		double advancePercentage = Parser.defaultAdvancePercentage;
		opennlp.tools.parser.Parser parser = TreebankParser.getParser(
		        "modelEditor/openNLP/parser", useTagDict, useCaseInsensitiveTagDict,
		        beamSize, advancePercentage);
		 
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
		
		
		opennlp.tools.lang.english.Tokenizer tokenizer = new opennlp.tools.lang.english.Tokenizer("modelEditor/openNLP/tokenize/EnglishTok.bin.gz");
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
		    System.out.println("\t\t"+tok);
		}
		String text = sb.substring(0, sb.length() - 1).toString();
		System.out.println("\t"+text);
		return tokenList;
	}
	
	public List tag(List<String> tokenList) throws IOException, InvalidFormatException{
		String tagBiGz = "modelEditor/openNLP/postag/tag.bin.gz";
		String tagdict = "modelEditor/openNLP/postag/tagdict";
		
		
		
		SuffixSensitiveGISModelReader modelReader = (new SuffixSensitiveGISModelReader(new File(tagBiGz)));
		MaxentModel model = modelReader.getModel();
		
		//InputStream in = getClass().getResourceAsStream("modelEditor/openNLP/postag/tagdict");
		
		//InputStream in = new FileInputStream(tagBiGz);
		
		POSDictionary dictionary = new POSDictionary(tagdict);
		
		POSTaggerME tagger = new POSTaggerME(model,dictionary);
		List tags = tagger.tag(tokenList);
		return tags;
		

		
		//return null;
	}
	


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		learningOpenNLP lonlp = new learningOpenNLP();
	}

}
