package com.truviq.contract;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
public class Storedoc2Pdf implements JavaDelegate{
	public void execute(DelegateExecution execution) throws Exception {
        String filename = "D:\\temp\\Truviq2.docx"; // Replace with the name of your PDF file
        String base64Content = encodePDFToBase64(filename);
        storePDFInDatabase(filename, base64Content);
    }

    private static String encodePDFToBase64(String filename) {
        String base64Content = "";
        try {
            File file = new File(filename);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            inputStream.read(bytes);
            base64Content = Base64.getEncoder().encodeToString(bytes);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Content;
    }

    private static void storePDFInDatabase(String filename, String base64Content) {
        String myConnectionString = "jdbc:mysql://localhost:3306/template";
        String username = "root";
        String password = "root";
        Connection dbConnection = null;
        PreparedStatement ps = null;

        try {
            dbConnection = DriverManager.getConnection(myConnectionString, username, password);
            ps = dbConnection.prepareStatement("INSERT INTO storetemplate (filename, pdf_file) VALUES (?, ?)");
            ps.setString(1, filename);
            ps.setString(2, base64Content);
            ps.executeUpdate();
            System.out.println("PDF file stored in database.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


