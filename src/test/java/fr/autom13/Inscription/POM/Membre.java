package fr.autom13.Inscription.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Objects;

public class Membre {
    private final WebDriver driver;
    private final WebDriverWait wait;


    @FindBy(css = "nav .active")
    private WebElement MEMBER;

    @FindBy(id = "emailInput")
    private WebElement MEMBER_EMAIL;

    @FindBy(id = "jardeenersToValidateNextButton")
    private WebElement NEXT_BTN;

    @FindBy(id = "jardeenersToValidatePreviousButton")
    private WebElement PREV_BTN;

    @FindBy(id = "jardeenersToValidateCount")
    private WebElement PAGE_COUNT;

    @FindBy(id = "jardeenersToValidate")
    private WebElement VALIDATE_SECTION;

    @FindBy(id = "modifyNonValidatedJardeenerButton2")
    @CacheLookup
    private WebElement MODIFYVALID_BTN;

    @FindBy(id = "deleteNonValidatedJardeenerButton2")
    @CacheLookup
    private WebElement DELETE_BTN;

    public Membre(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
        wait.until(ExpectedConditions.visibilityOf(VALIDATE_SECTION));
    }

    public Membre validateMember(String email) {
        boolean found = false;
        boolean hasNextPage = true;

        while (!found && hasNextPage) {
            found = tryValidateMemberOnCurrentPage(email);

            if (!found) {
                hasNextPage = tryGoToNextPage();
            }
        }

        if (!found) {
            throw new RuntimeException("Membre avec l'email '" + email + "' non trouvé dans les inscriptions à valider.");
        }
        return this;
    }

    private boolean tryValidateMemberOnCurrentPage(String email) {
        wait.until(ExpectedConditions.visibilityOf(VALIDATE_SECTION));

        List<WebElement> emailInputs = VALIDATE_SECTION.findElements(
                By.cssSelector("input[id^='emailInput']")
        );

        for (WebElement emailInput : emailInputs) {
            String value = emailInput.getDomAttribute("value");
            if (value != null && value.contains(email)) {
                WebElement card = emailInput.findElement(By.xpath("./ancestor::div[contains(@class,'card') or contains(@class,'col')][1]"));
                WebElement validateBtn = card.findElement(
                        By.cssSelector("button[id^='validateJardeenerButton'], input[id^='validateJardeenerButton']")
                );

                wait.until(ExpectedConditions.elementToBeClickable(validateBtn));
                validateBtn.click();
                return true;
            }
        }

        return false;
    }

    private void goToFirstPage() {
        while (isPreviousButtonEnabled()) {
            PREV_BTN.click();
            wait.until(ExpectedConditions.stalenessOf(
                    VALIDATE_SECTION.findElements(By.cssSelector("input[id^='emailInput']"))
                            .stream().findFirst().orElse(VALIDATE_SECTION)
            ));
        }
    }


    private boolean tryGoToNextPage() {
        if (!isNextButtonEnabled()) {
            return false;
        }

        List<WebElement> currentEmails = VALIDATE_SECTION.findElements(
                By.cssSelector("input[id^='emailInput']")
        );

        NEXT_BTN.click();

        if (!currentEmails.isEmpty()) {
            wait.until(ExpectedConditions.stalenessOf(currentEmails.get(0)));
        }

        return true;
    }

    private boolean isNextButtonEnabled() {
        return NEXT_BTN.isEnabled() && !NEXT_BTN.getAttribute("class").contains("disabled");
    }

    private boolean isPreviousButtonEnabled() {
        return PREV_BTN.isEnabled() && !PREV_BTN.getAttribute("class").contains("disabled");
    }
}







