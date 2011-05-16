package modelEditor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


import modelEditor.abstractClasses.AC_Element;

import org.w3c.dom.Element;

public class Model_Message_fullWord extends Model_Message_posWord{
	protected class fullWord{
		String oWord;
		ArrayList<PosWord> posWords;
		
		Random random;
		
		public fullWord(String oWord, ArrayList<PosWord> posWords){
			//System.out.println(oWord+":   ("+tokens+")  ("+tags+")");
			this.oWord = oWord;
			this.posWords = posWords;
			random = new Random();
		}
		
		public String getWord_Original(){
			return oWord;
		}
		
		public boolean isNewMessageAfterFullWord(){
			return posWords.get(posWords.size()-1).isNewMessageAfterWord();
		}
		
		public ArrayList<String> getWord_PostDist(){
			ArrayList<String> finalWord = new ArrayList<String>();
			
			boolean untouched = true;
			for(int i=0; i<posWords.size() && untouched;i++){
				PosWord p = posWords.get(i);
				if(posWords.get(i).isDistorted() || posWords.get(i).isNewMessageAfterWord()){
					untouched = false;
					
					//if the last element has a new line, who cares... original word will suffice
					if(i== posWords.size()-1 && !posWords.get(i).isDistorted() && posWords.get(i).isNewMessageAfterWord())
						untouched = true;						
				}
					
			}
			
			if(untouched){
				finalWord.add(this.getWord_Original());
			}
			else{	
				
				String currentMessage = "";
				
				PosWord _posWord = null;
				
				for(int i=0; i<posWords.size();i++){
					PosWord posWord = posWords.get(i);
					
					if(_posWord == null){
						if(i==posWords.size()-1){//last element
							if(posWord.isDistorted())
								currentMessage += posWord.getDistortedWord();
							else
								currentMessage += posWord.getTokenAsWord();
						}else{//not last element
							if(posWord.pos_isUnclassified() || !posWord.pos_isKnown()){//punctuation and other
								currentMessage += posWord.getToken()+" ";
								
								if(posWord.isNewMessageAfterWord()){
									finalWord.add(currentMessage);
									currentMessage= "";
								}
								
							}else{//is a real word
								_posWord = posWord;
							}		
						}
						
					}else{//previous element exists!
						
						if(!_posWord.isDistorted() && !_posWord.isNewMessageAfterWord()){//previous word is NOT distorted NOR New line
							
							if(posWord.isDistorted()){//current word is distorted
								currentMessage+=_posWord.getTokenAsWord()+" "+posWord.getDistortedWord();
							}else{//current word is normal
								currentMessage+=_posWord.getToken()+posWord.getToken();
							}
							
						}else{//previous word IS distorted or has a new line
							if(_posWord.isNewMessageAfterWord()){// new line after prior word
								
								
								
								if(_posWord.isDistorted())//prior word is distorted
									currentMessage += _posWord.getDistortedWord();
								else//prior word is not distorted
									currentMessage += _posWord.getTokenAsWord();
								
								if(currentMessage.length() != 0)
									finalWord.add(currentMessage);
								
				
								currentMessage= AC_Element.generateparalinguisticWithSpace();
								
								
								if(posWord.isDistorted()){//current word is distorted
									currentMessage+=posWord.getDistortedWord();
								}else{//current word is normal
									currentMessage+=posWord.getTokenAsWord();
								}
								
							}else{//no new line after prior word, use a space... also prior is distorted
								
								if(posWord.isDistorted()){//current word is distorted
									currentMessage+=_posWord.getDistortedWord()+" "+posWord.getDistortedWord();
								}else{//current word is normal
									currentMessage+=_posWord.getDistortedWord()+" "+posWord.getTokenAsWord();
								}
								
							}
							
						}
						
						if(posWord.isNewMessageAfterWord()){
						
							if(currentMessage.length() != 0)
								finalWord.add(currentMessage+AC_Element.generateparalinguisticWithSpace());
							currentMessage= "";
						}
						
						_posWord = null;
					}						
					
				}
				
				if(currentMessage.length()!=0)
					finalWord.add(currentMessage);

			}
			
			return finalWord;
		}
	
		public Element getXML(){
			Element word_element = dom.createElement("FullWord");
			word_element.appendChild(makeElementWithTextBody("OriginalWord",getWord_Original()));
			word_element.appendChild(makeElementWithIndextedChildren("FinalWord",getWord_PostDist(),"Word"));
			
			for(int i=0; i<posWords.size();i++){
				word_element.appendChild(posWords.get(i).getXML());
			}
			
			
			return word_element;
		}
		public String toString(){
			return "["+oWord+"]";
		}
		
	}
	
	
	
}
