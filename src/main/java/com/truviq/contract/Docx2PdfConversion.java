package com.truviq.contract;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
public class Docx2PdfConversion implements JavaDelegate{
	
	

	public void execute(DelegateExecution execution) throws Exception {
		try { InputStream is = new FileInputStream(new File("D:\\temp\\EmploymentContractTemplatenew.docx"));
				OutputStream out = new FileOutputStream(new File("D:\\temp\\EmploymentContractTemplatenew.pdf"));
			long start = System.currentTimeMillis();
			// 1) Load DOCX into XWPFDocument
			XWPFDocument document = new XWPFDocument(is);
			// 2) Prepare Pdf options
			PdfOptions options = PdfOptions.create();
			// 3) Convert XWPFDocument to Pdf
			PdfConverter.getInstance().convert(document, out, options);
			System.out.println("D:\\temp\\EmploymentContractTemplate.docx was converted to a PDF file in :: "
					+ (System.currentTimeMillis() - start) + " milli seconds");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}