package fr.autom13.Connexion;

import fr.autom13.Inscription.POM.Accueil;
import fr.autom13.Inscription.POM.Connexion;
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

    private static final String loginAdmin = "admin";
    private static final String passwordAdmin = "admin";

    private static final String loginJardeener = "admin";
    private static final String passwordJardeener = "admin";

    private static final String loginOwner = "admin";
    private static final String passwordOwner = "admin";

    /***
     * Méthode servant à l'initialisation du Webdriver avant l'exécution des différents tests
     */
    @BeforeAll
    static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(URL);
    }

    /***
     * Méthode servant à la cloture du Webdriver après l'exécution des différents tests
     */
    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }

    /***
     * Méthode permettant de retourner la classe connexion depuis la page d'accueil, simulant un clic
     * sur le bouton connexion depuis cette dernière.
     * @return l'objet Connexion utilisé pour effectuer une tentative de connexion.
     */
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

    /***
     * Cas de test vérifiant qu'il est possible de se connecter au site en tant qu'administrateur en utilisant :
     * login comme identifiant
     * password comme mot de passe
     *
     * Attente de la vue de la page d'accueil est assertion de visibilité de cette dernière
     */
    @Test
    @Order(1)
    public void testPassantAdmin() {
        Connexion connexionPage = goToConnexion();
        Accueil accueilPage = connexionPage.seConnecter(loginAdmin, passwordAdmin);

        wait.until(ExpectedConditions.visibilityOf(accueilPage.vueAccueil));
        assertTrue(accueilPage.estAffichee());

        // Déconnexion après authentification réussie
    }

    /***
     * Cas de test vérifiant qu'il est possible de se connecter au site en tant que Jardinier en utilisant :
     * login comme identifiant
     * password comme mot de passe
     *
     * Attente de la vue de la page d'accueil est assertion de visibilité de cette dernière
     */
    @Test
    @Order(2)
    public void testPassantJardinier() {
        Connexion connexionPage = goToConnexion();
        Accueil accueilPage = connexionPage.seConnecter(loginJardeener, passwordJardeener);

        wait.until(ExpectedConditions.visibilityOf(accueilPage.vueAccueil));
        assertTrue(accueilPage.estAffichee());

        // Déconnexion après authentification réussie
    }

    /***
     * Cas de test vérifiant qu'il est possible de se connecter au site en tant que Propriétaire en utilisant :
     * login comme identifiant
     * password comme mot de passe
     *
     * Attente de la vue de la page d'accueil est assertion de visibilité de cette dernière
     */
    @Test
    @Order(3)
    public void testPassantProprietaire() {
        Connexion connexionPage = goToConnexion();
        Accueil accueilPage = connexionPage.seConnecter(loginOwner, passwordOwner);

        wait.until(ExpectedConditions.visibilityOf(accueilPage.vueAccueil));
        assertTrue(accueilPage.estAffichee());

        // Déconnexion après authentification réussie
    }

    /***
     * Cas de test vérifiant qu'il n'est pas possible de se connecter avec des champs vides
     * Attente de la visibilité du message de connexion et que ce dernier spécifie :
     * Identifiant et/ou mot de passe incorrect(s).
     */
    @Test
    @Order(4)
    public void testNonPassantChampsVides(){
        Connexion connexionPage = goToConnexion();
        connexionPage.tenterConnexionInvalide("","");

        wait.until(ExpectedConditions.visibilityOf(connexionPage.getMessageConnexion()));
        assertTrue(connexionPage.getMessageErreur().contains("Identifiant et/ou mot de passe incorrect(s)."));
    }

    /***
     * Cas de test vérifiant qu'il n'est pas possible de se connecter sans renseigner de mot de passe
     * Attente de la visibilité du message de connexion et que ce dernier spécifie :
     * Identifiant et/ou mot de passe incorrect(s).
     */
    @Test
    @Order(5)
    public void testNonPassantMotDePasseVide(){
        Connexion connexionPage = goToConnexion();
        connexionPage.tenterConnexionInvalide(loginAdmin,"");

        wait.until(ExpectedConditions.visibilityOf(connexionPage.getMessageConnexion()));
        assertTrue(connexionPage.getMessageErreur().contains("Identifiant et/ou mot de passe incorrect(s)."));
    }

    /***
     * Cas de test vérifiant qu'il n'est pas possible de se connecter sans renseigner d'identifiant
     * Attente de la visibilité du message de connexion et que ce dernier spécifie :
     * Identifiant et/ou mot de passe incorrect(s).
     */
    @Test
    @Order(6)
    public void testNonPassantIdentifiantVide(){
        Connexion connexionPage = goToConnexion();
        connexionPage.tenterConnexionInvalide("", passwordAdmin);

        wait.until(ExpectedConditions.visibilityOf(connexionPage.getMessageConnexion()));
        assertTrue(connexionPage.getMessageErreur().contains("Identifiant et/ou mot de passe incorrect(s)."));
    }

    /***
     * Cas de test vérifiant qu'il n'est pas possible de se connecter en renseignant un mot de passe erroné
     * Attente de la visibilité du message de connexion et que ce dernier spécifie :
     * Identifiant et/ou mot de passe incorrect(s).
     */
    @Test
    @Order(7)
    public void testNonPassantMauvaisMotDePasse(){
        Connexion connexionPage = goToConnexion();
        connexionPage.tenterConnexionInvalide(loginAdmin, "JeSuisUnMauvaisMotDePasse");

        wait.until(ExpectedConditions.visibilityOf(connexionPage.getMessageConnexion()));
        assertTrue(connexionPage.getMessageErreur().contains("Identifiant et/ou mot de passe incorrect(s)."));
    }
}
