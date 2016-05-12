/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatoos_server;

import static chatoos_server.connect_db.ret_name;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
public class login_listner extends Thread {
     Chat_manager cm=new Chat_manager();
     public static DataInputStream dis;
     public static DataOutputStream dos;
    public void run()
    {
        int chat_port=10024;
       
        connect_db cdb=new connect_db();
        try {
            while(true)
            {
                
            ServerSocket ss=new ServerSocket(31288);
            System.out.println("ohh waiting");
            Socket s=ss.accept();
            dis=new DataInputStream(s.getInputStream());
             dos=new DataOutputStream(s.getOutputStream());
            
            String username=dis.readUTF();
            String password=dis.readUTF();
            int rs=cdb.Select_query(username, password);
            String res=Integer.toString(rs);
            dos.writeUTF(res);
            if(!res.equals("0"))
            {
                thread_to tt=new thread_to(chat_port, rs);
                tt.start();
                
                dos.writeUTF(ret_name);
                dos.writeInt(chat_port);
               cdb.fetch_friend(rs);
                System.out.println("that was friend list");
                chat_port++;
            }
            
            ss.close();
            s.close();
            }  
        } catch (IOException ex) {
            Logger.getLogger(login_listner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public class thread_to extends Thread{
        int id,port;
        public thread_to(int port,int id)
        {
            this.id=id;
            this.port=port;
        }
        public void run(){
              cm.manage_connection(port,id);
        }
    }
}
