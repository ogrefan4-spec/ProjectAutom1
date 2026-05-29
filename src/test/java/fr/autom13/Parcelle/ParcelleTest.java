package fr.autom13.Parcelle;

import fr.autom13.Inscription.POM.Accueil;
import fr.autom13.Inscription.POM.Connexion;
import fr.autom13.Inscription.POM.Parcelle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ParcelleTest {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    private static final String URL = "http://localhost:8085/index.html";

    private static final String plotName = "Ma nouvelle parcelle";
    private static final String plotAdresse = "67 Rue de Montrouge";
    private static final String plotZipCode = "34090";
    private static final String plotCity = "Montpellier";
    private static final String validPlotWitdh = "15";
    private static final String validPlotLength = "34";

    private static final String invalidPlotWitdh = "-15";
    private static final String invalidPlotLength = "-34";

    private static final String errorMsgEmptyField = "Vous devez renseigner tous les champs.";
    private static final String errorMsgSize = "La parcelle doit faire au minimum 20m₂.";
    private static final String errorMsgNameAlreadyExisting = "Une parcelle existe déjà avec le nom";


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
     * Méthode permettant de retourner la classe parcelle depuis la page d'accueil, simulant un clic
     * sur le bouton parcelle depuis cette dernière.
     * @return l'objet Parcelle
     */
    private Parcelle goToParcelle()
    {
        return new Accueil(driver, wait).goToParcelle();
    }

    /***
     * Test de la création d'une parcelle valide.
     * Commence par se connecter en tant que propriétaire
     */
    @Test
    @Order(1)
    public void testCreationParcelleValide(){

        Parcelle parcellePage = new Accueil(driver,wait).goToConnexion()
                .seConnecter("proprietaire","proprietaire")
                .goToParcelle();

        wait.until(ExpectedConditions.visibilityOf(parcellePage.getVueAjoutParcelle()));
        assertTrue(parcellePage.estAffichee());

        parcellePage.ajouterParcelle(plotName,plotAdresse, plotZipCode, plotCity, validPlotWitdh, validPlotLength);
        assertTrue(parcellePage.estAffichee());
        assertFalse(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgEmptyField));
        assertFalse(parcellePage.getMessageAjoutParcelle().getText().contains("La parcelle doit faire au minimum 20m₂."));
    }

    /***
     * Test non passant de la création d'une parcelle avec un champ vide
     */
    @Test
    @Order(2)
    public void testCreationParcelleInvalideManqueInfo(){

        Parcelle parcellePage = new Parcelle(driver,wait);
        wait.until(ExpectedConditions.visibilityOf(parcellePage.getVueAjoutParcelle()));

        parcellePage.ajouterParcelle("","","",
                "","","");
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgEmptyField));

        parcellePage.ajouterParcelle(plotName,"","",
                "","","");
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgEmptyField));

        parcellePage.ajouterParcelle(plotName,plotAdresse,"",
                "","","");
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgEmptyField));

        parcellePage.ajouterParcelle(plotName,plotAdresse,plotZipCode,
                "","","");
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgEmptyField));

        parcellePage.ajouterParcelle(plotName,plotAdresse,plotZipCode,
                plotCity,"","");
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgEmptyField));

        parcellePage.ajouterParcelle(plotName,plotAdresse,plotZipCode,
                plotCity,validPlotWitdh,"");
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgEmptyField));

        parcellePage.ajouterParcelle(plotName,plotAdresse,plotZipCode,
                plotCity,"", validPlotLength);
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgEmptyField));
    }

    /***
     * Test non passant de la création d'une parcelle avec un problème sur sa taille (négative ou inférieure à 20m²)
     */
    @Test
    @Order(2)
    public void testCreerParcelleInvalideTaille() {

        Parcelle parcellePage = new Parcelle(driver,wait);
        wait.until(ExpectedConditions.visibilityOf(parcellePage.getVueAjoutParcelle()));

        parcellePage.ajouterParcelle(plotName,plotAdresse, plotZipCode,
                plotCity, "0", validPlotLength);
        wait.until(ExpectedConditions.visibilityOf(parcellePage.getMessageAjoutParcelle()));
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgSize));

        parcellePage.ajouterParcelle(plotName,plotAdresse, plotZipCode,
                plotCity, validPlotWitdh, "0");
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgSize));

        parcellePage.ajouterParcelle(plotName,plotAdresse, plotZipCode,
                plotCity, invalidPlotWitdh, validPlotLength);
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgSize));

        parcellePage.ajouterParcelle(plotName,plotAdresse, plotZipCode,
                plotCity, validPlotWitdh, invalidPlotLength);
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgSize));

    }

    /***
     * Test non passant de la création d'une parcelle avec un nom déjà existant
     * MANQUE LA DECONNEXION A AJOUTER
     */
    @Test
    @Order(5)
    public void testCreerParcelleInvalideNomExistant() {
        Parcelle parcellePage = new Parcelle(driver,wait);
        wait.until(ExpectedConditions.visibilityOf(parcellePage.getVueAjoutParcelle()));

        parcellePage.ajouterParcelle(plotName,plotAdresse, plotZipCode,
                "A", validPlotWitdh, validPlotLength);
        wait.until(ExpectedConditions.visibilityOf(parcellePage.getMessageAjoutParcelle()));
        assertTrue(parcellePage.getMessageAjoutParcelle().getText().contains(errorMsgNameAlreadyExisting));

        // SE DECONNECTER A FAIRE
    }

    /***
     * Test de la connexion en tant qu'administrateur et vérification que la liste des parcelles n'est pas vide
     * LOG À METTRE A JOUR
     */
    @Test
    @Order(4)
    public void testAffichageListDesParcellesAdmin() {

        Parcelle parcellePage = new Accueil(driver, wait).goToConnexion().
                seConnecter("admin","admin").
                goToParcelle();

        assertFalse(parcellePage.listeDesParcellesVisiblesEstVide());
    }

    /***
     * Test de la connexion en tant que Jardineer et vérification que la liste des parcelles n'est pas vide
     * LOG À METTRE A JOUR
     */
    @Test
    @Order(5)
    public void testAffichageListDesParcellesJardeener(){

        Parcelle parcellePage = new Accueil(driver, wait).goToConnexion().
                seConnecter("admin","admin").
                goToParcelle();

        assertFalse(parcellePage.listeDesParcellesVisiblesEstVide());
    }

    /***
     * Test de la connexion en tant que propriétaire et vérification que la liste des parcelles n'est pas vide
     * LOG À METTRE A JOUR
     */
    @Test
    @Order(6)
    public void testAffichageListDesParcellesProprietaire(){

        Parcelle parcellePage = new Accueil(driver, wait).goToConnexion().
                seConnecter("admin","admin").
                goToParcelle();

        assertFalse(parcellePage.listeDesParcellesVisiblesEstVide());
    }
}