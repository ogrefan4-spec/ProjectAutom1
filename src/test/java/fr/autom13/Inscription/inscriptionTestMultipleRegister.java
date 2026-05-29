package fr.autom13.Inscription;

import fr.autom13.Inscription.POM.Accueil;
import fr.autom13.Inscription.POM.Connexion;
import fr.autom13.Inscription.POM.ExcelReader;
import fr.autom13.Inscription.POM.Inscription;
import fr.autom13.Inscription.POM.Membre;
import fr.autom13.Inscription.POM.MembreData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class inscriptionTestMultipleRegister {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String DEFAULT_EXCEL_PATH = "src/test/java/fr/autom13/Inscription/excel/membres.xlsx";
    private static final String URL = "http://localhost:8085/index.html";

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }


    /**
     * Test Paramétrable prenant toutes les lignes de l'Excel et effectue le parcours d'Inscription,
     * de Validation du membre et de connection pour verifier ces droits.
     * @param rowIndex
     * @throws InterruptedException
     */
    @ParameterizedTest
    @MethodSource("excelRowProvider")
    @DisplayName("Test With Excel")
    void testRegisterFromExcel(int rowIndex) throws InterruptedException {
        MembreData data = ExcelReader.readRow(rowIndex);
        Accueil accueil = new Accueil(driver,wait);
        Inscription inscription = accueil.goToRegister();

        String message = inscription
                .fillFromData(data)
                .submit();

        assertEquals("Votre inscription est désormais en attente de validation", message);
        Connexion connexion = accueil.goToConnexion();
        connexion.inputAdmin();
        accueil = connexion.pressConnexionButton();
        Membre membre = accueil.goToJardenners();
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        membre.validateMember(data.email());
    }

    /**
     * Test récupérant la ligne de l'Excel
     * @return
     * @throws IOException
     */
    static Stream<Integer> excelRowProvider() {
        return IntStream.range(0, ExcelReader.rowCount()).boxed();
    }

}
