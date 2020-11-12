package shifty;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Anoosha Sajid
 */


public class ArrayOutput {
    
    private String id;
    private String timestamp;
    static ArrayOutput output1 = new ArrayOutput();

    public String getId() { return id;}
    public String getTimestamp() { return timestamp;}
    public void setId(String id) { this.id = id;}
    public void setTimestamp(String timestamp) { this.timestamp = timestamp;}

    public ArrayOutput() {
    }

    public ArrayOutput(String id, String timestamp) { //constructor
        this.id = id;
        this.timestamp = timestamp;
    }
    
    
    static ArrayList<ArrayOutput> readProducts() throws XMLStreamException
    {
    ArrayList<ArrayOutput> products = new ArrayList<ArrayOutput>(); // new arraylist instance
    ArrayOutput p = null;
    XMLInputFactory inputFactory = XMLInputFactory.newFactory();
    try
    {
        //create a stream reader object
        FileReader fileReader = new FileReader("WriteTo.xml");
        XMLStreamReader reader = inputFactory.createXMLStreamReader(fileReader); 
        //read XML file
        while (reader.hasNext())  // Read values from xml
        {
          int eventType = reader.getEventType(); 
          switch (eventType)
          {
               case  XMLStreamConstants.START_ELEMENT :
                  String elementName = reader.getLocalName();
                  if (elementName.equals("Employee"))
                  {
                     p = new ArrayOutput(); // Start Arraylist
                  }  
                  if (elementName.equals("EmployeeID"))
                  {
                     String id = reader.getElementText();  //Read child node
                     p.output1.setId(id); // Import it into Array
                  }    
                  if (elementName.equals("LoginTime"))
                  {
                     String timestamp = reader.getElementText();
                     p.output1.setTimestamp(timestamp);
                  }   
                  break;
               
                case XMLStreamConstants.END_ELEMENT :
                  elementName = reader.getLocalName();
                  if(elementName.equals("Employee"))  // When the XML file ends, end arraylist
                  {
                    products.add(p); // Finish ArrayList
                  }
                  break; 
              }
         reader.next();
        }    
    }
    catch (IOException e) // //Error handling, compulsory
    {
      System.out.println(e); 
    }    
     return products;
    }

}