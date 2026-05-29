package fr.autom13.Inscription;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;

public class UtilsConn {
    private static final String DEFAULT_EXCEL_PATH = "src/test/java/fr/autom13/Inscription/excel/membres.xlsx";



    public static String cellValue(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        if (cell == null) return "";

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    public static void clearAndType(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    static String getEmailFromExcel(int rowIndex) {
        try (FileInputStream fis = new FileInputStream(DEFAULT_EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex + 1);

            if (row == null) {
                throw new IllegalArgumentException("Aucune donnée à la ligne " + rowIndex);
            }

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(row.getCell(10)).trim();

        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire l'email depuis l'Excel", e);
        }
    }
}
