package fr.autom13.Inscription.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Connexion {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "loginInput")
    private WebElement USER;

    @FindBy(id = "passwordInput")
    private WebElement PSW;

    @FindBy(css = "nav .active")
    private WebElement CONN;

    @FindBy(id = "connectionButton")
    @CacheLookup
    private WebElement SUBMIT;

    public Connexion(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(CONN));
    }

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
    }

    public Accueil pressConnexionButton() {
        SUBMIT.click();
        return new Accueil(driver, wait);
    }

    public Connexion inputAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(USER));
        USER.clear();
        USER.sendKeys("admin");
        wait.until(driver ->
                USER.getAttribute("value").equals("admin"));

        wait.until(ExpectedConditions.elementToBeClickable(PSW));
        PSW.clear();
        PSW.sendKeys("admin");
        wait.until(driver ->
                PSW.getAttribute("value").equals("admin"));
        return this;
    }

    /***
     * Tente une connexion avec des identifiants invalides.
     * Reste sur la LoginPage (pas de redirection).
     */
    public Connexion tenterConnexionInvalide (String login, String password){
        USER.clear();
        USER.sendKeys(login);
        PSW.clear();
        PSW.sendKeys(password);
        SUBMIT.click();
        return this;
    }

}
