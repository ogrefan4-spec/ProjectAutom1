package fr.autom13.Inscription.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Accueil {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(linkText = "Connexion")
    private WebElement CONN;

    @FindBy(linkText = "Inscription")
    private WebElement REGISTER;

    @FindBy(id = "informations")
    private WebElement ACCUEIL;

    @FindBy(linkText = "Tâches")
    private WebElement TASK;

    @FindBy(linkText = "Parcelles")
    private WebElement PLOTS;

    @FindBy(linkText = "Membres")
    private WebElement MEMBERS;


    public Accueil(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(ACCUEIL));
    }

    public Connexion goToConnexion() {
        CONN.click();
        return new Connexion(driver, wait);
    }

    public Inscription goToRegister() {
        REGISTER.click();
        return new Inscription(driver, wait);
    }

    public Membre goToJardenners() {
        MEMBERS.click();
        return new Membre(driver, wait);
    }
}
