package fr.autom13.Inscription.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;
import java.util.List;


public class Parcelle {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /////////////////////////////
    /// PARCELLES DISPONIBLES ///
    /////////////////////////////

    @FindBy(id = "plotList")
    private WebElement vueParcelles;

    @FindBy(xpath = "plotsPreviousButton")
    @CacheLookup
    private WebElement btnParcellesPrecedentes;

    @FindBy(xpath = "plotsNextButton")
    @CacheLookup
    private WebElement btnParcellesSuivantes;

    ///////////////////////////
    /// PARCELLES À VALIDER ///
    ///////////////////////////

    @FindBy(id = "plotsToValidate")
    private WebElement vueParcellesAValider;

    @FindBy(id = "plotsToValidatePreviousButton")
    @CacheLookup
    private WebElement btnParcellesPrecedentesAValider;

    @FindBy(id = "plotsToValidateNextButton")
    @CacheLookup
    private WebElement btnParcellesSuivantesAValider;

    //////////////////////////////////////
    /// FORMULAIRE D'AJOUT DE PARCELLE ///
    //////////////////////////////////////

    @FindBy(id = "addPlotForm")
    private WebElement vueAjoutParcelle;

    @FindBy(xpath = "//form[@id='addPlotForm']//input[@id='nameInput']")
    private WebElement nomNouvelleParcelle;

    @FindBy(xpath = "//form[@id='addPlotForm']//input[@id='addressInput']")
    private WebElement adresseNouvelleParcelle;

    @FindBy(xpath = "//form[@id='addPlotForm']//input[@id='zipCodeInput']")
    private WebElement codePostalNouvelleParcelle;

    @FindBy(xpath = "//form[@id='addPlotForm']//input[@id='cityInput']")
    private WebElement villeNouvelleParcelle;

    @FindBy(xpath = "//form[@id='addPlotForm']//input[@id='widthInput']")
    private WebElement largeurNouvelleParcelle;

    @FindBy(xpath = "//form[@id='addPlotForm']//input[@id='lengthInput']")
    private WebElement longueurNouvelleParcelle;

    @FindBy(xpath = "//form[@id='addPlotForm']//input[@id='addPlotButton']")
    @CacheLookup
    private WebElement btnAjouterNouvelleParcelle;

    @FindBy(xpath = "//form[@id='addPlotForm']//label[@id='messageLabel']")
    private WebElement messageAjoutParcelle;



    /***
     * Constucteur paramétré de la classe Connexion
     * @param driver correspond au webdriver
     * @param wait correspond à l'attente explicite
     */
    public Parcelle(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(vueAjoutParcelle));
    }

    /***
     * Méthode permettant de contrôler la visibilité du formulaire d'ajout de Parcelle.
     * @return un booléen indiquant si le formulaire de connexion est visible
     */
    public boolean estAffichee () {
        return vueAjoutParcelle.isDisplayed();
    }

    public WebElement getVueAjoutParcelle(){return vueAjoutParcelle;}

    public WebElement getMessageAjoutParcelle(){ return messageAjoutParcelle;}

    /***
     * Crée une nouvelle parcelle ave les informations renseignées
     * La même page, contenant la nouvelle parcelle.
     */
    public Parcelle ajouterParcelle (String name, String adresse, String zipCode,
                                    String city, String width, String length){
        nomNouvelleParcelle.clear();
        nomNouvelleParcelle.sendKeys(name);
        wait.until(driver ->
                nomNouvelleParcelle.getAttribute("value").equals(name));

        adresseNouvelleParcelle.clear();
        adresseNouvelleParcelle.sendKeys(adresse);
        wait.until(driver ->
                adresseNouvelleParcelle.getAttribute("value").equals(adresse));

        codePostalNouvelleParcelle.clear();
        codePostalNouvelleParcelle.sendKeys(zipCode);
        wait.until(driver ->
                codePostalNouvelleParcelle.getAttribute("value").equals(zipCode));

        villeNouvelleParcelle.clear();
        villeNouvelleParcelle.sendKeys(city);
        wait.until(driver ->
                villeNouvelleParcelle.getAttribute("value").equals(city));

        largeurNouvelleParcelle.clear();
        largeurNouvelleParcelle.sendKeys(width);
        wait.until(driver ->
                largeurNouvelleParcelle.getAttribute("value").equals(width));

        longueurNouvelleParcelle.clear();
        longueurNouvelleParcelle.sendKeys(length);
        wait.until(driver ->
                longueurNouvelleParcelle.getAttribute("value").equals(length));

        btnAjouterNouvelleParcelle.click();
        return new Parcelle(driver, wait);
    }

    public boolean listeDesParcellesVisiblesEstVide(){
        return driver.findElements(By.xpath("//div[@id='validated']//div")).isEmpty();
    }

    public boolean listeDesParcellesInvisiblesEstVide(){
        return driver.findElements(By.xpath("//div[@id='non-validated']//div")).isEmpty();
    }


}
