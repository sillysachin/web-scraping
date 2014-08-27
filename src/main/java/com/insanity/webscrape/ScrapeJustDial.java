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
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

public class ScrapeJustDial {
	private static final int NUMBER_OF_PAGES = 1;
	static Logger LOGGER = Logger.getLogger(ScrapeJustDial.class.getName());

	public static void main(String[] args) {

		for (int page = 0; page < NUMBER_OF_PAGES; page++) {
			scrapeOnline(page);
		}
	}

	private static void scrapeOnline(int justDialSearchParam) {
		Document doc = null;
		try {
			String justDialWapSearch = "http://wap.justdial.com/search.php?page="
					+ justDialSearchParam
					+ "&what=Hospitals&stype=category&city=Bangalore";
			Document searchDoc = Jsoup.connect(justDialWapSearch).get();
			Elements selects = searchDoc.select("a[class=comp_name_text]");
			for (Element element : selects) {
				String href = justDialWapSearch + element.attr("href");
				doc = Jsoup.connect(href).get();
				// String htmlContent = doc.body().html();
				// saveHtmlFile(htmlContent, "" + justDialSearchParam);
				final List<String> tags = new ArrayList<String>();
				if (doc != null) {
					doc.select("div[class=com_details]").traverse(new NodeVisitor() {
						public void tail(Node node, int depth) {
						}

						public void head(Node node, int depth) {
							if (depth >= 2) {
								String tagInfo = node.toString();
								tags.add(tagInfo);
							}
						}
					});
				}
			}
		} catch (IOException e) {
			LOGGER.fatal("Error accessing justdial search : ", e);
		}

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