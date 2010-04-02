package NLP.stanfordNLP;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

class TaggerDemo {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("usage: java TaggerDemo modelFile fileToTag");
      return;
    }
    MaxentTagger tagger = new MaxentTagger(args[0]);
    @SuppressWarnings("unchecked")
    List<Sentence<? extends HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader(args[1])));
    for (Sentence<? extends HasWord> sentence : sentences) {
      Sentence<TaggedWord> tSentence = MaxentTagger.tagSentence(sentence);
      System.out.println(tSentence.toString(false));
    }
  }

}
