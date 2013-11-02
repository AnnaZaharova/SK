
package CreateXML;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import javax.xml.transform.OutputKeys;

public class BuildXML {
    
	public static void buildXML(ArrayList<String> column, ArrayList<String> row, ArrayList<String> fixed) {
 
	  try {
 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Appledb");
                Attr path = doc.createAttribute("name");
                path.setValue("Apple");
                rootElement.setAttributeNode(path);
		doc.appendChild(rootElement);
 
		// Cells elements
		Element Cells = doc.createElement("Cells");
		rootElement.appendChild(Cells);

 
		// Dimension_by_row elements
		Element Dimension_by_Row = doc.createElement("Dimension_by_Row");
		Dimension_by_Row.appendChild(doc.createTextNode(row.get(0)));
		Cells.appendChild(Dimension_by_Row);
 
		// Dimension_by_Column elements
		Element Dimension_by_Column = doc.createElement("Dimension_by_Column");
		Dimension_by_Column.appendChild(doc.createTextNode(column.get(0)));
		Cells.appendChild(Dimension_by_Column);
 
		// Fixed elements
		Element Fixed = doc.createElement("Fixed");
		rootElement.appendChild(Fixed);
 
		// fixed dimension elements
		Element fixedDim = doc.createElement(fixed.get(0));
                fixedDim.appendChild(doc.createTextNode(fixed.get(1)));
                Fixed.appendChild(fixedDim);
                
                Element Selection = doc.createElement("Selection");
                rootElement.appendChild(Selection);
                
                Element firstDim = doc.createElement(row.get(0));
                firstDim.appendChild(doc.createTextNode(row.get(1)));
                Selection.appendChild(firstDim);
                
                Element secondDim = doc.createElement(column.get(0));
                secondDim.appendChild(doc.createTextNode(column.get(1)));
                Selection.appendChild(secondDim);
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("B:\\University\\AllProjects\\Netbeans\\Projects\\DemoSK\\"+fixed.get(0)+".xml"));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}
