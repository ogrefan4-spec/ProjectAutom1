package fr.autom13.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Accueil {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(linkText = "login.php")
    private WebElement CONN;

    @FindBy(name = "registration.html")
    private WebElement REGISTER;

    @FindBy(id = "informations")
    private WebElement ACCUEIL;

    @FindBy(id = "task.html")
    private WebElement TASK;

    @FindBy(id = "plots.html")
    private WebElement PLOTS;

    @FindBy(id = "jardeneers.html")
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
}
