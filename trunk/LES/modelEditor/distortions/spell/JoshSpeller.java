package modelEditor.distortions.spell;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import modelEditor.distortions.spell.engine.SpellDictionaryHashMap;
import modelEditor.distortions.spell.event.SpellChecker;

public class JoshSpeller {
	
	SpellChecker checker;
	
	public JoshSpeller(){
		checker = new SpellChecker();
		
		try {
			InputStream is = getClass().getResourceAsStream("/modelEditor/distortions/spell/english/english.0");
	        checker.addDictionary(new SpellDictionaryHashMap(new InputStreamReader(is)));
	      } catch (IOException ex) {
	        ex.printStackTrace();
	      }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JoshSpeller js = new JoshSpeller();
	      

	}

}
