package fr.autom13.tache;

import fr.autom13.Inscription.POM.Accueil;
import fr.autom13.Inscription.POM.Connexion;
import fr.autom13.BaseTest;
import fr.autom13.Inscription.POM.Tache;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TacheTest extends BaseTest {

    @Override
    protected void createAndValideAccount() {
        super.createAndValideAccount();
    }

    @Test
    @Order(1)
    @DisplayName("Non accès à la page tâche pour un simple visiteur")
    public void testNonAccesTachePage_Visiteur() {
        driver.get("http://localhost:8085/tasks.html");
        Tache tachePage = new Tache(driver, wait);
        assertTrue(tachePage.estAffichee());
        assertTrue(tachePage.nonConnectedAccountMessageIsDisplayed()
                && tachePage.ownerAccountMessageIsDisplayed()
                && tachePage.conformityOfNonConnectedAccountMessage("Vous pourrez accéder à la gestion des tâches dès " +
                "que votre inscription aura été validée.")
                && tachePage.conformityOfOwnerAccountMessage("Vous pouvez accéder à la gestion des tâches en vous " +
                "enregistrant en tant que jardeenier et en vous acquitant de la cotisation annuelle.")
        );
    }

    @Test
    @Order(2)
    @DisplayName("Non accès à la page tâche pour un profil Propriétaire")
    public void testNonAccesTachePage_Owner(){
        Accueil accueilPage = new Accueil(driver, wait);

        Connexion connexionPage = accueilPage.goToConnexion();

        driver.get("http://localhost:8085/tasks.html");
        Tache tachePage = new Tache(driver, wait);

        assertTrue(tachePage.estAffichee());
        assertFalse(tachePage.nonConnectedAccountMessageIsDisplayed());
        assertTrue(tachePage.ownerAccountMessageIsDisplayed()
                && tachePage.conformityOfOwnerAccountMessage("Vous pouvez accéder à la gestion des tâches en vous " +
                "enregistrant en tant que jardeenier et en vous acquitant de la cotisation annuelle.")
        );
//        accueilPage.goToAccueil();
    }

//    @Test
//    @Order(3)
//    @DisplayName("Accès à la page Tâches pour un profil connecté autorisé")
//    public void testAccesTachePage_Members() {
//        Accueil accueilPage = new Accueil(driver, wait);
//        Connexion connexionPage = accueilPage.goToConnexion();
//        connexionPage.inputAdmin();
//        accueilPage = connexionPage.pressConnexionButton();
//        Tache tachePage = accueilPage.goToTask();
//        accueilPage.goToAccueil();
//    }

//    @Test
//    @Order(4)
//    @DisplayName("")
}
