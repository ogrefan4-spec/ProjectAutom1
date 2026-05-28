package fr.autom13.test;

import fr.autom13.POM.Accueil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class AccueilTest extends BaseTest {

    /*
        1-Visiteur
            Accès à l'accueil
                Info sur l'asso

        2-Connecté
            Accès à l'accueil
                Information
                Infos sur les tâches à venir
            Présence de l'header
     */

    @Test
    @Order(1)
    public void testAccesAccueil_Visiteur() {
        Accueil accueilPage = new Accueil(driver, wait);

    }

    @Test
    @Order(2)
    public void testInformationAsso_Visiteur() {
        Accueil accueilPage = new Accueil(driver, wait);

        assertTrue(accueilPage.informationIsDisplayed());
    }

    @Test
    @Order(3)
    public void testParcelleAsso_Visiteur() {
        Accueil accueilPage = new Accueil(driver, wait);
        assertTrue(accueilPage.plotIsDisplayed());
    }

    @Test
    @Order(4)
    public void testAccesAccueil_Admin() {
        Accueil accueilPage = new Accueil(driver, wait);

        assertTrue(accueilPage.informationIsDisplayed());
    }

    @Test
    @Order(5)
    public void testInformationAsso_Admin() {
        Accueil accueilPage = new Accueil(driver, wait);

        assertTrue(accueilPage.informationIsDisplayed());
    }

    @Test
    @Order(6)
    public void testUnstaffedTasksDisplayed_Visiteur() {
        Accueil accueilPage = new Accueil(driver, wait);

        assertTrue(accueilPage.unstaffedTasksIsDisplayed());
    }

    @Test
    @Order(7)
    public void testUnstaffedImminentTasksIsDisplayedDisplayed_Visiteur() {
        Accueil accueilPage = new Accueil(driver, wait);

        assertTrue(accueilPage.unstaffedImminentTasksIsDisplayed());
    }
}
