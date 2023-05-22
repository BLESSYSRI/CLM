
package com.truviq.contract;

import javax.sql.rowset.serial.SerialBlob;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.awt.Desktop;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ToDBasBase64  implements JavaDelegate{
	@Override
	public void execute(DelegateExecution execution) throws ClassNotFoundException, SQLException, IOException {
	  Class.forName("com.mysql.jdbc.Driver");
	  int i=0;
	  Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/CLM","root","root");
	  java.io.File file =new java.io.File("C:\\Users\\sam\\Downloads\\Employment-Contract-Template-Download-20201125.pdf");
	  
	    byte[] pdfBytes =  Files.readAllBytes(Paths.get("C:\\Users\\sam\\Downloads\\Employment-Contract-Template-Download-20201125.pdf"));
       byte[] encoded=java.util.Base64.getEncoder().encode(pdfBytes);
       
  


       SerialBlob blob = new javax.sql.rowset.serial.SerialBlob(encoded);
       
     
       
       
       System.out.println(encoded);
       
       //code to push this encoded pdf to DataBase here
       String sql="Insert into CLM.DocFiles (BusinessKey,files) values (?,?);";
       PreparedStatement stmt=con.prepareStatement(sql);
       stmt.setString(1, execution.getVariable("ProcessBusinessKey").toString());
       stmt.setBlob(2, blob);
        i=  stmt.executeUpdate();
       System.out.println("Insertion status"+" :"+i);
       System.out.println("The Process Business Key is :"+execution.getVariable("ProcessBusinessKey"));
       System.out.println("Successful");
       //decoding and displaying , you can ignore this code 
       
       byte[] decoded=java.util.Base64.getDecoder().decode(encoded);
       FileOutputStream newFile =new FileOutputStream("C:\\Users\\sam\\Downloads\\Employment-Contract-Template-Download-20201125.pdf");
       newFile.write(decoded);
       newFile.flush();
       newFile.close();
	  
	  if(!Desktop.isDesktopSupported()) {
		  System.out.println("Desktop feauture is not supported");
		  
		  return;
	  }
	  Desktop desktop =Desktop.getDesktop();
	  //desktop.open(file);
	 	
	   
	  
}


}
