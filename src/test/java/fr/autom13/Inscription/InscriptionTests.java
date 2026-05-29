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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    }

    @Test
    @Order(2)
    @DisplayName("JAR_ADB_CT10 - Modification valide de la fiche d'un Jardeenier")
    void testModificationValide() {
        //Inscription d'un compte Jardeenier
        MembreData data = ExcelReader.findByRole("JARDEENER");
        Accueil accueil = new Accueil(driver, wait);
        String messageInscription = accueil.goToRegister().fillFromData(data).submit();
        assertEquals("Votre inscription est désormais en attente de validation", messageInscription);

        // Connection Admin pour Valider l'inscription
        Connexion connexion = accueil.goToConnexion();
        connexion.inputAdmin();
        accueil = connexion.pressConnexionButton();
        Membre membre = accueil.goToJardenners();
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        membre.validateMember(data.email());

        // Modification du champ Email et Assert pour verifier la modification
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("experts")
        ));
        String originalEmail = data.email();
        String nouvelEmail = "modifie_" + originalEmail;
        membre.modifyMemberEmail(originalEmail, nouvelEmail).confirmModification(originalEmail);


        assertTrue(
                membre.isMemberEmailPresent(nouvelEmail),
                "Le nouvel email devrait être présent après modification"
        );
        assertFalse(
                membre.isMemberEmailPresent(originalEmail),
                "L'ancien email ne devrait plus être présent"
        );

    }

    @Test
    @Order(3)
    @DisplayName("JAR_ADB_CT11 - Supprimer le compte d’un Jardeenier ou Propriétaire")
    void testSupprimerCompteProprietaire() {
        //Compte Jardeenier venant de l'Excel
        MembreData data = ExcelReader.findByRole("JARDEENER");
        Accueil accueil = new Accueil(driver, wait);

        //Connection en tant qu'admin
        Connexion connexion = accueil.goToConnexion();
        connexion.inputAdmin();
        accueil = connexion.pressConnexionButton();
        Membre membre = accueil.goToJardenners();
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

        // Delete depuis le mail
        membre.deleteMember(data.email());
    }


}
