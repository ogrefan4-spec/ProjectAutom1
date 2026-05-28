package fr.autom13.Connexion;

import fr.autom13.POM.Accueil;
import fr.autom13.POM.Connexion;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConnexionTest {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    private static final String URL = "http://localhost:8085/index.html";


    @BeforeAll
    static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(URL);
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }

    private Connexion goToConnexion()
    {
       return new Accueil(driver, wait).goToConnexion();
    }

    /*
    Test Passant Admin
    Test Passant Jardeener
    Test Passant Proprio
    Test Non Passant champs vides
    Test Non Passant PassWord vide
    Test Non Passant Login Vide
    Test Non Passant Login Valide mais mauvais password
     */

    @Test
    @Order(1)
    public void testPassant() {
        Connexion connexionPage = goToConnexion();
        Accueil accueilPage = connexionPage.seConnecter(login, password);

        wait.until(ExpectedConditions.visibilityOf(accueilPage.vueAcceuil));
        assertTrue(accueilPage.estAffichee);
    }
}
