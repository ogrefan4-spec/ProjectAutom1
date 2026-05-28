package fr.autom13.Inscription.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Connexion {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy (id = "connectionForm")
    private WebElement vueConnexion;

    @FindBy (id = "loginInput")
    private WebElement champLogin;

<<<<<<< HEAD:src/test/java/fr/autom13/Inscription/POM/Connexion.java
    @FindBy(css = "nav .active")
    private WebElement CONN;
=======
    @FindBy (id = "passwordInput")
    private WebElement champPwd;
>>>>>>> 438de879a33d7c45e2f1dfa1bb76ee3084c3ee0a:src/test/java/fr/autom13/POM/Connexion.java

    @FindBy (id = "connectionButton")
    private WebElement btnConnection;

    @FindBy (id = "messageLabel")
    private WebElement msgConnexion;

    /***
     * Constucteur paramétré de la classe Connexion
     * @param driver correspond au webdriver
     * @param wait correspond à l'attente explicite
     */
    public Connexion(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(vueConnexion));
    }

<<<<<<< HEAD:src/test/java/fr/autom13/Inscription/POM/Connexion.java
    public Connexion inputUserAndPass(String user, String psw) {
        wait.until(ExpectedConditions.elementToBeClickable(USER));
        USER.clear();
        USER.sendKeys(user);
        wait.until(driver ->
                USER.getAttribute("value").equals(user));

        wait.until(ExpectedConditions.elementToBeClickable(PSW));
        PSW.clear();
        PSW.sendKeys(psw);
        wait.until(driver ->
                PSW.getAttribute("value").equals(psw));
        return this;
=======
    /***
     * Méthode permettant de contrôler la visibilité du formulaire.
     * @return un booléen indiquant si le formulaire de connexion est visible
     */
    public boolean estAffichee() {
        return vueConnexion.isDisplayed();
>>>>>>> 438de879a33d7c45e2f1dfa1bb76ee3084c3ee0a:src/test/java/fr/autom13/POM/Connexion.java
    }

    /***
     * Méthode permettant de contrôler la visibilité du message de connexion
     * @return un booléen indiquant si le message de connexion est visible.
     */
    public boolean messageConnexionEstAffiche(){return msgConnexion.isDisplayed();}

    /***
     * Méthode permettant de récupérer le WebElement du message de connexion.
     * Permet par exemple d'effectuer une attente explicite sur sa visibilité depuis l'extérieur
     * @return le WebElement contenant le message de connexion
     */
    public WebElement getMessageConnexion(){return msgConnexion;}

    /***
     * Méthode permettant de contrôler le contenu du message de connexion
     * @return le String correspondant au message affiché
     */
    public String getMessageErreur(){return msgConnexion.getText();}

    /***
     * Se connecte avec les identifiants fournis.
     * Retourne le TableauDeBordPage si la connexion réussit.
     */
    public Accueil seConnecter(String login, String password) {
        champLogin.clear();
        champLogin.sendKeys(login);
        champPwd.clear();
        champPwd.sendKeys(password);
        btnConnection.click();
        return new Accueil(driver, wait);
    }

<<<<<<< HEAD:src/test/java/fr/autom13/Inscription/POM/Connexion.java
=======
    /***
     * Tente une connexion avec des identifiants invalides.
     * Reste sur la LoginPage (pas de redirection).
     */
    public Connexion tenterConnexionInvalide(String login, String password) {
        champLogin.clear();
        champLogin.sendKeys(login);
        champPwd.clear();
        champPwd.sendKeys(password);
        btnConnection.click();
        return this;
    }
>>>>>>> 438de879a33d7c45e2f1dfa1bb76ee3084c3ee0a:src/test/java/fr/autom13/POM/Connexion.java
}
