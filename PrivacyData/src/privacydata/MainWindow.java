/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package privacydata;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

/**
 *
 * @author Sridhar
 */
public class MainWindow extends JFrame{
    
    JTextField hostInput=new JTextField();
    JTextField portInput=new JTextField();
    
    JTextField dbServerInput=new JTextField();
    JTextField dbPortInput=new JTextField();
    JTextField dbInput=new JTextField();
    JTextField dbUserInput=new JTextField();
    JTextField dbTableName=new JTextField();
    JPasswordField dbPassInput=new JPasswordField();
    JTextArea outputArea=new JTextArea();
    JTextField dbColName=new JTextField();
    JScrollPane scroll=new JScrollPane(outputArea);
    JButton submit=new JButton("Submit");
    JButton addDatabase=new JButton("Add Database Details");
    
    public MainWindow(String title){
        this.setTitle(title);
    }
    
    public void createWindow(){
        JPanel mainPan=new JPanel();
        JPanel serverPan=new JPanel();
        JPanel dataPan=new JPanel();
        
        BoxLayout box=new BoxLayout(mainPan,BoxLayout.PAGE_AXIS);
        
        String[] names={"Host Name","Port No","Host Name","Port No","Database","User Name","Password","Table Name","Col Name"};
        JLabel[] labels=new JLabel[names.length];
        for(int i=0;i<names.length;i++){
            labels[i]=new JLabel(names[i]);
        }
        mainPan.setLayout(box);
        serverPan.setLayout(new SpringLayout());
        dataPan.setLayout(new SpringLayout());
          mainPan.add(new JLabel(""));
      
        JLabel ser=new JLabel("Server");
        ser.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPan.add(ser);
        
        serverPan.add(labels[0]);
        serverPan.add(hostInput);
        hostInput.setText("127.0.0.1");
        
        
        
        serverPan.add(labels[1]);
        serverPan.add(portInput);
        portInput.setText("1243"
                + "");
        mainPan.add(serverPan);
        
        /////
        mainPan.add(new JLabel(""));
      
        JLabel db=new JLabel("Database");
        db.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPan.add(db);
        
        dataPan.add(labels[2]);
        dataPan.add(dbServerInput);
      //  dbServerInput.setText("grietdb.gokaraju.info");
        
        dataPan.add(labels[3]);
        dataPan.add(dbPortInput);
        dbPortInput.setText("3306");

        dataPan.add(labels[4]);
        dataPan.add(dbInput);
       // dbInput.setText("class_griet");
        
        dataPan.add(labels[5]);
        dataPan.add(dbUserInput);
        //dbUserInput.setText("cseadmin");
        
        dataPan.add(labels[6]);
        dataPan.add(dbPassInput); 
        
        dataPan.add(labels[7]);
        dataPan.add(dbTableName);
        //dbTableName.setText("med1");
        
        dataPan.add(labels[8]);
        dataPan.add(dbColName);
        //dbColName.setText("ssn");
        
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPan.add(dataPan);
         mainPan.add(new JLabel(""));
      
        
        dataPan.add(submit);
       dataPan.add(addDatabase);
        mainPan.add(new JLabel(""));
      
        
        
        JLabel out=new JLabel("Output");
        out.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPan.add(out);
        mainPan.add(outputArea);
        
        SpringUtilities.makeCompactGrid(serverPan, 2, 2, 10, 10, 10, 10);
        SpringUtilities.makeCompactGrid(dataPan, 8,2 ,10, 10, 10, 10);
        
        
        this.getContentPane().add(mainPan);
    }    
    
    
    public String getServerName(){
        return hostInput.getText();
    }
    
    public String getServerPort(){
        return portInput.getText();
    }
    
    public String getDbServer(){
        return dbServerInput.getText();
    }

    public String getDbPort(){
        return dbPortInput.getText();
    }
    
     public String getDbName(){
        return dbInput.getText();
    }
    
     public String getDbUser(){
        return dbUserInput.getText();
    }
    
    public String getDbPass(){
        return new String(dbPassInput.getPassword());
    }
    public String getDbTableName(){
        return dbTableName.getText();
    }
    
    public String getColName(){
        return dbColName.getText();
    }
}
