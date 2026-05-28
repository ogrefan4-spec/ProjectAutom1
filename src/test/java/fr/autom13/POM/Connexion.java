package fr.autom13.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Connexion {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(linkText = "login.php")
    private WebElement USER;

    @FindBy(name = "registration.html")
    private WebElement REGISTER;

    @FindBy(id = "informations")
    private WebElement ACCUEIL;

    public Connexion(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }


}
