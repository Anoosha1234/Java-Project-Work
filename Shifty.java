/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shifty;

import java.util.Scanner;
import org.xml.sax.SAXException;

/**
 *
 * @author Anoosha Sajid
 */
public class Shifty {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SAXException {
        
        
        Scanner Input = new Scanner(System.in);
        String Username = null;
        String Password = null;
        Database database1 = new Database(Username, Password);

       while(Database.database1.isPointer()){
       System.out.println("Enter Username");
       Username = Input.next();
       System.out.println("Enter Password");
       Password = Input.next();

       Database.userLogin(Username, Password);
    }
    Database.exitMethod();
        
}
}
