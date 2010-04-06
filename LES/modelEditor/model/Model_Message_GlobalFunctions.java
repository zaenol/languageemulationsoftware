package modelEditor.model;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import NLP.BooleanObject;
import NLP.StringObject;

public class Model_Message_GlobalFunctions{
	
	Document dom = null;
	
	protected void init_Dom(){
		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();
	
			//create an instance of DOM
			dom = db.newDocument();
			
		}catch(ParserConfigurationException pce) {
			//dump it
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
			System.exit(1);
		}
	}
	
	protected Element makeElementWithIndextedChildren(String elementName, ArrayList<String>children){
		return this.makeElementWithIndextedChildren(elementName, children, "");
	}
	protected Element makeElementWithIndextedChildren(String elementName, ArrayList<String>children,String label){
		Element word_element = dom.createElement(elementName);
		
		for(int i=0; i<children.size();i++){
			word_element.appendChild(makeElementWithTextBody(label+i,children.get(i)));
		}
		
		return word_element;
	}
	
	
	protected Element makeElementWithTextBody(String elementName, String elementContent){
		Element word_element = dom.createElement(elementName);
		Text word_text = dom.createTextNode(elementContent);
		word_element.appendChild(word_text);		
		return word_element;
	}
	protected Element makeElementWithTextBody(String elementName, StringObject elementContent){
		return makeElementWithTextBody(elementName,elementContent.getValue()+"");
	}
	protected Element makeElementWithTextBody(String elementName, BooleanObject elementContent){
		return makeElementWithTextBody(elementName,elementContent.getValue());
	}
	protected Element makeElementWithTextBody(String elementName, boolean elementContent){
		return makeElementWithTextBody(elementName,elementContent+"");
	}
	
	protected void setElementAttribute(Element e, String attributeName, String attributeValue){
		e.setAttribute(attributeName, attributeValue);
	}
	protected void setElementAttribute(Element e, String attributeName, StringObject attributeValue){
		this.setElementAttribute(e, attributeName, attributeValue.getValue());
	}
	protected void setElementAttribute(Element e, String attributeName, BooleanObject attributeValue){
		this.setElementAttribute(e, attributeName, attributeValue.getValue());
	}
	protected void setElementAttribute(Element e, String attributeName, boolean attributeValue){
		this.setElementAttribute(e, attributeName, attributeValue+"");
	}

	
}
