/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatoos_server;

import com.sun.org.apache.bcel.internal.util.ByteSequence;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author Anshal Dwivedi
 */
public class signup extends Thread{
    public void run(){
        try {
            connect_db cdb=new connect_db();
            while(true)
            {
            ServerSocket ss=new ServerSocket(31289);
            System.out.println("waiting");
            Socket s=ss.accept();
            if(s.isConnected())
            {
                DataInputStream dis=new DataInputStream(s.getInputStream());
                String uname=dis.readUTF();
                String name=dis.readUTF();
                String password=dis.readUTF();
                 
                 cdb.Insert_Query(uname,name,password);
                 dis.close();
                 ss.close();
                 s.close();
              
            }
            }
        } catch (IOException ex) {
            Logger.getLogger(signup.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
