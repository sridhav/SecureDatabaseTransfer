/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package privacydata;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sridhar
 */
public class ServerSock extends Thread {
  String serverName,portNo;
  ServerSocket serv;
  String arrEle[];
  MainWindow x=new MainWindow("ds");
  private String dbServerName,dbPortNo,dbName,dbUserName,dbPassword,dbTableName=null;
  public ServerSock(String port,String server,String dbServer,String dbPort, String dbName, String dbUser, String dbPass, String dbTable ) throws UnknownHostException, IOException{
   //   serverName=server;
      
      portNo=port;
      serverName=server;
      serv=new ServerSocket(Integer.parseInt(port)); 
      dbServerName=dbServer;
      dbPortNo=dbPort;
      this.dbName=dbName;
      dbUserName=dbUser;
      dbPassword=dbPass;
      dbTableName=dbTable;
      
  }  
  
  @Override
  public void run(){
      x.outputArea.append("Server Started with "+serv.getInetAddress()+":"+serv.getLocalPort());
      System.out.println("Waiting For Connection");
      try {
          try (Socket server = serv.accept()) {
              x.outputArea.append("Accepted connection from Client "+server.getRemoteSocketAddress());
              DataInputStream in=new DataInputStream(server.getInputStream());
              String inp=in.readUTF();
              StringTokenizer ss=new StringTokenizer(inp,"\n");
              arrEle=new String[ss.countTokens()];
              int i=0;
              x.outputArea.append("Recieving Data");
              while(ss.hasMoreElements()){
                  arrEle[i]=(String)ss.nextElement();
                  x.outputArea.append(arrEle[i]+"\n");
                  i++;
                  
              }
              System.out.println("Sending Data");
              
              String data=getData();
              x.outputArea.append(data);
              
              DataOutputStream out=new DataOutputStream(server.getOutputStream());
              x.outputArea.append("Data Sent");
              
          } catch (  SQLException | NoSuchAlgorithmException ex) {
              Logger.getLogger(ServerSock.class.getName()).log(Level.SEVERE, null, ex);
          }
      } catch (IOException ex) {
          Logger.getLogger(ServerSock.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  private String getData() throws SQLException, NoSuchAlgorithmException{
       // x.outputArea.append("Connecting to Local Database");
        DBConn x=new DBConn(dbServerName,dbPortNo,dbName,dbUserName,dbPassword,dbTableName);
        Connection conn=x.getConnection();
        String query="select * from "+dbTableName+";";
        ResultSet rs=x.execute(conn, query);
        String res="";
        while(rs.next()){
            String encryptVal=PrivacyData.encryptMD5(rs.getString(1));
             int noOfCols;
             //System.out.println(encryptVal);
            ResultSetMetaData rsmd=rs.getMetaData();
            noOfCols=rsmd.getColumnCount();
            for(int i=0;i<arrEle.length;i++){
                if(encryptVal.equals(arrEle[i])){
                    System.out.println("hellp");
                    for(int j=1;j<=noOfCols;j++){
                        res=res+rs.getString(j)+"\t";
                    }
                    res=res+"\n";
                    break;
                }
            }
        }
        return res;
  }
  
}
    
