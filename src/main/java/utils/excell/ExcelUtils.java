package utils.excell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.logging.Logs;

/**
 * The Class ExcelReader.
 * 
 * @author Parimi Jagadeesh Bhaskar
 */
public class ExcelUtils {
	private LinkedHashMap<String, String> linkedHashMap;
	public static Logs logsObj;
	public String path;
	public FileInputStream fileInputStream = null;
	public FileOutputStream fileOutputStream = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	/**
	 * Instantiates a new excel utils.
	 *
	 * @param path
	 *            the path
	 */
	public ExcelUtils(String path) {
		try {
			this.path = path;
			this.fileInputStream = new FileInputStream(path);
			this.workbook = new XSSFWorkbook(this.fileInputStream);
			this.sheet = this.workbook.getSheetAt(0);
			this.fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the row count.
	 *
	 * @param sheetName
	 *            the sheet name
	 * @return the row count
	 */
	public int getRowCount(String sheetName) {
		int index = this.workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		}
		this.sheet = this.workbook.getSheetAt(index);
		int number = this.sheet.getLastRowNum() + 1;
		return number;
	}

	/**
	 * Gets the cell data.
	 *
	 * @param sheetName
	 *            the sheet name
	 * @param colName
	 *            the col name
	 * @param rowNum
	 *            the row num
	 * @return the cell data
	 */
	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0) {
				return "";
			}
			int index = this.workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1) {
				return "";
			}
			this.sheet = this.workbook.getSheetAt(index);
			this.row = this.sheet.getRow(0);
			for (int i = 0; i < this.row.getLastCellNum(); ++i) {
				if (this.row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1) {
				return "";
			}
			this.sheet = this.workbook.getSheetAt(index);
			this.row = this.sheet.getRow(rowNum - 1);
			if (this.row == null)
				return "";
			this.cell = this.row.getCell(col_Num);

			if (this.cell == null) {
				return "";
			}
			if (this.cell.getCellType() == CellType.STRING) {
				return this.cell.getStringCellValue();
			}

			if (this.cell.getCellType() == CellType.FORMULA) {
				CellType typ = this.cell.getCachedFormulaResultType();
				if (typ == CellType.STRING) {
					XSSFRichTextString formulaText = this.cell.getRichStringCellValue();
					return formulaText.getString();
				}

				double formulaValue = this.cell.getNumericCellValue();
				return Double.toString(formulaValue);
			}

			if (this.cell.getCellType() == CellType.NUMERIC) {
				String cellText = String.valueOf(this.cell.getNumericCellValue());
				if (DateUtil.isCellDateFormatted(this.cell)) {
					double d = this.cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = String.valueOf(cal.get(1)).substring(2);
					cellText = cal.get(5) + "/" + cal.get(2) + 1 + "/" + cellText;
				}

				return cellText;
			}
			if (this.cell.getCellType() == CellType.BLANK) {
				return "";
			}
			return String.valueOf(this.cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "row " + rowNum + " or column " + colName + " does not exist in xls";
	}

	/**
	 * Gets the cell data.
	 *
	 * @param sheetName
	 *            the sheet name
	 * @param colNum
	 *            the col num
	 * @param rowNum
	 *            the row num
	 * @return the cell data
	 */
	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0) {
				return "";
			}
			int index = this.workbook.getSheetIndex(sheetName);

			if (index == -1) {
				return "";
			}

			this.sheet = this.workbook.getSheetAt(index);
			this.row = this.sheet.getRow(rowNum - 1);
			if (this.row == null)
				return "";
			this.cell = this.row.getCell(colNum);
			if (this.cell == null) {
				return "";
			}
			if (this.cell.getCellType() == CellType.STRING) {
				return this.cell.getStringCellValue();
			}

			if (this.cell.getCellType() == CellType.FORMULA) {
				CellType typ = (CellType) this.cell.getCachedFormulaResultType();
				if (typ == CellType.STRING) {
					XSSFRichTextString formulaText = this.cell.getRichStringCellValue();
					return formulaText.getString();
				}

				double formulaValue = this.cell.getNumericCellValue();
				return Double.toString(formulaValue);
			}

			if (this.cell.getCellType() == CellType.NUMERIC) {
				String cellText = String.valueOf(this.cell.getNumericCellValue());

				if (DateUtil.isCellDateFormatted(this.cell)) {
					double d = this.cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(d));
					cellText = String.valueOf(cal.get(1)).substring(2);
					cellText = (cal.get(2) + 1) + "/" + cal.get(5) + "/" + cellText;
				}

				return cellText;
			}
			if (this.cell.getCellType() == CellType.BLANK) {
				return "";
			}

			return String.valueOf(this.cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
	}

	/**
	 * Gets the cell row num.
	 *
	 * @param sheetName
	 *            the sheet name
	 * @param colName
	 *            the col name
	 * @param cellValue
	 *            the cell value
	 * @return the cell row num
	 */
	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		for (int i = 2; i <= getRowCount(sheetName); ++i) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks if is sheet exist.
	 *
	 * @param sheetName
	 *            the sheet name
	 * @return true, if is sheet exist
	 */
	public boolean isSheetExist(String sheetName) {
		int index = this.workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = this.workbook.getSheetIndex(sheetName.toUpperCase());

			return (index != -1);
		}
		return true;
	}

	/**
	 * Gets the column count.
	 *
	 * @param sheetName
	 *            the sheet name
	 * @return the column count
	 */
	public int getColumnCount(String sheetName) {
		if (!(isSheetExist(sheetName))) {
			return -1;
		}
		this.sheet = this.workbook.getSheet(sheetName);
		this.row = this.sheet.getRow(0);

		if (this.row == null) {
			return -1;
		}
		return this.row.getLastCellNum();
	}

	/**
	 * Fetch single sheet data.
	 *
	 * @param sheetName
	 *            the sheet name
	 * @param xls
	 *            the xls
	 * @return the object[][]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object[][] fetchSingleSheetData(String sheetName, ExcelUtils xls) {
		if (!(xls.isSheetExist(sheetName))) {
			xls = null;
			Object[][] data = new Object[1][1];
			data[0][0] = "No parameters Test";

			return data;
		}
		int rows = xls.getRowCount(sheetName);
		int cols = xls.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];
		Hashtable table = null;
		int index = 0;
		for (int rowNum = 2; rowNum <= rows; ++rowNum) {
			table = new Hashtable();
			for (int colNum = 0; colNum < cols; ++colNum) {
				String key = xls.getCellData(sheetName, colNum, 1);
				String value = xls.getCellData(sheetName, colNum, rowNum);
				table.put(key, value);
			}
			data[index][0] = table;
			++index;
		}
		return data;
	}

	/**
	 * Gets the rowdata from excel.
	 *
	 * @param xls
	 *            the xls
	 * @param sheetName
	 *            the sheet name
	 * @param row
	 *            the row
	 * @return the rowdata from excel
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Hashtable<String, String> getRowdataFromExcel(ExcelUtils xls, String sheetName, int row) {
		int generalSheetRow = row;
		Hashtable generalSheetData = new Hashtable();
		Object[][] obj_generalSheet = fetchSingleSheetData(sheetName, xls);

		if (generalSheetRow < obj_generalSheet.length) {
			generalSheetData = (Hashtable) obj_generalSheet[generalSheetRow][0];
		}

		return generalSheetData;
	}

	/**
	 * Read excel.
	 *
	 * @param filePath
	 *            the file path
	 * @param fileName
	 *            the file name
	 * @param sheetName
	 *            the sheet name
	 * @return the linked hash map
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public LinkedHashMap<String, String> readExcel(String filePath, String fileName, String sheetName)
			throws IOException {
		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		Workbook DriverWorkBook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		if (fileExtensionName.equals(".xlsx")) {
			DriverWorkBook = new XSSFWorkbook(inputStream);
		}
		else if (fileExtensionName.equals(".xls")) {
			DriverWorkBook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = DriverWorkBook.getSheet(sheetName);

		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		linkedHashMap = new LinkedHashMap<String, String>();
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = sheet.getRow(i);
			linkedHashMap.put(row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue());
		}
		return linkedHashMap;
	}
}
