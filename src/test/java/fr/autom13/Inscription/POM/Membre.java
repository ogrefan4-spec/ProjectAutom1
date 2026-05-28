package fr.autom13.Inscription.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Membre {
    private final WebDriver driver;
    private final WebDriverWait wait;


    @FindBy(css = "nav .active")
    private WebElement MEMBER;

    @FindBy(id = "jardeenersToValidate")
    private WebElement VALIDATE;

    @FindBy(id = "emailInput")
    private WebElement EMAILIN;

    @FindBy(id = "modifyNonValidatedJardeenerButton2")
    @CacheLookup
    private WebElement MODIFYVALID_BTN;

    @FindBy(id = "validateJardeenerButton2")
    @CacheLookup
    private WebElement VALIDATION_BTN;

    @FindBy(id = "deleteNonValidatedJardeenerButton2")
    @CacheLookup
    private WebElement DELETE_BTN;

    public Membre(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(MEMBER));
    }

    public Membre validateMember(String email) {

        return this;
    }
}
