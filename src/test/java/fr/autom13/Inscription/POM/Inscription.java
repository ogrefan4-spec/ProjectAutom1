package fr.autom13.Inscription.POM;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;

import java.time.format.DateTimeFormatter;

import static fr.autom13.Inscription.UtilsConn.cellValue;
import static fr.autom13.Inscription.UtilsConn.clearAndType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Inscription {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final String DEFAULT_EXCEL_PATH = "src/test/java/fr/autom13/Inscription/excel/membres.xlsx";
    private static final String SUCCESS_MESSAGE = "Votre inscription est désormais en attente de validation";

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

    public Inscription fillFromData(MembreData data) {
        fillCredentials(data.login(), data.password());
        fillIdentity(data.firstName(), data.lastName(), data.gender());
        fillBirthdate(data.birthdate());
        fillContact(data.address(), data.city(), data.zipCode(), data.phone(), data.email());
        fillRoleAndSkill(data.role(), data.skill());
        return this;
    }

    public Inscription fillFromExcel(int rowIndex) {
        return fillFromExcel(DEFAULT_EXCEL_PATH, rowIndex);
    }

    public Inscription fillFromExcel(String excelPath, int rowIndex) {
        try (FileInputStream fis = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(rowIndex + 1);

            if (row == null) {
                throw new IllegalArgumentException(
                        "Aucune donnée à la ligne " + rowIndex + " dans " + excelPath);
            }

            fillCredentials(cellValue(row, 0), cellValue(row, 1));
            fillIdentity(cellValue(row, 2), cellValue(row, 3), cellValue(row, 4));
            fillBirthdate(cellValue(row, 5));
            fillContact(
                    cellValue(row, 6), cellValue(row, 7),
                    cellValue(row, 8), cellValue(row, 9),
                    cellValue(row, 10)
            );
            fillRoleAndSkill(cellValue(row, 11), cellValue(row, 12));

        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire le fichier Excel : " + excelPath, e);
        }
        return this;
    }

    public Inscription fillCredentials(String user, String psw) {
        clearAndType(USER, user);
        clearAndType(PSW, psw);
        return this;
    }

    public Inscription fillIdentity(String name, String lname, String gender) {
        clearAndType(NAME, name);
        clearAndType(LASTNAME, lname);
        new Select(GENDER).selectByValue(gender);
        return this;
    }

    public Inscription fillBirthdate(String date) {
        clearAndType(BDATE, date);
        return this;
    }

    public Inscription fillContact(String adress, String city, String zip, String phone, String email) {
        clearAndType(ADDRESS, adress);
        clearAndType(CITY, city);
        clearAndType(ZIPCODE, zip);
        clearAndType(PHONE, phone);
        clearAndType(EMAIL, email);
        return this;
    }

    public Inscription fillRoleAndSkill(String skill, String role) {
        wait.until(ExpectedConditions.elementToBeClickable(SKILLLVL));
        new Select(SKILLLVL).selectByValue(skill);
        wait.until(ExpectedConditions.elementToBeClickable(ROLE));
        new Select(ROLE).selectByValue(role);
        return this;
    }

    public String submit() {
        SUBMIT.click();
        wait.until(ExpectedConditions.visibilityOf(VALIDATION));
        return SUCCESS_MESSAGE;
    }

    private Accueil fullInputRegistrationAndSubmit(Accueil accueilPage ,String login, String password, String firstName,
                                                  String lastName, String gender, String birthDate,
                                                  String address, String city, String cp,
                                                  String tel, String mail, String role, String skill) {

        inputUserandPass(login, password);
        inputNameplusGender(firstName, lastName, gender);
        inputDate(birthDate);
        inputAdresse(address, city, cp, tel, mail);
        inputRoleAndSkill(skill, role);

        try {
            submit();
        } catch (Exception e) {
            System.out.println("ERROR : Something went wrong with the registration");
            return null;
        }

        accueilPage.backToAccueil(false);
        Connexion connexionPage = accueilPage.goToConnexion().inputAdmin();
        accueilPage = connexionPage.pressConnexionButton();
        Membre membrePage = accueilPage.goToJardenners().validateMember(mail);

//        Membre membrePage = new Accueil(driver,wait).
//                goToConnexion()
//                .inputAdmin()
//                .pressConnexionButton()
//                .goToJardenners()
//                .validateMember(mail);
        accueilPage.disconnect();

        return accueilPage;
    }

    public void createAccountValideItAndConnect(Accueil accueilPage, String login, String password, String firstName,
                                                String lastName, String gender, String birthDate,
                                                String address, String city, String cp,
                                                String tel, String mail, String role, String skill) {

        Connexion connexionPage =  fullInputRegistrationAndSubmit(accueilPage, login, password, firstName,
                lastName, gender,birthDate,
                address, city, cp,
                tel, mail ,role, skill)
                .goToConnexion()
                .inputUserAndPass(login, password);
        accueilPage = connexionPage.pressConnexionButton();
    }

}


