package fr.autom13.POM;

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

    @FindBy (id = "passwordInput")
    private WebElement champPwd;

    @FindBy (id = "connectionButton")
    private WebElement btnConnection;

    public Connexion(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(vueConnexion));
    }

    public boolean estAffichee() {
        return vueConnexion.isDisplayed();
    }

    /**
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

    /**
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
}
