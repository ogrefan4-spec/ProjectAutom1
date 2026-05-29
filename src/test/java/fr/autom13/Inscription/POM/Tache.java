package fr.autom13.Inscription.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Tache {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "validated")
    private WebElement vueTask;

    @FindBy(id = "non-validated")
    private WebElement nonConnectedAccountMessage;

    @FindBy(id = "owner")
    private WebElement ownerAccountMessage;

    public Tache(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(vueTask));
    }

    public boolean estAffichee() {return vueTask.isDisplayed();}
    public boolean nonConnectedAccountMessageIsDisplayed() { return nonConnectedAccountMessage.isDisplayed();}

    public boolean conformityOfNonConnectedAccountMessage(String message) {
        return nonConnectedAccountMessage.getText().equals(message);
    }

    public boolean ownerAccountMessageIsDisplayed() { return ownerAccountMessage.isDisplayed();}

    public boolean conformityOfOwnerAccountMessage(String message) {
        return ownerAccountMessage.getText().equals(message);
    }
}
