package modelEditor.openNLP;

import java.io.IOException;

import opennlp.tools.lang.english.SentenceDetector;
import opennlp.tools.lang.english.Tokenizer;
import opennlp.tools.lang.english.TreebankParser;
import opennlp.tools.parser.chunking.Parser;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.parser.*;

/*
 * http://bulba.sdsu.edu/jeanette/thesis/PennTags.html
 * http://sagarsunkle.spaces.live.com/blog/cns!E07F3B561597E4EE!495.entry
 * http://danielmclaren.net/2007/05/11/getting-started-with-opennlp-natural-language-processing
 * 
 */

public class learningOpenNLP {

	public learningOpenNLP() {
		try {
			String[] sents = sentences();
		} catch (IOException e) {
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		learningOpenNLP lonlp = new learningOpenNLP();
	}

}
