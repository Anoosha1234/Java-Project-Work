package shifty;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anoosha Sajid
 */
public class Database {
    
    static Scanner Input = new Scanner(System.in);
    static  ArrayList <Database> User = new ArrayList<>();
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

    public Database(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Database() {
    }
    
    
    public static void userLogin(String username, String password) throws SAXException {
        
        database1.username = username;
        database1.password = password;
        
        try {
            File file = new File("Database.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            Document xml = dBuilder.parse(file);
            xml.getDocumentElement().normalize(); 
            int Login = 0;
            
            
            NodeList nodes = xml.getElementsByTagName("Employee");
            for (Integer i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);  
                if (node.getNodeType() == Node.ELEMENT_NODE)   {  
                    Element eElement = (Element) node; 
                    if (eElement.getElementsByTagName("Username").item(0).getTextContent().equals(username) 
                        && eElement.getElementsByTagName("Password").item(0).getTextContent().equals(password)){
                            Login = 1;
                            System.out.println("Login successful");
                            database1.setFlag(false);
                            break;
                    }
                }
            }  
            
            if (Login == 0) {
            System.out.println("\nInvalid username and password combination. Please try again.");
            iteration();
            }
            
        } catch (IOException e) {
            e.printStackTrace(); 
        }
        
        
    }
    
    public static void iteration() throws SAXException {
		System.out.println("Press 0 to exit, 1 to try again:");
		if(Input.nextInt()== 0) {
                    database1.setFlag(false);}
    }
    
    public static void exitMethod() {
		System.out.println("Thank you for using shifty. Have a nice day!");
	}
    
}
