package com.insanity.webscrape;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrapePractoBangaloreDoctors {
	static Logger LOGGER = Logger.getLogger(ScrapePractoBangaloreDoctors.class
			.getName());
	public static final int NumberOfPages = 1640;

	private final static List<String> doctors = new ArrayList<String>();

	public static void main(String[] args) {
		for (int pageIndex = 0; pageIndex < NumberOfPages; pageIndex++) {
			findDoctors(pageIndex);
		}
	}

	private static String findDoctors(int page) {
		Document doc = null;
		String doctorCSV = "";
		try {
			doc = Jsoup
					.connect("https://www.practo.com/bangalore?page=" + page)
					.userAgent("Mozilla").get();
			String htmlContent = doc.body().html();
			saveHtmlFile(htmlContent, "" + page);
		} catch (IOException e) {
			LOGGER.fatal("Error accessing page : " + page, e);
		}
		if (doc != null) {
			LOGGER.info("Completed Processing Page  - " + page);
		}
		return doctorCSV;
	}

	public static void saveHtmlFile(String htmlContent, String saveLocation)
			throws IOException {
		FileWriter fileWriter = new FileWriter(saveLocation);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(htmlContent);
		bufferedWriter.close();
		LOGGER.info("Downloading completed successfully..!" + saveLocation);
	}

	public static void useBufferedFileWriter(List<String> content, File file) {

		// File file = new File(filePath);
		Writer fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);

			// Write the lines one by one
			for (String line : content) {
				line += System.getProperty("line.separator");
				bufferedWriter.write(line);
				// alternatively add bufferedWriter.newLine() to change line
			}

		} catch (IOException e) {
			LOGGER.fatal("Error writing the file : ", e);
		} finally {
			if (bufferedWriter != null && fileWriter != null) {
				try {
					bufferedWriter.close();
					fileWriter.close();
				} catch (IOException e) {
					LOGGER.fatal("Error closing the file : ", e);
				}
			}
		}

	}
}