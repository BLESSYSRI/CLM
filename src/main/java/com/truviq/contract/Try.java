package com.truviq.contract;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;

public class Try implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub 
        String filePath = "C:\\Users\\devi\\Desktop\\Template.docx";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fileInputStream)) {
    
        	String Frontname =(String)execution.getVariable("City");
        	
            //replaceText(document, "client", "newWord1");
            //replaceText(document, "vendar", "newWord2");
            // replaceText(document, "NOTICES" , "sreeee");
            replaceText(document, "name",Frontname  );
            		

            try (FileOutputStream fileOutputStream = new FileOutputStream("D:\\modified_document.docx")) {
                document.write(fileOutputStream);
                System.out.println("Word document modified successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void replaceText(XWPFDocument document, String searchWord, String replaceWord) {
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                String text = run.getText(0);
                if (text != null && text.contains(searchWord)) {
                    int startIndex = text.indexOf(searchWord);
                    while (startIndex != -1) {
                        int endIndex = startIndex + searchWord.length();
                        text = text.substring(0, startIndex) + replaceWord + text.substring(endIndex);
                        run.setText(text, 0);
                        startIndex = text.indexOf(searchWord, startIndex + replaceWord.length());
                    }
                    // Break from the inner loop after replacing the word in the run
                    break;
                }
            }
        }
    }

}

