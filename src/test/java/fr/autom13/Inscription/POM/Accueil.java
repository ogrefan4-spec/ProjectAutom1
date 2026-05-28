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

<<<<<<< HEAD:src/test/java/fr/autom13/Inscription/POM/Accueil.java
    @FindBy(linkText = "Connexion")
    private WebElement CONN;

    @FindBy(linkText = "Inscription")
=======
    @FindBy(xpath = "//a[@href='connection.html']")
    private WebElement CONN;

    @FindBy(xpath = "//a[@href='registration.html']")
>>>>>>> 438de879a33d7c45e2f1dfa1bb76ee3084c3ee0a:src/test/java/fr/autom13/POM/Accueil.java
    private WebElement REGISTER;

    @FindBy(id = "informations")
    private WebElement INFORMATION;

<<<<<<< HEAD:src/test/java/fr/autom13/Inscription/POM/Accueil.java
    @FindBy(linkText = "Tâches")
    private WebElement TASK;

    @FindBy(linkText = "Parcelles")
    private WebElement PLOTS;

    @FindBy(linkText = "Membres")
=======
    @FindBy(xpath = "//a[@href='task.html']")
    private WebElement TASK;

    @FindBy(id = "plots")
    private WebElement PLOTS;

    @FindBy(xpath = "//a[@href='jardeneers.html']")
>>>>>>> 438de879a33d7c45e2f1dfa1bb76ee3084c3ee0a:src/test/java/fr/autom13/POM/Accueil.java
    private WebElement MEMBERS;

    @FindBy(id = "unstaffedImminentTasks")
    private WebElement unstaffedImminentTasks;

    @FindBy(id = "unstaffedTasks")
    private WebElement unstaffedTasks;

    public WebElement vueAccueil = INFORMATION;

    public Accueil(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(INFORMATION));
    }

    public Connexion goToConnexion() {
        CONN.click();
        return new Connexion(driver, wait);
    }

    public Inscription goToRegister() {
        REGISTER.click();
        return new Inscription(driver, wait);
    }

    public Membre goToJardenners() {
        MEMBERS.click();
        return new Membre(driver, wait);
    }

    public boolean estAffichee() {return vueAccueil.isDisplayed();}
    public boolean informationIsDisplayed() {return INFORMATION.isDisplayed();}
    public boolean unstaffedTasksIsDisplayed() {return unstaffedTasks.isDisplayed();}
    public boolean unstaffedImminentTasksIsDisplayed() {return unstaffedImminentTasks.isDisplayed();}
//    public boolean presentationAssociationIsDisplayed() {return presentationAssociation.isDisplayed();}
    public boolean plotIsDisplayed() {return PLOTS.isDisplayed();}

}
