package fr.autom13.POM;

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

    @FindBy(xpath = "/html/body/article/div[1]/div/h2")
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

    public Connexion inputUserandPass(String user, String psw) {
        USER.clear();
        USER.sendKeys(user);
        PSW.clear();
        PSW.sendKeys(psw);
        return this;
    }

    public Accueil pressConnexionButton() {
        SUBMIT.click();
        return new Accueil(driver, wait);
    }


}
