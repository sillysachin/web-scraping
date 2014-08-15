package com.insanity.webscrape;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ScrapePractoHospitals {
	static Logger LOGGER = Logger.getLogger(ScrapePractoHospitals.class.getName());
	private static int end;
	private static int next;
	private static final int REQUEST_PER_HOUR = 2000;
	private final static int TOTAL_NUMBER_OF_DOCTORS = 1006366;
	private final static int MILLISECONDS_IN_HOUR = 3600000;
	private static final String CONST_COMMA = ",";
	private final static List<String> doctors = new ArrayList<String>();
	static File doctorcsv = null;

	public static void main(String[] args) {
		if (args.length > 0) {
			String filename = args[0];
			doctorcsv = new File(filename);
		} else {
			doctorcsv = new File("doctors.csv");
		}
		try {
			doctorcsv.createNewFile();
		} catch (IOException e) {
			LOGGER.error("Error creating the file : ", e);
		}

		int start = 0;
		if (args.length > 1) {
			start = Integer.valueOf(args[1]);
		} else {
			start = 372000;
		}

		int end = 0;
		if (args.length > 2) {
			end = Integer.valueOf(args[2]);
		} else {
			end = TOTAL_NUMBER_OF_DOCTORS;
		}

		int sleep = 0;
		if (args.length > 3) {
			sleep = Integer.valueOf(args[3]);
		} else {
			sleep = MILLISECONDS_IN_HOUR;
		}

		Writer fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			fileWriter = new FileWriter(doctorcsv);
			bufferedWriter = new BufferedWriter(fileWriter);
			scrapeOnline(start, end, sleep, bufferedWriter);
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

	private static void scrapeOnline(int start, int end, int sleep,
			BufferedWriter bufferedWriter) throws IOException {

		int sleepCounter = 0;
		int sleepTicker = 0;

		for (int i = start; i <= end; i++) {
			sleepCounter++;
			LOGGER.info("FindDoctor - " + i);
			String doctorInfo = findDoctor(i);
			bufferedWriter.write(doctorInfo
					+ System.getProperty("line.separator"));
			doctors.add(doctorInfo);
			if (sleepCounter == 4000) {
				sleepTicker++;
				LOGGER.info("Sleep Ticker - " + sleepTicker);
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					LOGGER.fatal("Error processing the thread in sleep.", e);
				}
			}
		}

	}

	private static String findDoctor(int id) {
		Document doc = null;
		String doctorCSV = "";
		try {
			doc = Jsoup.connect(
					"http://www.mciindia.org/ViewDetails.aspx?ID=" + id).get();
			String htmlContent = doc.body().html();
			saveHtmlFile(htmlContent, "" + id);
		} catch (IOException e) {
			LOGGER.error("Error accessing doctor with id : " + id);
		}
		if (doc != null) {
			String name = doc.select("span[id=Name]").text();
			String fatherName = doc.select("span[id=FatherName]").text();
			String dob = doc.select("span[id=DOB]").text();
			String yearOfInfo = doc.select("span[id=lbl_Info]").text();
			String regisNo = doc.select("span[id=Regis_no]").text();
			String dateReg = doc.select("span[id=Date_Reg]").text();
			String council = doc.select("span[id=Lbl_Council]").text();
			String qualification_0 = doc.select("span[id=Qual]").text();
			String qualificationYear_0 = doc.select("span[id=QualYear]").text();
			String university_0 = doc.select("span[id=Univ]").text();
			String address = doc.select("span[id=Address]").text();
			String qualification_1 = doc.select("span[id=AddQual1]").text();
			String qualificationYear_1 = doc.select("span[id=AddQualYear1]")
					.text();
			String university_1 = doc.select("span[id=AddQualUniv1]").text();
			String qualification_2 = doc.select("span[id=AddQual2]").text();
			String qualificationYear_2 = doc.select("span[id=AddQualYear2]")
					.text();
			String university_2 = doc.select("span[id=AddQualUniv2]").text();
			String qualification_3 = doc.select("span[id=AddQual3]").text();
			String qualificationYear_3 = doc.select("span[id=AddQualYear3]")
					.text();
			String university_3 = doc.select("span[id=AddQualUniv3]").text();

			doctorCSV = new Date() + CONST_COMMA + id + CONST_COMMA + name
					+ CONST_COMMA + fatherName + CONST_COMMA + dob
					+ CONST_COMMA + yearOfInfo + CONST_COMMA + regisNo
					+ CONST_COMMA + dateReg + CONST_COMMA + council
					+ CONST_COMMA + qualification_0 + CONST_COMMA
					+ qualificationYear_0 + CONST_COMMA + university_0
					+ CONST_COMMA + address + CONST_COMMA + qualification_1
					+ CONST_COMMA + qualificationYear_1 + CONST_COMMA
					+ university_1 + CONST_COMMA + qualification_2
					+ CONST_COMMA + qualificationYear_2 + CONST_COMMA
					+ university_2 + qualification_3 + CONST_COMMA
					+ qualificationYear_3 + CONST_COMMA + university_3;
			LOGGER.info("Completed Processing Doctor with Name - " + name);

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