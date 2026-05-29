package fr.autom13.Inscription.POM;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private static final String EXCEL_PATH = "src/test/java/fr/autom13/Inscription/excel/membres.xlsx";

    public static MembreData readRow(int rowIndex) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex + 1);

            if (row == null) {
                throw new IllegalArgumentException("Aucune donnée à la ligne " + rowIndex);
            }

            DataFormatter fmt = new DataFormatter();
            return new MembreData(
                    fmt.formatCellValue(row.getCell(0)).trim(),  // login
                    fmt.formatCellValue(row.getCell(1)).trim(),  // password
                    fmt.formatCellValue(row.getCell(2)).trim(),  // firstName
                    fmt.formatCellValue(row.getCell(3)).trim(),  // lastName
                    fmt.formatCellValue(row.getCell(4)).trim(),  // gender
                    fmt.formatCellValue(row.getCell(5)).trim(),  // birthdate
                    fmt.formatCellValue(row.getCell(6)).trim(),  // address
                    fmt.formatCellValue(row.getCell(7)).trim(),  // city
                    fmt.formatCellValue(row.getCell(8)).trim(),  // zipCode
                    fmt.formatCellValue(row.getCell(9)).trim(),  // phone
                    fmt.formatCellValue(row.getCell(10)).trim(), // email
                    fmt.formatCellValue(row.getCell(11)).trim(), // role
                    fmt.formatCellValue(row.getCell(12)).trim()  // skill
            );

        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire la ligne " + rowIndex, e);
        }
    }

    public static int rowCount() {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            return workbook.getSheetAt(0).getLastRowNum();
        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire le fichier Excel", e);
        }
    }

    public static MembreData findByRole(String role) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String rowRole = fmt.formatCellValue(row.getCell(12)).trim();
                if (rowRole.equalsIgnoreCase(role)) {
                    return readRow(i - 1);
                }
            }

            throw new IllegalArgumentException(
                    "Aucun membre avec le rôle '" + role + "' trouvé dans l'Excel"
            );

        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire le fichier Excel", e);
        }
    }
}
