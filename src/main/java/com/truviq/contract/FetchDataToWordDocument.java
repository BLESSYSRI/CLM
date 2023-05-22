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

public class FetchDataToWordDocument implements JavaDelegate {

	 
	    public void execute(DelegateExecution execution) throws Exception {
        try {
            // Load the Word document template
            FileInputStream file = new FileInputStream("D:\\temp\\EmploymentContractTemplate.docx");
            XWPFDocument document = new XWPFDocument(file);

            // Fetch data from the UI
            String fetchedData = "Data fetched from UI";
            
            // Define text mappings
            HashMap<String, String> mappings = new HashMap<>();
            mappings.put("DATE","17");
            mappings.put("DAY","Wednesday");
            mappings.put("YEAR","2023");
            mappings.put("COMPANYNAME","Truviq Systems");
            mappings.put("EMPLOYEENAME","Divya");
            mappings.put("JOBTITTLE","Trainee");
            mappings.put("SALARY","10000");
            mappings.put("EMPLOYEESIGNATURE","divya");
            mappings.put("Edate","18-05-2023");
            mappings.put("COMPANYOFFICIALSIGNATURE","truviq");
            mappings.put("codat","19-05-2023");

            // Replace placeholders in the document with fetched data
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    for (String placeholder : mappings.keySet()) {
                        if (text!=null && text.contains(placeholder)) {
                            text = text.replace(placeholder, mappings.get(placeholder));
                            run.setText(text,0);
                            run.setBold(true); // Set text to bold
                        }
                    }
                }
            }

            // Save the modified document to a new file
            FileOutputStream out = new FileOutputStream("D:\\temp\\EmploymentContractTemplatenew.docx");
             document.write(out);
            out.close();
            document.close();

            System.out.println("Data successfully fetched and stored in the Word document.");
                   
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
