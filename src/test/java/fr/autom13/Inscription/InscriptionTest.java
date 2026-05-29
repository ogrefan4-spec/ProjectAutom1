package fr.autom13.Inscription;

import fr.autom13.Inscription.POM.Accueil;
import fr.autom13.Inscription.POM.Connexion;
import fr.autom13.Inscription.POM.Inscription;
import fr.autom13.Inscription.POM.Membre;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static fr.autom13.Inscription.UtilsConn.getEmailFromExcel;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InscriptionTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String DEFAULT_EXCEL_PATH = "src/test/java/fr/autom13/Inscription/excel/membres.xlsx";
    private static final String URL = "http://localhost:8085/index.html";

    @BeforeAll
    static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(URL);
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("Test mouse Hover Menu")
    void testRegis() {
        Accueil accueil = new Accueil(driver, wait);
        Connexion conn = accueil.goToConnexion();
        conn.seConnecter("admin", "admin");
        accueil.goToJardenners();
    }

    @Test
    @Order(2)
    @DisplayName("REGISTER")
    void testRegister() throws InterruptedException {
        Accueil accueil = new Accueil(driver,wait);
        Inscription inscription = accueil.goToRegister();
        inscription.inputUserandPass("user1234", "user1234");
        inscription.inputNameplusGender("Patrick", "LAMBERT", "Masculin");
        inscription.inputDate("1979-03-10");
        inscription.inputAdresse("5 rue de la police", "Paris", "75002", "0666584512", "p.l@mail.com");
        inscription.inputRoleAndSkill("Propriétaire", "Débutant");
        Thread.sleep(3000);
        String rsult = inscription.submit();
        assertEquals("Votre inscription est désormais en attente de validation", rsult);
        Connexion connexion = accueil.goToConnexion();
        connexion.inputAdmin();
        accueil = connexion.pressConnexionButton();
        Membre membre = accueil.goToJardenners();
        Thread.sleep(3000);
        membre.validateMember("p.l@mail.com");
    }

    @ParameterizedTest
    @MethodSource("excelRowProvider")
    @DisplayName("Test With Excel")
    void testRegisterFromExcel(int rowIndex) throws InterruptedException {
        Accueil accueil = new Accueil(driver,wait);
        Inscription inscription = accueil.goToRegister();

        String email = getEmailFromExcel(rowIndex);
        String message = inscription
                .fillFromExcel(0)
                .submit();

        assertEquals("Votre inscription est désormais en attente de validation", message);
        Connexion connexion = accueil.goToConnexion();
        connexion.inputAdmin();
        accueil = connexion.pressConnexionButton();
        Membre membre = accueil.goToJardenners();
        Thread.sleep(3000);
        membre.validateMember(email);
    }

    static Stream<Integer> excelRowProvider() throws IOException {
        try (FileInputStream fis = new FileInputStream(DEFAULT_EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {
            int rowCount = workbook.getSheetAt(0).getLastRowNum();
            return IntStream.range(0, rowCount).boxed();
        }
    }

}
