package com.demo.html2pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.select.Elements;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class HtmlToPdf {

	// Converting Html Page to Pdf
	public static void ConvertingHtmlToPdf() throws IOException {

		Document document = modificationOnHtmlFile();

		document.outputSettings().syntax(Syntax.xml);
		try (FileOutputStream oStream = new FileOutputStream("C:/Users/DELL/Downloads/ITN.pdf")) {
			ITextRenderer renderer = new ITextRenderer();
			SharedContext cntContext = renderer.getSharedContext();
			cntContext.setPrint(true);
			cntContext.setInteractive(false);
			String baseString = FileSystems.getDefault().getPath("C:/Users/DELL/Documents").toUri().toURL().toString();
			renderer.setDocumentFromString(document.html(), baseString);
			renderer.layout();

			renderer.createPDF(oStream);
			System.out.print("Done");
		}

		catch (Exception e) {
			System.out.print("Error" + e.getMessage());
		}
	}

	// Modifying Html Document using jsoup
	private static Document modificationOnHtmlFile() throws IOException {

		File htmlfile = new File("C:/Users/DELL/Desktop/Room1.html");

		Document document = Jsoup.parse(htmlfile, "UTF-8");
		
		
		
		Element removeAgent = document.selectFirst("table");

		if (removeAgent != null) {

			Elements rows = removeAgent.select("tr");

			int targetRow = 1; // Row 2
			int targetColumn = 2; // Column 2

			Element tdElement = rows.get(targetRow).select("td").get(targetColumn);

			targetRow = 1;
			targetColumn = 1;

			Element tdElement2 = rows.get(targetRow).select("td").get(targetColumn);
			tdElement.remove();
			tdElement2.remove();

			// System.out.println(tdElement.text());
		}

//		Element passengerDetailsElement = document.selectFirst("td");
//
//		if (passengerDetailsElement != null) {
//			passengerDetailsElement.text("GUEST & STAY DETAILS");
//		}
		
		
		Element removePassengerDetails = document.select("table#voucherPanel").first();
		if(removePassengerDetails!=null) {
			for(Element tr:removePassengerDetails.select("tr")) {
				Element passenderDetailElement=tr.selectFirst("td");
				if(passenderDetailElement.text().equals("PASSENGER DETAILS")) {
					passenderDetailElement.text("GUEST & STAY DETAILS");
				}
			}
		}
		
		
		

		Element table = document.select("table#passengerDetails").first();

		if (table != null) {
			for (Element tr : table.select("tr")) {

				Element GuestName = tr.selectFirst("td");
				Element PassengerNationality = tr.selectFirst("td");
				if (GuestName.text().equals("Passenger Name")) {
					GuestName.text("Guest Name");
				}
				if (PassengerNationality.text().equals("Passenger Nationality")) {
					PassengerNationality.text("Guest Nationality");
				}
			}
		}

		Element tables2 = document.select("table#voucherGeneralDetails").first();

		for (Element tr : tables2.select("tr")) {

			Element image = tr.selectFirst("img");
			if (image != null) {
				image.attr("src",
						"https://mcusercontent.com/b9b38543e81e56f3d1e9fc377/_thumbs/a605dd62-c37f-590a-dc11-bbbbf4ad29f1.png");
			}

		}

		for (Element tr : tables2.select("tr")) {
			Element itinerayNumber = tr.selectFirst("td");
			Element referenceNumber = tr.selectFirst("td");
			if (itinerayNumber.text().equals("Itinerary Number")) {
				tr.remove();
			}
			if (referenceNumber.text().equals("Reference Number")) {
				tr.remove();
			}

		}

		Elements strongElement = document.select("strong");
		for (Element strong : strongElement) {

			if (strong.text().equals("Operations Team dummyvendor FZ LLC")) {
				strong.text("Unravel Support");
			}
			
		}

		Element anchorElement = document.selectFirst("a");
		if (anchorElement != null) {
			anchorElement.attr("href", "support@gounravel.com");
			anchorElement.text("support@gounravel.com");
		}
		
		return document;
	}

}
