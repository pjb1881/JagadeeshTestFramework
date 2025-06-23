package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class ExcellAndMail {
	// -----Today's date------
	public static String getTodayDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.now().format(formatter);
	}

	// -------------------- Email File Utility --------------------

	public static void sendEmailToRecipients(String host, 
			                                 String port, 
			                                 final String userName, 
			                                 final String password,
			                                 List<String> toAddresses, 
			                                 String subject, 
			                                 String message, 
			                                 String htmlFilePath, 
			                                 String attachFile,
			                                 String options) throws MessagingException, IOException {

		// Common email session properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] recipientAddresses = new InternetAddress[toAddresses.size()];
		for (int i = 0; i < toAddresses.size(); i++) {
			recipientAddresses[i] = new InternetAddress(toAddresses.get(i));
		}
		msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		MimeBodyPart htmlPart = new MimeBodyPart();
		MimeBodyPart imagePart = null;
		Multipart multipart = new MimeMultipart();

		if ("Daily".equalsIgnoreCase(options) && htmlFilePath == null) {
			String htmlContent = dailyStatusMailHTML1(
					"C:\\Users\\JagadeeshP\\Desktop\\Projects\\Maven_Testing_Projects\\2025_test_projects\\JagadeeshTestFramework\\Sheets\\timeSheet.xlsx",
					"Sheet1", getTodayDate());
			htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
			multipart.addBodyPart(htmlPart);

			// Add image signature if applicable
			if (userName.contains("innominds")) {
				imagePart = new MimeBodyPart();
				DataSource imageSource = new FileDataSource(
						"C:\\Users\\JagadeeshP\\Desktop\\Projects\\Maven_Testing_Projects\\2025_test_projects\\JagadeeshTestFramework\\src\\main\\resources\\utilityFiles\\Mail\\imgs\\logo.png");
				imagePart.setDataHandler(new DataHandler(imageSource));
				imagePart.setHeader("Content-ID", "<image_cid>");
				imagePart.setDisposition(MimeBodyPart.INLINE);
				multipart.addBodyPart(imagePart);
			}

			// Optional attachment
			if (attachFile != null && !attachFile.isEmpty()) {
				MimeBodyPart attachPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachFile);
				attachPart.setDataHandler(new DataHandler(source));
				attachPart.setFileName(new File(attachFile).getName());
				multipart.addBodyPart(attachPart);
			}

			msg.setContent(multipart);
			Transport.send(msg);
			System.out.println("✅ Daily status email sent successfully to: " + toAddresses);
			return;
		}

		if ("template".equalsIgnoreCase(options) && htmlFilePath != null && !htmlFilePath.isEmpty()) {
			String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)), StandardCharsets.UTF_8);
			htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
			multipart.addBodyPart(htmlPart);

			// Optional image for template
			if (userName.contains("innominds")) {
				imagePart = new MimeBodyPart();
				DataSource imageSource = new FileDataSource(
						"C:/Users/jparimi/Desktop/Testing/MavenProjects/JagadeeshTestFramework/src/main/resources/utilityFiles/Mail/imgs/logo.png");
				imagePart.setDataHandler(new DataHandler(imageSource));
				imagePart.setHeader("Content-ID", "<image_cid>");
				imagePart.setDisposition(MimeBodyPart.INLINE);
				multipart.addBodyPart(imagePart);
			}

			// Optional attachment
			if (attachFile != null && !attachFile.isEmpty()) {
				MimeBodyPart attachPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachFile);
				attachPart.setDataHandler(new DataHandler(source));
				attachPart.setFileName(new File(attachFile).getName());
				multipart.addBodyPart(attachPart);
			}

			msg.setContent(multipart);
			Transport.send(msg);
			System.out.println("✅ Template email sent successfully to: " + toAddresses);
			return;
		}

		if ("textMessage".equalsIgnoreCase(options)) {
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(message);
			multipart.addBodyPart(textPart);

			// Optional attachment
			if (attachFile != null && !attachFile.isEmpty()) {
				MimeBodyPart attachPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachFile);
				attachPart.setDataHandler(new DataHandler(source));
				attachPart.setFileName(new File(attachFile).getName());
				multipart.addBodyPart(attachPart);
			}

			msg.setContent(multipart);
			Transport.send(msg);
			System.out.println("✅ Text email sent successfully to: " + toAddresses);
		}
	}



	// -------------------- Excel Methods --------------------

	public static void createExcelFile(String filePath, String sheetName, List<String> headers) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.size(); i++) {
			headerRow.createCell(i).setCellValue(headers.get(i));
		}
		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			workbook.write(fos);
		}
		workbook.close();
	}

	public static void writeExcelData(String filePath, String sheetName, List<List<String>> data, boolean append)
			throws IOException {
		File file = new File(filePath);
		Workbook workbook = file.exists() ? new XSSFWorkbook(new FileInputStream(file)) : new XSSFWorkbook();
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null)
			sheet = workbook.createSheet(sheetName);
		int startRow = append ? sheet.getLastRowNum() + 1 : 1;
		for (List<String> rowData : data) {
			Row row = sheet.createRow(startRow++);
			for (int i = 0; i < rowData.size(); i++) {
				row.createCell(i).setCellValue(rowData.get(i));
			}
		}
		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			workbook.write(fos);
		}
		workbook.close();
	}

	
	public static List<List<String>> readExcelData(String filePath, String sheetName) throws IOException {
		List<List<String>> data = new ArrayList<>();
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			for (Row row : sheet) {
				List<String> rowData = new ArrayList<>();
				for (Cell cell : row) {
					cell.setCellType(CellType.STRING);
					rowData.add(cell.getStringCellValue());
				}
				data.add(rowData);
			}
		}
		return data;
	}

	public static String getExcelCellData(String filePath, String sheetName, int rowIndex, int colIndex)
			throws IOException {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowIndex);
			if (row == null)
				return null;
			Cell cell = row.getCell(colIndex);
			if (cell == null)
				return null;
			cell.setCellType(CellType.STRING);
			return cell.getStringCellValue();
		}
	}

	public static void updateExcelCell(String filePath, String sheetName, int rowIndex, int colIndex, String newValue)
			throws IOException {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowIndex);
			if (row == null)
				row = sheet.createRow(rowIndex);
			Cell cell = row.getCell(colIndex);
			if (cell == null)
				cell = row.createCell(colIndex);
			cell.setCellValue(newValue);
			try (FileOutputStream fos = new FileOutputStream(filePath)) {
				workbook.write(fos);
			}
		}
	}

	public static int getColumnIndexByHeader(String filePath, String sheetName, String header) throws IOException {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			for (Cell cell : headerRow) {
				cell.setCellType(CellType.STRING);
				if (cell.getStringCellValue().equalsIgnoreCase(header)) {
					return cell.getColumnIndex();
				}
			}
		}
		return -1;
	}

	public static String getCellDataByRowMatch(String filePath, String sheetName, String matchHeader, String matchValue,
			String returnHeader) throws IOException {
		int matchCol = getColumnIndexByHeader(filePath, sheetName, matchHeader);
		int returnCol = getColumnIndexByHeader(filePath, sheetName, returnHeader);
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			for (Row row : sheet) {
				Cell matchCell = row.getCell(matchCol);
				if (matchCell != null) {
					matchCell.setCellType(CellType.STRING);
					if (matchCell.getStringCellValue().equalsIgnoreCase(matchValue)) {
						Cell returnCell = row.getCell(returnCol);
						if (returnCell != null) {
							returnCell.setCellType(CellType.STRING);
							return returnCell.getStringCellValue();
						}
					}
				}
			}
		}
		return null;
	}

	// -------------------- Additional Utilities --------------------

	public static int getRowCount(String filePath, String sheetName) throws IOException {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			return sheet.getLastRowNum();
		}
	}

	public static int getColumnCount(String filePath, String sheetName, int rowIndex) throws IOException {
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			Row row = sheet.getRow(rowIndex);
			return (row != null) ? row.getLastCellNum() : 0;
		}
	}

	public static List<String> getColumnValues(String filePath, String sheetName, String header) throws IOException {
		List<String> values = new ArrayList<>();
		int colIndex = getColumnIndexByHeader(filePath, sheetName, header);
		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					Cell cell = row.getCell(colIndex);
					if (cell != null) {
						cell.setCellType(CellType.STRING);
						values.add(cell.getStringCellValue());
					}
				}
			}
		}
		return values;
	}

	// -------------------- CSV Methods --------------------

	public static void createCSVFile(String filePath, List<String> headers) throws IOException {
		try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
			writer.writeNext(headers.toArray(new String[0]));
		}
	}

	public static void writeCSVData(String filePath, List<List<String>> data, boolean append) throws IOException {
		try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, append))) {
			for (List<String> row : data) {
				writer.writeNext(row.toArray(new String[0]));
			}
		}
	}

	public static List<List<String>> readCSVData(String filePath) throws IOException {
		List<List<String>> data = new ArrayList<>();
		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			String[] line;
			while ((line = reader.readNext()) != null) {
				data.add(Arrays.asList(line));
			}
		} catch (CsvValidationException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static String getCSVCellData(String filePath, int rowIndex, int colIndex) throws IOException {
		List<List<String>> data = readCSVData(filePath);
		if (rowIndex < data.size() && colIndex < data.get(rowIndex).size()) {
			return data.get(rowIndex).get(colIndex);
		}
		return null;
	}

	public static String getCSVCellDataByRowMatch(String filePath, String matchHeader, String matchValue,
			String returnHeader) throws IOException {
		List<List<String>> data = readCSVData(filePath);
		if (data.isEmpty())
			return null;
		List<String> headers = data.get(0);
		int matchCol = headers.indexOf(matchHeader);
		int returnCol = headers.indexOf(returnHeader);
		if (matchCol == -1 || returnCol == -1)
			return null;
		for (int i = 1; i < data.size(); i++) {
			if (data.get(i).get(matchCol).equalsIgnoreCase(matchValue)) {
				return data.get(i).get(returnCol);
			}
		}
		return null;
	}

	public static String dailyStatusMailHTML(String filePath, String sheetName, String dateFilter) throws IOException {
		StringBuilder html = new StringBuilder();
		html.append("<h3>🗓️ Daily QA Status - " + getTodayDate()).append(dateFilter).append("</h3>");
		html.append("<table border='1' cellpadding='6' cellspacing='0' style='border-collapse: collapse;'>");
		html.append(
				"<tr style='background-color:#f2f2f2;'><th>Date</th><th>Task</th><th>Status</th><th>Remarks</th></tr>");

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				return "<p>No sheet found: " + sheetName + "</p>";

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				Cell dateCell = row.getCell(0);
				String date = (dateCell != null) ? dateCell.toString().trim() : "";

				if (date.equals(dateFilter)) {
					html.append("<tr>");
					for (int j = 0; j < 4; j++) {
						Cell cell = row.getCell(j);
						String cellValue = (cell != null) ? cell.toString().trim() : "";
						html.append("<td>").append(cellValue).append("</td>");
					}
					html.append("</tr>");
				}
			}
		}

		html.append("</table>");
		return html.toString();
	}

	public static String dailyStatusMailHTML1(String filePath, String sheetName, String dateFilter) throws IOException {
		StringBuilder html = new StringBuilder();
		html.append("<h3>🗓️ Daily QA Status - ").append(dateFilter).append("</h3>");
		html.append("<table border='1' cellpadding='6' cellspacing='0' style='border-collapse: collapse;'>");
		html.append(
				"<tr style='background-color:#f2f2f2;'><th>Date</th><th>Task</th><th>Status</th><th>Remarks</th></tr>");

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null)
				return "<p>No sheet found: " + sheetName + "</p>";

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null)
					continue;

				Cell dateCell = row.getCell(0);
				String date = (dateCell != null) ? dateCell.toString().trim() : "";

				if (date.equals(dateFilter)) {
					html.append("<tr>");
					for (int j = 0; j < 4; j++) {
						Cell cell = row.getCell(j);
						String cellValue = (cell != null) ? cell.toString().trim() : "";
						html.append("<td>").append(cellValue).append("</td>");
					}
					html.append("</tr>");
				}
			}
		}

		html.append("</table>");

		// Add your email signature here
		html.append("<br><br>");
		html.append("<p style='font-family:Arial, sans-serif; font-size:14px;'>");
		html.append("Regards,<br>");
		html.append("<b>Jagadeesh Bhaskar Parimi</b><br>");
		html.append("QA Engineer | Innominds<br>");
		html.append("<a href='mailto:jagadeesh97014@gmail.com'>jagadeesh97014@gmail.com</a><br>");
		html.append("📞 +91-6309605699<br>");
		html.append("</p>");

		return html.toString();
	}

}
