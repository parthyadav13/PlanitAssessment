package reusableFunctions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadDataFromExcel {
// Methods to read data from excel
	public static ArrayList<String> ReadData(String testcase) throws IOException {

		ArrayList<String> data = new ArrayList<String>();
		FileInputStream fileInput = new FileInputStream(".\\src\\main\\java\\resources\\TestData.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(fileInput);

		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase("testdata")) {
				XSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rows = sheet.iterator();
				Row firstRow = rows.next();
				Iterator<Cell> cells = firstRow.cellIterator();
				int k = 0;
				int coloumn = 0;
				while (cells.hasNext()) {

					Cell value = cells.next();
					if (value.getStringCellValue().equalsIgnoreCase("TestCase")) {
						coloumn = k;
					}
					k++;
				}

				while (rows.hasNext()) {
					Row row = rows.next();
					if (row.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testcase)) {
						Iterator<Cell> cellValues = row.cellIterator();
						while (cellValues.hasNext()) {
							data.add(cellValues.next().getStringCellValue());
						}
					}

				}

			}
		}
		workbook.close();
		return data;
	}

	public List<List<Object>> ReadDataAll(String sheetName, String firstColoumn) throws IOException {

		List<List<Object>> listOfdata = new ArrayList<List<Object>>();
		ArrayList<Object> data = new ArrayList<Object>();
		FileInputStream fileInput = new FileInputStream(".\\src\\main\\java\\resources\\TestData.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(fileInput);

		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				XSSFSheet sheet = workbook.getSheetAt(i);

				Iterator<Row> rows = sheet.iterator();
				Row firstRow = rows.next();
				Iterator<Cell> cells = firstRow.cellIterator();
				int k = 0;
				int coloumn = 0;
				while (cells.hasNext()) {

					Cell value = cells.next();
					if (value.getStringCellValue().equalsIgnoreCase(firstColoumn)) {
						coloumn = k;
					}
					k++;
				}

				while (rows.hasNext()) {
					Row row = rows.next();
					Iterator<Cell> cellValues = row.cellIterator();
					while (cellValues.hasNext()) {
						Cell cell = cellValues.next();
						if (cell.getCellType() == CellType.STRING) {
							data.add(cell.getStringCellValue());
						} else {
							data.add(cell.getNumericCellValue());
						}

					}

					ArrayList<Object> copy = createCopy(data);
					listOfdata.add(copy);
					data.clear();

				}

			}
		}
		workbook.close();
		return listOfdata;
	}

	public static ArrayList<Object> createCopy(ArrayList<Object> orginal) {
		ArrayList<Object> copy = new ArrayList<Object>();
		for (Object s : orginal) {
			copy.add(s);
		}
		return copy;
	}

}
