package fr.autom13.Inscription;

import fr.autom13.Inscription.POM.Accueil;
import fr.autom13.Inscription.POM.Connexion;
import fr.autom13.Inscription.POM.Inscription;
import fr.autom13.Inscription.POM.Membre;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InscriptionTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
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
        conn.inputUserAndPass("admin", "admin");
        accueil = conn.pressConnexionButton();
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

    @Test
    @Order(3)
    @DisplayName("Test With Excel")
    void testRegisterFromExcel() throws InterruptedException {
        Accueil accueil = new Accueil(driver,wait);
        Inscription inscription = accueil.goToRegister();

        String message = inscription
                .fillFromExcel(0)   // lit la ligne 0 (première ligne de données)
                .submit();

        assertEquals("Votre inscription est désormais en attente de validation", message);
        Connexion connexion = accueil.goToConnexion();
        connexion.inputAdmin();
        accueil = connexion.pressConnexionButton();
        Membre membre = accueil.goToJardenners();
        Thread.sleep(3000);
        membre.validateMember("pierrot77@gmail.com");
    }

}
