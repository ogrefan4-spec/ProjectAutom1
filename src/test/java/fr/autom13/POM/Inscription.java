package fr.autom13.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Inscription {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "loginInput")
    private WebElement USER;

    @FindBy(id = "passwordInput")
    private WebElement PSW;

    @FindBy(id = "firstNameInput")
    private WebElement NAME;

    @FindBy(id = "lastNameInput")
    private WebElement LASTNAME;

    @FindBy(id = "genderSelect")
    private WebElement GENDER;

    @FindBy(id = "birthdateInput")
    private WebElement BDATE;

    @FindBy(id = "addressInput")
    private WebElement ADDRESS;

    @FindBy(id = "cityInput")
    private WebElement CITY;

    @FindBy(id = "zipCodeInput")
    private WebElement ZIPCODE;

    @FindBy(id = "phoneNumberInput")
    private WebElement PHONE;

    @FindBy(id = "emailInput")
    private WebElement EMAIL;

    @FindBy(id = "roleSelect")
    private WebElement ROLE;

    @FindBy(id = "skillSelect")
    private WebElement SKILLLVL;

    @FindBy(className = "nav-link active")
    private WebElement RGSTR;

    public Inscription(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(RGSTR));
    }


}
