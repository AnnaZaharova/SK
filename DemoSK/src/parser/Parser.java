
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
   
   
    //таблицы, содержащие результирующие атрибуты
    ArrayList<String> table  = new ArrayList<String>();
    
      //результирующие атрибуты
    ArrayList<String> attr = new ArrayList<String>();
    
        //ключи результирующих таблиц
    ArrayList<String> key  = new ArrayList<String>();
    
     //используется для хранение сечения(таблица, ключ)
    ArrayList<String> section  = new ArrayList<String>(2);
    
    //конкретные значения ключа сечения section
    ArrayList<String> rezult = new ArrayList<String>();
    
     //имя БД
   public String dbName = "";
   //результирующий зарос
   public String request;
   
   //дерево - результат работы парсера DOM
   Document document;
   
   public Parser(){}
   
   public  ArrayList<String> createDomParser(File file) {
       try{
       //Get the DOM Builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
       //Get DOM builder
            DocumentBuilder builder  = factory.newDocumentBuilder();
            
       //Load and Parse the XML document
       //document contains the complete XML as a Tree.
                getDomTree( builder,file);
       }
       catch(Exception e){
           System.out.println("Error : "+e.getClass().getName());
       }
       return attr;
   }
    private void getDomTree(DocumentBuilder builder,File file) throws IOException{
        try {
            document=builder.parse(file);  
        } catch (Exception e) {
            System.out.println("Error : "+e.getClass().getName());
        }
        Element root = document.getDocumentElement();
        dbName = root.getAttribute("path");
        NodeList nodelist = root.getChildNodes();
        for (int i = 1; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);
            if (node instanceof Element) {
                if(node.getNodeName().equals("Section")){
                    section.add( node.getChildNodes().item(1).getNodeName());
                    section.add(node.getChildNodes().item(1).getAttributes().getNamedItem("key").getNodeValue());
                    StringTokenizer st = new StringTokenizer(node.getChildNodes().item(1).getTextContent());
                    while (st.hasMoreElements()) {
                        rezult.add((String)st.nextElement());
                    }
                }
                if(node.getNodeName().equals("Result")){
                    NodeList nodelist1 = node.getChildNodes();
                    for (int j = 1; j < nodelist1.getLength(); j++) {
                        Node node1 = nodelist1.item(j);
                         if (node1 instanceof Element) {
                             table.add(node1.getNodeName());
                             attr.add(node1.getAttributes().getNamedItem("attr").getNodeValue());
                             key.add(node1.getAttributes().getNamedItem("key").getNodeValue());
                         }
                    }
                }
            }
        }
        getRezult();
    }
       
    private void getRezult(){
        request = "Select ";
        for (int i = 0; i< table.size(); i++) {
            request += table.get(i) +"."+attr.get(i) +" ";
            if(i+1!=table.size())
               request+=", ";
        }
        request +="from Sales ";
        for (int i = 0; i< table.size(); i++){  
                    if(!table.get(i).equals("Sales"))      
                request += " inner join "+table.get(i)+" on Sales."+key.get(i)+" = " + table.get(i)+"."+key.get(i)+" ";
        }
        request+=" inner join "+section.get(0)+" on Sales."+section.get(1)+" = " + section.get(0)+"."+section.get(1)+" ";
         request +="where "+section.get(0)+"."+section.get(1)+" IN (";
         for(int i = 0; i < rezult.size(); i++){
             request+= " '"+rezult.get(i)+"'";
             if(i+1 != rezult.size())
                 request+=",";
         }
         request+=" )";
    }
}