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

    @FindBy(xpath = "//a[@href='registration.html']")
    private WebElement INSCRIPTION;

    @FindBy(xpath = "//a[@href='connection.html']")
    private WebElement CONNEXION;

    @FindBy(xpath = "//a[@href='plots.html']")
    private WebElement PLOTS;

    @FindBy(id = "informations")
    private WebElement INFORMATION;

    @FindBy(xpath = "//a[@href='task.html']")
    private WebElement TASK;

    //@FindBy(id = "plots")
    //private WebElement PLOTS;

    @FindBy(xpath = "//a[@href='jardeneers.html']")
    private WebElement MEMBERS;

    @FindBy(id = "unstaffedImminentTasks")
    private WebElement unstaffedImminentTasks;

    @FindBy(id = "unstaffedTasks")
    private WebElement unstaffedTasks;

    public WebElement vueAccueil;

    public Accueil(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(INFORMATION));
        vueAccueil = INFORMATION;
    }

    public Connexion goToConnexion() {
        CONNEXION.click();
        return new Connexion(driver, wait);
    }

    public Connexion goToPlot() {
        PLOTS.click();
        return new Connexion(driver, wait);
    }

    public Inscription goToRegister() {
        INSCRIPTION.click();
        return new Inscription(driver, wait);
    }

    public Membre goToJardenners() {
        MEMBERS.click();
        return new Membre(driver, wait);
    }

    public Parcelle goToParcelle(){
        PLOTS.click();
        return new Parcelle(driver, wait);
    }

    public boolean estAffichee() {return vueAccueil.isDisplayed();}
    public boolean informationIsDisplayed() {return INFORMATION.isDisplayed();}
    public boolean unstaffedTasksIsDisplayed() {return unstaffedTasks.isDisplayed();}
    public boolean unstaffedImminentTasksIsDisplayed() {return unstaffedImminentTasks.isDisplayed();}
//    public boolean presentationAssociationIsDisplayed() {return presentationAssociation.isDisplayed();}
    public boolean plotIsDisplayed() {return PLOTS.isDisplayed();}

}
