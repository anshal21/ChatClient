/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatoos_server;

import static chatoos_server.Chat_manager.cp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anshal Dwivedi
 */
public class Chat_package {
    
    public Chat_package(){
       
    }
    DataInputStream dis=null;
    DataOutputStream dos=null;
    ServerSocket ss=null;
    connect_db cdb=new connect_db();
    Socket s=null;
    public void initialize_connection(int port)
    {
        System.out.println("here we come to initailaize");  
      // connection_thread th=new connection_thread(port);
       //th.start();
          try {
            // System.out.println("here we are in thread");
              System.out.println("this side waiting on port "+port);
             ss=new ServerSocket(port);
             s=ss.accept();
             System.out.println("waiting");
             dos=new DataOutputStream(s.getOutputStream());
             dos.writeUTF("anshal this side");
             dis=new DataInputStream(s.getInputStream());
             start_listening();
             
        } catch (IOException ex) {
            Logger.getLogger(Chat_package.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void terminate_connection(){
        try {
            ss.close();
            s.close();
            dos.close();
            dis.close();
        } catch (IOException ex) {
            Logger.getLogger(Chat_package.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public class connection_thread extends Thread{
        int port;
        public connection_thread(int port){
            this.port=port;
        }
        public void run(){
         try {
             //System.out.println("here we are in thread");
             ss=new ServerSocket(port);
             s=ss.accept();
             dos=new DataOutputStream(s.getOutputStream());
             dis=new DataInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Chat_package.class.getName()).log(Level.SEVERE, null, ex);
        }}
    }
   
    public void start_listening(){
    Runnable runner=new Runnable() {

        @Override
        public void run() {
                while(s.isConnected())
                {
                    try {
                        String sender_un = null;
                        int rec_id = 0;
                        if(s.isConnected())
                        rec_id=dis.readInt();
                        if(rec_id==-1)
                        {
                            System.out.println("here we come to disconnect");
                            int i=dis.readInt();
                             ss.close();
                            s.close();
                            dis.close();
                            dos.close();
                            continue;
                            
                        }
                        else{
                          if(s.isConnected())
                            sender_un=dis.readUTF();
                        }
                         String msg = null;
                        if(s.isConnected())
                        msg=dis.readUTF();
                        if(rec_id==-12){
                            int id=0,l=0;
                            while(l<msg.length())
                            {
                                id=id*10+msg.charAt(l)-48;
                                l++;
                            }
                            System.out.println("here we come to add friend");
                            cdb.add_friend(sender_un,id);
                            continue;
                        }
                       
                        cp[rec_id].write_msg(msg,sender_un);
                        
                    } catch (IOException ex) {
                        try {
                            ss.close();
                            s.close();
                            dis.close();
                            dos.close();
                          //  Logger.getLogger(Chat_package.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex1) {
                            
                            Logger.getLogger(Chat_package.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
        }
    };
    Thread thr=new Thread(runner);
    thr.start();
    }
            public void write_msg(String msg,String sen_id){
        try {
            dos.writeUTF(sen_id);
            dos.writeUTF(msg);
        } catch (IOException ex) {
            Logger.getLogger(Chat_package.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
}
