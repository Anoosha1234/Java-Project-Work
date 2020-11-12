package shifty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import static shifty.Database.database1;

/**
 *
 * @author Anoosha Sajid
 */
public class Database {
    
    static Scanner Input = new Scanner(System.in);
    private  String username;
    private  String password;
    static Database database1 = new Database(); 
    private boolean flag = true;
    
    
    public String getUsername() { return username;}
    public String getPassword() { return password;}
    public boolean isFlag() { return flag;}
    public void setUsername(String username) { this.username = username;}
    public void  setPassword(String password) { this.password = password;}
    public void setFlag(boolean pointer) { this.flag = pointer;}

    public Database(String username, String password) { // constructor
        this.username = username;
        this.password = password;
    }

    public Database() {
    }
    
    
    public static void userLogin(String username, String password) throws SAXException, ParserConfigurationException, XPathExpressionException, TransformerException {
        
        database1.username = username;
        database1.password = password;
        
        try {
            File file = new File("Database.xml");
            //Read simple file into DOM-based Document class
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //Create new documentbuilder instance
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            Document xml = dBuilder.parse(file);
            //Eliminate garbage characters, corrects UTF coding errors
            xml.getDocumentElement().normalize(); 
            //List for storing data read from XML
            int Login = 0;
            
            
            NodeList nodes = xml.getElementsByTagName("Employee");
            for (Integer i = 0; i < nodes.getLength(); i++) {
                //Select actual node
                Node node = nodes.item(i);  
                //Cast node into editable element
                if (node.getNodeType() == Node.ELEMENT_NODE)   {  
                    Element eElement = (Element) node; 
                    //Cast node into editable element
                    if (eElement.getElementsByTagName("Username").item(0).getTextContent().equals(username) 
                        && eElement.getElementsByTagName("Password").item(0).getTextContent().equals(password)){ // if username and password from the main equal the database values
                            Login = 1;
                            Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // create timestamp
                            System.out.println("You logged in at " + timestamp);
                            String id = eElement.getAttribute("id");
                            String str = timestamp.toString();
                            SendToXml(str, id);  // call next method
                            database1.setFlag(false);  // change flag status
                            break;
                    }
                }
            }  
            
            if (Login == 0) {
            System.out.println("\nInvalid username and password combination. Please try again.");
            iteration(); // call next method
            }
            
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        
        
    }
    
    public static void iteration() throws SAXException {
		System.out.println("Press 0 to exit, 1 to try again:");
		if(Input.nextInt()== 0) {
                    database1.setFlag(false);} // exit program
    }
    
    public static void exitMethod() {
		System.out.println("Thank you for using shifty. Have a nice day!");
	}
    
    public static void SendToXml(String timestamp, String id) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerException{
        
        
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();  //New document builder
        DocumentBuilder b = f.newDocumentBuilder(); //Open simple file
        Document doc = b.parse(new File("WriteTo.xml"));  // read WriteTo.xml file
        Element newRecElement = doc.createElement("Employee"); //Create new element, its connected to record later
        Element LoginTime = doc.createElement("LoginTime");
        LoginTime.appendChild(doc.createTextNode(timestamp)); //Append new node as child from LoginTime property 
        Element Id = doc.createElement("EmployeeID");
        Id.appendChild(doc.createTextNode(id)); 
        newRecElement.appendChild(Id); //Connect record element to DOM of XML
        newRecElement.appendChild(LoginTime); //Connect record element to DOM of XML
        
        Element xmlRoot = (Element)doc.getFirstChild(); //Get root
        xmlRoot.appendChild(newRecElement); 
         try {
            Source source = new DOMSource(doc);
            File xmlFile = new File("WriteTo.xml"); // open file
            StreamResult result = new StreamResult(new OutputStreamWriter(
                                  new FileOutputStream(xmlFile), "ISO-8859-1")); // writing format
            Transformer xformer = TransformerFactory.newInstance().newTransformer(); //Finalize elements into class DOMSource
            xformer.transform(source, result); //Transforming DOM into file
        } catch(FileNotFoundException | UnsupportedEncodingException | TransformerException e) { //XML error handling, compulsory
            e.printStackTrace();
        }
         
         
    }
    
}