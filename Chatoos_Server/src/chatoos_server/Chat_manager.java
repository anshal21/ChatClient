/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatoos_server;

import java.net.ServerSocket;

/**
 *
 * @author Anshal Dwivedi
 */
public class Chat_manager {
   public static Chat_package[] cp=new Chat_package[100];
   
    public void manage_connection(int port,int id){
        cp[id]=new Chat_package();
      cp[id].initialize_connection(port);
        
    }
   
    
  
  
}
