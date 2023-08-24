package com.demo.html2pdf;

import java.io.IOException;

public class App {

	public static void main(String[] args)    {

		try {
			HtmlToPdf.ConvertingHtmlToPdf();
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
		
	  

	}
}
