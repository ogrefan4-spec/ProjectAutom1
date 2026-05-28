package fr.autom13.Inscription.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.format.DateTimeFormatter;

public class Inscription {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FindBy(css = "nav .active")
    private WebElement INSCRIPTION;

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

    @FindBy(id = "registrationForm ")
    private WebElement RGSTR;

    @FindBy(id = "jardeener")
    private WebElement VALIDATION;

    @FindBy(id = "registrationButton")
    @CacheLookup
    private WebElement SUBMIT;

    public Inscription(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(INSCRIPTION));
    }

    public Inscription inputUserandPass(String user, String psw) {
        USER.clear();
        USER.sendKeys(user);
        PSW.clear();
        PSW.sendKeys(psw);
        return this;
    }

    public Inscription inputNameplusGender(String name, String lname, String gender) {
        NAME.clear();
        NAME.sendKeys(name);
        LASTNAME.clear();
        LASTNAME.sendKeys(lname);
        Select genderselect = new Select(GENDER);
        genderselect.selectByVisibleText(gender);
        return this;
    }

    public Inscription inputDate(String date) {
        BDATE.clear();
        BDATE.sendKeys(date);
        return this;
    }

    public Inscription inputAdresse(String adress, String city, String zip, String phone, String email) {
        ADDRESS.clear();
        ADDRESS.sendKeys(adress);
        CITY.clear();
        CITY.sendKeys(city);
        ZIPCODE.clear();
        ZIPCODE.sendKeys(zip);
        PHONE.clear();
        PHONE.sendKeys(phone);
        EMAIL.clear();
        EMAIL.sendKeys(email);
        return this;
    }

    public Inscription inputRoleAndSkill(String role, String Skill) {
        Select roleselect = new Select(ROLE);
        Select skillselect = new Select(SKILLLVL);
        roleselect.selectByVisibleText(role);
        skillselect.selectByVisibleText(Skill);
        return this;
    }

    public String pressSubmitButton() {
        SUBMIT.click();
        wait.until(ExpectedConditions.visibilityOf(VALIDATION));
        return "Votre inscription est désormais en attente de validation";
    }


}
