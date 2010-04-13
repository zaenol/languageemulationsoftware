package modelEditor.distortions.rhyme;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class rhap2 extends Applet implements ActionListener {
  Button search = new Button("Search");
  Button clear = new Button("Clear");
  TextField word = new TextField(15);
  TextArea rhymes = new TextArea(15,20);
  String aword;
  StringBuffer theresult = new StringBuffer("");

  public void init() {
    setBackground(Color.pink);
    search.setBackground(Color.green);
    search.addActionListener(this);
    search.setActionCommand("search");
    add(search);
    clear.setBackground(Color.red);
    clear.addActionListener(this);
    clear.setActionCommand("clear");
    add(clear);
    word.setBackground(Color.yellow);
    add(word);
    rhymes.setBackground(Color.yellow);
    rhymes.setEditable(false);
    add(rhymes);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() == "search") {
      if (word.getText().equals(""))
	rhymes.setText("Enter a word above.");
      else {
	rhymes.setText("Searching...");
	aword = word.getText();
	rhymes.setText(doit());
      }
    }
    if (e.getActionCommand() == "clear") {
      rhymes.setText("");
      word.setText("");
    }
  }

  public String doit() {
    BufferedReader mh;
    String line;
    String therhyme = null;
    theresult = new StringBuffer("the word: " + aword + "\n");
    URL url;
    try {
    	InputStream in = getClass().getResourceAsStream("/modelEditor/distortions/rhyme/phondic.english.txt");
	      mh = new BufferedReader(new InputStreamReader(in));
    	//InputStream in = getClass().getResourceAsStream("/modelEditor/openNLP/tag.bin.gz");
     // mh = new BufferedReader(new InputStreamReader(url.openStream()));
      while (((line = mh.readLine()) != null) && (therhyme == null)) {
	 therhyme = this.mhp(line, aword);
      }
    }
    catch(Exception e) {System.out.println("fail 1");}
    theresult.append("the rhyme: " + therhyme + "\n");
    try {
    	InputStream in = getClass().getResourceAsStream("/modelEditor/distortions/rhyme/phondic.english.txt");
	      mh = new BufferedReader(new InputStreamReader(in));
      while ((line = mh.readLine()) != null) this.mhp2(line,therhyme);
    }
    catch (Exception e)
      {theresult.append("\nThe word is not\nin the dictionary\n" +
			  "or the dictionary\nis unavailable.");System.out.println("fail 2");}
    return theresult.toString();
  }

  String mhp(String astring, String string2) {
    StringTokenizer words = new StringTokenizer(astring);
    String[] word = new String[words.countTokens()];
    int i = 0;
    int stress = 0;
    while (words.hasMoreTokens()) {
      word[i] = words.nextToken();
      i++;
    }
    if (word[0].equals(string2)) {
      stress = word[2].indexOf("'");
      return word[2].substring(stress);
    }
    else { return null; }
  }

  void mhp2(String theline, String arhyme) {
    StringTokenizer words2 = new StringTokenizer(theline);
    String[] wordsinline = new String[words2.countTokens()];
    int i = 0;
    while (words2.hasMoreTokens()) {
      wordsinline[i] = words2.nextToken();
      i++;
    }
    if (wordsinline[2].endsWith(arhyme)) {
      theresult.append(wordsinline[0] + "\n");
    }
  }
}
