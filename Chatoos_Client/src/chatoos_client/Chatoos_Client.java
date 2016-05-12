/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatoos_client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Anshal Dwivedi
 */
public class Chatoos_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     login_panel lpp=new login_panel();
     lpp.setVisible(true);
     lpp.setLocationRelativeTo(null);
    }
    
}
