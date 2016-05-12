/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatoos_server;

/**
 *
 * @author Anshal Dwivedi
 */
public class Chatoos_Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       signup sp=new signup();
       sp.start();
       login_listner ll=new login_listner();
       ll.start();
    }
    
}
