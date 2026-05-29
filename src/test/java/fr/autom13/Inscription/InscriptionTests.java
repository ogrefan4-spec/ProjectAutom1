package fr.autom13.Inscription;

import fr.autom13.Inscription.POM.Accueil;
import fr.autom13.Inscription.POM.Connexion;
import fr.autom13.Inscription.POM.ExcelReader;
import fr.autom13.Inscription.POM.Inscription;
import fr.autom13.Inscription.POM.Membre;
import fr.autom13.Inscription.POM.MembreData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InscriptionTests {

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
    @DisplayName("JAR_ADB_CT07 - Inscription valide - Propriétaire")
    void testInscriptionValideProprietaire() {
        MembreData data = ExcelReader.findByRole("OWNER");

        Accueil accueil = new Accueil(driver, wait);
        String messageInscription = accueil.goToRegister().fillFromData(data).submit();
        assertEquals("Votre inscription est désormais en attente de validation", messageInscription);
        Connexion connexion = accueil.goToConnexion();
        connexion.inputAdmin();
        accueil = connexion.pressConnexionButton();
        Membre membre = accueil.goToJardenners();
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        membre.validateMember(data.email());
    }


}
