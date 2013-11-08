
package parser;
import java.io.File;
import java.io.IOException;
import java.util. StringTokenizer;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {
    
        //имя измерения № 1 и его ключи
    ArrayList<String> dimension1  = new ArrayList<String>();
    
        //имя измерения № 2 и его ключи
    ArrayList<String> dimension2  = new ArrayList<String>();
    
        //имя фиксируемого измерения и его ключи
    ArrayList<String> fixed  = new ArrayList<String>(2);  
    
     //имя БД
   public String dbName = "";
   //результирующий зарос
   public String request;
   
   //дерево - результат работы парсера DOM
   Document document;
   
   public Parser(){}
   
   public  void createDomParser(File file) {
       try{
       //Get the DOM Builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
       //Get DOM builder
            DocumentBuilder builder  = factory.newDocumentBuilder();
            
       //Load and Parse the XML document
       //document contains the complete XML as a Tree.
                openXML( builder,file);
       }
       catch(Exception e){
           System.out.println("Error : "+e.getClass().getName());
       }
   }
    private void openXML(DocumentBuilder builder,File file) throws IOException{
        try {
            document = builder.parse(file);  
        } catch (Exception e) {
            System.out.println("Error : "+e.getClass().getName());
        }
        Element root = document.getDocumentElement();
        dbName = root.getAttribute("name");
        NodeList nodelist = root.getChildNodes();
        for (int i = 1; i < nodelist.getLength(); i++) {
                //Получение названия измерений
                if((nodelist.item(i).getNodeName()).equals("Cells")){
                        NodeList  dimNode = nodelist.item(i).getChildNodes();
                        for(int j = 0; j < dimNode.getLength(); j++){
                            if(dimNode.item(j).getNodeName().equals("Dimension_by_Row")){
                                Element element = (Element)dimNode.item(j);
                                dimension1.add(element.getTextContent());
 
                            }
                            if(dimNode.item(j).getNodeName().equals("Dimension_by_Column"))
                                dimension2.add(((Element)dimNode.item(j)).getTextContent());
                    }
                }
                //Получение информации об фиксируемом измерении
                if((nodelist.item(i).getNodeName()).equals("Fixed")){
                    NodeList fixedList = nodelist.item(i).getChildNodes();
                    for(int t = 0; t < fixedList.getLength(); t++){
                        if(fixedList.item(t) instanceof  Element){
                            fixed.add(fixedList.item(t).getNodeName());
                            fixed.add(((Element)(fixedList.item(t))).getTextContent());
                        }
                    }
                }
                
                //Получение ключей измерений
                if((nodelist.item(i).getNodeName()).equals("Selection")){
                    NodeList  dimNode = nodelist.item(i).getChildNodes();
                    for(int j = 0; j < dimNode.getLength(); j++){
                            if(dimNode.item(j).getNodeName().equals(dimension1.get(0))){
                                dimension1.add(((Element)dimNode.item(j)).getTextContent());
                                System.out.println(((Element)dimNode.item(j)).getTextContent());
                            }
                            if(dimNode.item(j).getNodeName().equals(dimension2.get(0)))
                                dimension2.add(((Element)dimNode.item(j)).getTextContent());
                            
                    }
                }
                
        }
    }
}