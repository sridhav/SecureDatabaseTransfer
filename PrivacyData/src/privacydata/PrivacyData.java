/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package privacydata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Sridhar
 */
public class PrivacyData {

    /**
     * @param args the command line arguments
     */
    private static String serverName,portNo,dbServerName,dbPortNo,dbName,dbUserName,dbPassword,dbTableName,colName=null;
   private static MainWindow win;
    private static ServerSock m=null;
    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO code application logic here
        
        
       
        
        win=new MainWindow("User");
        win.createWindow();
        win.setVisible(true);
        win.pack();
        win.setSize(300,700);
        win.setResizable(false);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        win.submit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               //To change body of generated methods, choose Tools | Templates.
               serverName=win.getServerName();
               portNo=win.getServerPort();
               dbServerName=win.getDbServer();
               dbPortNo=win.getDbPort();
               dbName=win.getDbName();
               dbUserName=win.getDbUser();
               dbPassword=win.getDbPass();
               dbTableName=win.getDbTableName();
               colName=win.getColName();
                try {
                    connectToServer();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(PrivacyData.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PrivacyData.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(PrivacyData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
       win.addDatabase.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                //To change body of generated methods, choose Tools | Templates.
               dbServerName=win.getDbServer();
               dbPortNo=win.getDbPort();
               dbName=win.getDbName();
               dbUserName=win.getDbUser();
               dbPassword=win.getDbPass();
               dbTableName=win.getDbTableName();
               portNo=win.getServerPort();
               serverName=win.getServerName();
               colName=win.getColName();
               JOptionPane.showMessageDialog(win,"Database Details are added");
            }
           
       });
        
        
    }
    private static void connectToServer() throws UnknownHostException, IOException, SQLException, NoSuchAlgorithmException{
        try (Socket client = new Socket(serverName, Integer.parseInt(portNo))){
            OutputStream outToServer = client.getOutputStream();
             DataOutputStream out =
                           new DataOutputStream(outToServer);
             win.outputArea.append("Sending data .... \n");
             
             String data=getData();
             
             out.writeUTF(data);
             win.outputArea.append(data);
             win.outputArea.append("\nRecieving data .... \n");
             
             InputStream inFromServer = client.getInputStream();
             DataInputStream in =
                            new DataInputStream(inFromServer);
             String retdata=in.readUTF();
             System.out.println(retdata);
             win.outputArea.append(retdata+"\n");
             in.close();
             out.close();
        }
        
    }
    private static String getData() throws SQLException, NoSuchAlgorithmException{
        win.outputArea.append("Connecting to Database "+dbServerName+":"+dbPortNo+"\n");
        DBConn x=new DBConn(dbServerName,dbPortNo,dbName,dbUserName,dbPassword,dbTableName);
        Connection conn=x.getConnection();
        String query="select "+colName+" from "+dbTableName+";";
        ResultSet rs=x.execute(conn, query);
        String res="";
        while(rs.next()){
            String encryptVal=encryptMD5(rs.getString(1));
            res=res+encryptVal+"\n";
            //System.out.println(encryptVal);
        }
        return res;
    }
    
    public static String encryptMD5(String val) throws NoSuchAlgorithmException{
        
        MessageDigest md=MessageDigest.getInstance("MD5");
        md.update(val.getBytes());
        byte s[]=md.digest();
        String result="";
        for (int i = 0; i < s.length; i++) {
          result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
        }
        
   //    System.out.println(result);
        
        return result;
    }
}
