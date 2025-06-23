package tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import jakarta.mail.MessagingException;
import utils.ExcellAndMail;
import utils.excell.ExcelReader;
import utils.excell.ExcelUtils;

public class emailDemo {
	
	String MaildtailsExcellPath = "C:\\Users\\JagadeeshP\\Desktop\\Projects\\Maven_Testing_Projects\\2025_test_projects\\JagadeeshTestFramework\\src\\main\\java\\DataFolder\\mailDatails.xlsx";
	private ExcelReader MailData = new ExcelReader(MaildtailsExcellPath);
	
	public String sampleDoc = "C:\\Users\\jparimi\\Desktop\\Testing\\MavenProjects\\JagadeeshTestFramework\\src\\main\\resources\\utilityFiles\\123_test.csv";

	@Test
	public void sendMail() throws MessagingException, IOException {
		List<String> recipients = Arrays.asList("jparimi5@gmail.com", "nageswararaovallurupalli956@gmail.com",
				"amruthachowdary74328@gmail.com");
		
		String MailHsost = MailData.getCellValue("MailHost", "creds", "Value");
		String MailPort = MailData.getCellValue("MailPort", "creds", "Value");
		String MailUserName = MailData.getCellValue("MailUserName", "creds", "Value");
		String MailPassword = MailData.getCellValue("MailPassword", "creds", "Value");
		String MailSubject = "Welcome to Our Newsletter -1";
		String MailBodyMesssage = "Mail message Body here";
		String MailHtmlFilePath = null;
		String MailAttachmentFile = null;
		String MailOptions = "Daily";
		ExcellAndMail.sendEmailToRecipients(MailHsost, MailPort, MailUserName, MailPassword, recipients, MailSubject,
				MailBodyMesssage, MailHtmlFilePath, MailAttachmentFile, MailOptions);
	}

}
