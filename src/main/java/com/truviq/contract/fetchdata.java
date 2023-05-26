package com.truviq.contract;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class fetchdata implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
        try {
            // Load the Word document template
            FileInputStream file = new FileInputStream("D:\\temp\\Master-Service-Agreement-template-Software-Mind.docx");
            XWPFDocument document = new XWPFDocument(file);

            // Fetch data from the UI
            String date = (String) execution.getVariable("creation");
            String clientName = (String) execution.getVariable("Client");
            String vendorName = (String) execution.getVariable("Vendor");
            String regNo = (String) execution.getVariable("RegistrationNumber");
            String address = (String) execution.getVariable("Address");
            // Define text mappings
            HashMap<String, String> mappings = new HashMap<>();
            mappings.put("cname", clientName);
            mappings.put("contractintiationdate", date);
            mappings.put("vname", vendorName);
            mappings.put("regnum", regNo);
            mappings.put("address", address);

            // Replace placeholders in the document with fetched data
            /*for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    StringBuilder updatedText = new StringBuilder();

                    if (text != null) {
                        int startIndex = 0;
                        int placeholderIndex;

                        for (String placeholder : mappings.keySet()) {
                            while ((placeholderIndex = text.indexOf(placeholder, startIndex)) != -1) {
                                updatedText.append(text, startIndex, placeholderIndex);
                                updatedText.append(mappings.get(placeholder));
                                startIndex = placeholderIndex + placeholder.length();
                                run.setBold(true); // Set text to bold
                            }
                        

                        updatedText.append(text, startIndex, text.length());

                        // Set the updated text in the run
                        run.setText(updatedText.toString(), 0);
                        //run.setBold(true); // Set text to bold
                    }}
                }
            }*/
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    StringBuilder updatedText = new StringBuilder();

                    if (text != null) {
                        int startIndex = 0;
                        int placeholderIndex;

                        for (String placeholder : mappings.keySet()) {
                            while ((placeholderIndex = text.indexOf(placeholder, startIndex)) != -1) {
                                updatedText.append(text, startIndex, placeholderIndex);
                                updatedText.append(mappings.get(placeholder));
                                startIndex = placeholderIndex + placeholder.length();
                                run.setBold(true); // Set text to bold
                            }
                        }

                        updatedText.append(text, startIndex, text.length());

                        // Set the updated text in the run
                        run.setText(updatedText.toString(), 0);
                        updatedText.setLength(0); // Reset the StringBuilder object
                    }
                }
            }


            FileOutputStream out = new FileOutputStream("D:\\temp\\Truviq11.docx");
            document.write(out);
           out.close();
           document.close();
           System.out.println("Data successfully fetched and stored in the Word document.");
           
           
           
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

