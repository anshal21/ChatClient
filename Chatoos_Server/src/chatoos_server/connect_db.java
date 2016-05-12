/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatoos_server;
import static chatoos_server.login_listner.dos;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Anshal Dwivedi
 */
public class connect_db {
    public static String ret_name=null;
    Connection conn=null;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_PATH = "jdbc:mysql://localhost/chatoos";
    String USER="root";
    String PASS="holidays98";
     public void Insert_Query (String uname,String name,String pass) {
         String query = "INSERT INTO chat_signup "
                    + "(name,usernmae,password) "
                    + "VALUES (\"" + uname
                    + "\",\"" + name
                    + "\",\"" + pass
                    + "\") ;";
        Connection con = null ;
        Statement st = null ;
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DB_PATH,USER,PASS);
            st = con.createStatement();
            st.executeUpdate(query);
            // Free up resources
            con.close();
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
     public int Select_query(String username,String pass)
     {
        try {
            Connection con=null;
            Statement st=null;
            ResultSet rs=null;
            Class.forName(JDBC_DRIVER);
            try {
                con=DriverManager.getConnection(DB_PATH,USER,PASS);
            } catch (SQLException ex) {
                Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                st=con.createStatement();
                String query="SELECT * FROM chat_signup WHERE usernmae = '"+username+"'";
                rs=st.executeQuery(query);
               
                if(rs.first())
                {
                    String password=rs.getString("password");
                   int id;
                   id=rs.getInt("id");
                   String idq=Integer.toString(id);
                  // id=rs.getString(id);
                    if(pass.equals(password))
                    {
                        System.out.println("detected ! Mr."+username);
                        ret_name=rs.getString("name");
                        return id;
                    }
                    else
                    {
                        System.out.println("you are a sinner\n");
                        return 0;
                    }
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
        }
    return 0;
     }
     public void fetch_friend(int id){
        try {
            Connection con=null;
            Statement st=null;
            Statement st2=null;
            
            ResultSet res;
            ResultSet res_2;
            Class.forName(JDBC_DRIVER);
            con=DriverManager.getConnection(DB_PATH,USER,PASS);
            st=con.createStatement();
            st2=con.createStatement();
            String query="SELECT * FROM friend_list WHERE id_1 = '"+id+"'";
            
            res=st.executeQuery(query);
            while(res.next())
            {
                int u=res.getInt("id_2");
               query="SELECT * FROM chat_signup WHERE id = '"+u+"'";
                res_2=st2.executeQuery(query);
                  while (res_2.next()) {                    
                      System.out.println(res_2.getString("name"));
               
               try {
                  
                    dos.writeInt(u);
                 dos.writeUTF(res_2.getString("usernmae"));
                } catch (IOException ex) {
                    Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
                }
                  }
                //System.out.println(u);
                
            }
            try {
                dos.writeInt(0);
                dos.writeUTF("212121");
            } catch (IOException ex) {
                Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
     public void add_friend(String uname,int id){
         Connection con=null;
         Statement st=null,st2=null;
         ResultSet rs1,rs2;
        try {
            Class.forName(JDBC_DRIVER);
            con=DriverManager.getConnection(DB_PATH,USER,PASS);
            st=con.createStatement();
            st2=con.createStatement();
            String query="SELECT * FROM chat_signup WHERE usernmae = '"+uname+"'";
                rs1=st.executeQuery(query);
                int y = 0;
            while (rs1.next()) {                
                 y=rs1.getInt("id");
            }
        
            System.out.println(id+" to update");
            query = "INSERT INTO friend_list "
                    + "(id_1,id_2) "
                    + "VALUES (\"" + id
                    + "\",\"" + y
                    + "\") ;";
            st2.executeUpdate(query);
          query = "INSERT INTO friend_list "
                    + "(id_1,id_2) "
                    + "VALUES (\"" + y
                    + "\",\"" + id
                    + "\") ;";
            st2.executeUpdate(query);
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
}
