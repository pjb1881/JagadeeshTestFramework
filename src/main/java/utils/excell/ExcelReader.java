package utils.excell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.hssf.usermodel.HSSFCell;


/**
 * @author Parimi Jagadeesh Bhaskar*
 * ExcelReader class. To Read the test data and element properties.
 */
public final class ExcelReader {

    /** Variable to store the excel file path. */
    private static ExcelReader reader;
    private File file;
    private Logger logger = LogManager.getLogger(ExcelReader.class);

    /**
     * Reads file path in constructor.
     * 
     * @param filePath
     * @throws FileNotFoundException
     */
    public ExcelReader(String filePath){
        file = new File(filePath);
    }
   // FileNotFoundException
   



	/**
     * Creates instance for ExcelReader and returns the instance.
     * 
     * @param filePath - Path of the file
     * @return ExcelReaderInstance
     */
    public static ExcelReader getInstance(String filePath) {
        try {
            reader = new ExcelReader(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;
    }



    /**
     * Get row value as Map for the given rownumber and sheet name.
     * 
     * @param rownum - Row number to be read
     * @param sheetName - Name of the sheet
     * @return rowvalue as Map
     */
    public Map<String, String> getRowValue(int rownum, String sheetName) {

        Map<String, String> rowData = null;
        try {
            rowData = getSheetData(rownum, sheetName);
        } catch (OfficeXmlFileException e) {
            System.out.println("Reading Xlsx file");
            rowData = getXlsxSheetData(1, rownum, sheetName);
        }

        if (!rowData.isEmpty()) {
            System.out.println(rowData + "re");
            return rowData;
        } else {

            throw new NullPointerException(rownum + " : doen't exist in " + sheetName + " sheet");
        }
    }

    /**
     * Get row value as Map for the given TestCase ID and sheet name.
     * 
     * @param tcID - TestCase ID
     * @param sheetName - Sheet Name to fetch data
     * @return Map
     */
    public Map<String, String> getRowValue(String tcID, String sheetName) {

        final Map<String, String> rowData = getSheetData(tcID, sheetName);
        if (!rowData.isEmpty()) {
            return rowData;
        } else {
            throw new NullPointerException(tcID + " : doen't exist in " + sheetName + " sheet");
        }
    }

    /**
     * @param sheetName - Name of the sheet
     * @param colHeader - header of the column
     * @return List of values in the column
     */
    public List<String> getColumnValue(String sheetName, String colHeader) {
        final List<String> colData = new ArrayList<String>();
        int colnum = 0;
        XSSFWorkbook workbook;
        XSSFSheet sheet = null;
        XSSFRow row;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheet(sheetName);
            for (int i = 0; i < sheet.getRow(1).getLastCellNum(); i++) {
                if (colHeader.equalsIgnoreCase(sheet.getRow(1).getCell(i).getStringCellValue())) {
                    colnum = i;
                    break;
                }
            }
            for (int j = 2; j < sheet.getLastRowNum() + 1; j++) {
                row = sheet.getRow(j);
                colData.add(row.getCell(colnum).getStringCellValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return colData;
    }

    /**
     * Returns number of rows in a given sheet.
     * 
     * @param sheetName - Name of the sheet
     * @return rownumbers
     */
    public int getRowCount(String sheetName) {

        final XSSFSheet sheet = getSheet(sheetName);
        final int rowNum = sheet.getLastRowNum();
        return rowNum;
    }

    /**
     * Returns number of columns in a given sheet.
     * 
     * @param sheetName - Name of the sheet
     * @return columnnumbers
     */
    public int getCoulmnCount(String sheetName) {

        final XSSFSheet sheet = getSheet(sheetName);
        final XSSFRow row = sheet.getRow(0);
        final int cellCount = row.getLastCellNum();
        return cellCount;
    }

    /**
     * Returns cell value for the given rownum, sheetName and column value.
     * 
     * @param rownum - Row number to be read
     * @param sheetName - Name of the sheet
     * @param column -Coulmn Name
     * @return cellvalue
     */
    public String getCellValue(int rownum, String sheetName, String column) {

        final Map<String, String> rowData = getSheetData(rownum, sheetName);
        if (!rowData.isEmpty()) {
            logger.info(" Value for :" + column + " is " + rowData.get(column));
            return rowData.get(column);
        } else {
            throw new NullPointerException(rownum + " : doen't exist in " + sheetName + " sheet");
        }
    }

    /**
     * Returns cell value for the given Testlink ID, sheetName and column value.
     * 
     * @param tcID - Testlink ID
     * @param sheetName - Sheet Name to read
     * @param column - Column name to fetch data
     * @return String
     */
    public String getCellValue(String tcID, String sheetName, String column) {

        final Map<String, String> rowData = getSheetData(tcID, sheetName);
        if (!rowData.isEmpty()) {
            //logger.info(" Value for :" + column + " is " + rowData.get(column));
            return rowData.get(column);
        } else {
            throw new NullPointerException(tcID + " : doen't exist in " + sheetName + " sheet");
        }
    }

    /**
     * Returns sheet name.
     * 
     * @param sheetName - Name of the sheet
     * @return sheetName
     */
    private XSSFSheet getSheet(String sheetName) {
        XSSFWorkbook workbook;
        XSSFSheet sheet = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheet(sheetName);
            // logger.info(" Data will be read from the sheet :" + sheetName);
            return sheet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }

    /**
     * @param sheetName sheet name
     * @param rowNo the row number to be removed
     */
    public void removeRow(String sheetName, int rowNo) {
        HSSFWorkbook workbook;
        HSSFSheet sheet = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheet(sheetName);
            final int lastRow = sheet.getLastRowNum();
            sheet.removeMergedRegion(0);
            sheet.shiftRows(1, lastRow, -1);
            final FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
            logger.info(" Data will be read from the sheet :" + sheetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns sheet name.
     * 
     * @param sheetName - Name of the sheet
     * @return sheetName
     */
    public XSSFSheet getXlsxSheet(String sheetName) {
        XSSFWorkbook workbook;
        XSSFSheet sheet = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheet(sheetName);
            // logger.info(" Data will be read from the sheet :" + sheetName);
            return sheet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }

    /**
     * @param sheetName sheet name
     * @param dataToWrite the data to be written in the sheet
     */
    public void insertRow(String sheetName, String[] dataToWrite) {
        HSSFWorkbook workbook;
        HSSFSheet sheet = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheet(sheetName);
            final int lastRow = sheet.getLastRowNum();
            final HSSFRow row1 = sheet.getRow(lastRow);
            final HSSFRow row = sheet.createRow(lastRow + 1);
            for (int j = 0; j < row1.getLastCellNum(); j++) {
                final Cell cell = row.createCell(j);
                cell.setCellValue(dataToWrite[j]);
            }
            final FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
            logger.info(" Data will be written to the sheet :" + sheetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sheetName sheet name
     * @return total no of rows in the sheet
     */
    public int rowCount(String sheetName) {
        HSSFWorkbook workbook;
        HSSFSheet sheet = null;
        int lastRow = 0;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheet(sheetName);
            lastRow = sheet.getLastRowNum();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastRow;
    }

    /**
     * @param sheetName the sheet name
     * @param value the value to be inserted
     * @param rowno the row number
     * @param colno the column number
     */
    public void putcellData(String sheetName, String value, int rowno, int colno) {
        HSSFWorkbook workbook;
        HSSFSheet sheet = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(file));
            sheet = workbook.getSheet(sheetName);
            Cell cell = null;
            cell = sheet.getRow(rowno).getCell(colno);
            cell.setCellValue(value);
            final FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
            logger.info(" Data will be written to the sheet :" + sheetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read and returns the sheet data as a Map.
     * 
     * @param rownum - Row number to be read
     * @param sheetName - Name of the sheet
     * @return sheet values as Map
     */
    private Map<String, String> getSheetData(int rownum, String sheetName) {
        final List<String> rowData = new ArrayList<String>();
        final Map<String, String> rowVal = new LinkedHashMap<String, String>();
        Object value = null;
        final XSSFSheet sheet = getSheet(sheetName);
        final List<String> coulmnNames = getColumns(sheet);
        final XSSFRow row = sheet.getRow(rownum);
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        for (int j = firstCellNum; j < lastCellNum; j++) {
            final XSSFCell cell = row.getCell(j);
            if (cell == null || cell.getCellType() == CellType.BLANK) {
                rowData.add("");
            } else if (cell.getCellType() == CellType.NUMERIC) {
                final Double val = cell.getNumericCellValue();
                value = val.intValue();// cell.getNumericCellValue();
                rowData.add(value.toString());
            } else if (cell.getCellType() == CellType.STRING) {
                rowData.add(cell.getStringCellValue());
            } else if (cell.getCellType() == CellType.BOOLEAN || cell.getCellType() == CellType.ERROR
                    || cell.getCellType() == CellType.FORMULA) {
                throw new RuntimeException(" Cell Type is not supported ");
            }
            rowVal.put(coulmnNames.get(j), rowData.get(j));
        }
        return rowVal;

    }

    /**
     * Read and returns the sheet data as a Map.
     * 
     * @param rownum - Row number to be read
     * @param sheetName - Name of the sheet
     * @return sheetvalues as Map
     */
    private Map<String, String> getXlsxSheetData(int headerRow, int rownum, String sheetName) {
        final List<String> rowData = new ArrayList<String>();
        final Map<String, String> rowVal = new LinkedHashMap<String, String>();
        Object value = null;
        final XSSFSheet sheet = getXlsxSheet(sheetName);
        final List<String> coulmnNames = getColumns(sheet, headerRow);
        final XSSFRow row = sheet.getRow(rownum);
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        for (int j = firstCellNum; j < lastCellNum; j++) {
            final XSSFCell cell = row.getCell(j);
            if (cell == null || cell.getCellType() == CellType.BLANK) {
                rowData.add("");
            } else if (cell.getCellType() == CellType.NUMERIC) {
                final Double val = cell.getNumericCellValue();
                value = val.intValue();// cell.getNumericCellValue();
                rowData.add(value.toString());
            } else if (cell.getCellType() == CellType.STRING) {
                rowData.add(cell.getStringCellValue());
            } else if (cell.getCellType() == CellType.BOOLEAN || cell.getCellType() == CellType.ERROR
                    || cell.getCellType() == CellType.FORMULA) {
                throw new RuntimeException(" Cell Type is not supported ");
            }
            rowVal.put(coulmnNames.get(j), rowData.get(j));
        }
        return rowVal;

    }

    /**
     * Read and returns the sheet data as a Map.
     * 
     * @param tcID - TestLink ID
     * @param sheetName - Sheet Name to fetch data
     * @return Map
     */
    private Map<String, String> getSheetData(String tcID, String sheetName) {
        final List<String> rowData = new ArrayList<String>();
        final Map<String, String> rowVal = new HashMap<String, String>();
        Object value = null;
        final XSSFSheet sheet = getSheet(sheetName);
        final List<String> coulmnNames = getColumns(sheet);
        final int totalRows = sheet.getPhysicalNumberOfRows();
        final XSSFRow row = sheet.getRow(0);
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        for (int i = 1; i < totalRows; i++) {
            final XSSFRow rows = sheet.getRow(i);
            // System.out.println(rows.getCell(0).getStringCellValue());
            final String testLinkID = rows.getCell(0).getStringCellValue();
            if (tcID.equalsIgnoreCase(testLinkID)) {
                for (int j = firstCellNum; j < lastCellNum; j++) {
                    final XSSFCell cell = rows.getCell(j);
                    if (cell == null || cell.getCellType() == CellType.BLANK) {
                        rowData.add("");
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        String val = cell.getRawValue();
                        rowData.add(val);
                    } else if (cell.getCellType() == CellType.STRING) {
                        rowData.add(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.BOOLEAN || cell.getCellType() == CellType.ERROR
                            || cell.getCellType() == CellType.FORMULA) {
                        throw new RuntimeException(" Cell Type is not supported ");
                    }
                    rowVal.put(coulmnNames.get(j), rowData.get(j).trim());
                }
                break;
            }
        }
        //logger.info(tcID + " Read from " + sheetName + " Succcessfully");
        return rowVal;

    }

    /**
     * Returns Column values from the given sheet.
     * 
     * @param sheet - sheet
     * @return columnvalues
     */
    private List<String> getColumns(XSSFSheet sheet) {
        final XSSFRow row = sheet.getRow(0);
        final List<String> columnValues = new ArrayList<String>();
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        for (int i = firstCellNum; i < lastCellNum; i++) {
            final XSSFCell cell = row.getCell(i);
            columnValues.add(cell.getStringCellValue());
        }
        return columnValues;
    }

    /**
     * Returns Column values from the given sheet.
     * 
     * @param sheet - sheet
     * @param headerRow - the row number of header
     * @return columnvalues
     */
    private List<String> getColumns(XSSFSheet sheet, int headerRow) {
        final XSSFRow row = sheet.getRow(headerRow);
        final List<String> columnValues = new ArrayList<String>();
        final int firstCellNum = row.getFirstCellNum();
        final int lastCellNum = row.getLastCellNum();
        for (int i = firstCellNum; i < lastCellNum; i++) {
            final XSSFCell cell = row.getCell(i);
            columnValues.add(cell.getStringCellValue());
        }
        return columnValues;
    }

    /**
     * Cell to string.
     * 
     * @param cell the cell
     * @return the string
     */
    public static String cellToString(HSSFCell cell) {
        // This function will convert an object of type excel cell to a string value
        CellType type = cell.getCellType();
        Object result;

        switch (type) {
            case NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case STRING:
                result = cell.getStringCellValue();
                break;
            case FORMULA:
                throw new RuntimeException("We can't evaluate formulas in Java");
            case BLANK:
                result = "";
                break;
            case BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case ERROR:
                throw new RuntimeException("This cell has an error");
            default:
                throw new RuntimeException("We don't support this cell type: " + type);
        }

        return result.toString();
    }


}

