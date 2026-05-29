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

    @FindBy(id = "beginners")
    private WebElement beginnersSection;

    @FindBy(id = "confirmed")
    private WebElement confirmedSection;

    @FindBy(id = "experts")
    private WebElement expertsSection;

    @FindBy(id = "beginnerJardeenersNextButton")
    private WebElement beginnerNextBtn;

    @FindBy(id = "confirmedJardeenersNextButton")
    private WebElement confirmedNextBtn;

    @FindBy(id = "expertJardeenersNextButton")
    private WebElement expertNextBtn;

    @FindBy(id = "jardeenersToValidate")
    private WebElement TO_VALIDATE_SECTION;


    @FindBy(id = "validated")
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

    public boolean estAffichee() {
        return VALIDATE_SECTION.isDisplayed();
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

    public Membre modifyMemberEmail(String currentEmail, String newEmail) {
        WebElement form = findValidatedMemberFormByEmail(currentEmail);
        WebElement emailInput = form.findElement(By.id("emailInput"));

        emailInput.clear();
        emailInput.sendKeys(newEmail);

        return this;
    }

    public Membre confirmModification(String email) {
        WebElement form = findValidatedMemberFormByEmail(email);

        String formId = form.getDomAttribute("id");
        String memberId = formId.replace("modifyJardeenerForm", "");

        WebElement modifyBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("modifyValidatedJardeenerButton" + memberId)
        ));
        modifyBtn.click();

        wait.until(ExpectedConditions.stalenessOf(form));
        wait.until(ExpectedConditions.visibilityOf(expertsSection));

        return this;
    }

    public Membre deleteMember(String email) {
        WebElement form = findValidatedMemberFormByEmail(email);

        String formId = form.getDomAttribute("id");
        String memberId = formId.replace("modifyJardeenerForm", "");

        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("deleteValidatedJardeenerButton" + memberId)
        ));
        deleteBtn.click();

        wait.until(ExpectedConditions.invisibilityOf(form));

        return this;
    }

    private boolean tryValidateMemberOnCurrentPage(String email) {
        wait.until(ExpectedConditions.visibilityOf(TO_VALIDATE_SECTION));

        List<WebElement> emailInputs = TO_VALIDATE_SECTION.findElements(
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

    private WebElement findValidatedMemberFormByEmail(String email) {
        List<WebElement> sections = List.of(beginnersSection, confirmedSection, expertsSection);
        List<WebElement> nextBtns = List.of(beginnerNextBtn, confirmedNextBtn, expertNextBtn);

        for (int i = 0; i < sections.size(); i++) {
            WebElement form = findFormInSection(sections.get(i), nextBtns.get(i), email);
            if (form != null) return form;
        }

        throw new RuntimeException("Membre avec l'email '" + email + "' non trouvé dans les membres validés.");
    }

    private WebElement findFormInSection(WebElement section, WebElement nextBtn, String email) {
        while (!Objects.requireNonNull(nextBtn.getDomAttribute("class")).contains("disabled")) {
            WebElement prevBtn = getPrevButtonForSection(section);
            if (Objects.requireNonNull(prevBtn.getDomAttribute("class")).contains("disabled")) break;
            prevBtn.click();
            wait.until(ExpectedConditions.visibilityOf(section));
        }

        while (true) {
            List<WebElement> emailInputs = section.findElements(By.id("emailInput"));
            for (WebElement input : emailInputs) {
                if (input.getDomAttribute("value") != null
                        && Objects.requireNonNull(input.getDomAttribute("value")).contains(email)) {
                    return input.findElement(By.xpath("./ancestor::form[starts-with(@id,'modifyJardeenerForm')]"));
                }
            }

            if (Objects.requireNonNull(nextBtn.getDomAttribute("class")).contains("disabled")) break;
            nextBtn.click();
            wait.until(ExpectedConditions.visibilityOf(section));
        }

        return null;
    }

    private WebElement getPrevButtonForSection(WebElement section) {
        String sectionId = section.getDomAttribute("id");
        return switch (sectionId) {
            case "beginners" -> driver.findElement(By.id("beginnerJardeenersPreviousButton"));
            case "confirmed" -> driver.findElement(By.id("confirmedJardeenersPreviousButton"));
            case "experts" -> driver.findElement(By.id("expertJardeenersPreviousButton"));
            default -> throw new RuntimeException("Section inconnue : " + sectionId);
        };
    }

    private boolean isMemberPresentInSection(WebElement section, String email, WebElement nextBtn) {
        while (true) {
            List<WebElement> inputs = section.findElements(By.id("emailInput"));
            for (WebElement input : inputs) {
                if (email.equals(input.getDomAttribute("value"))) return true;
            }
            if (Objects.requireNonNull(nextBtn.getDomAttribute("class")).contains("disabled")) break;
            nextBtn.click();
            wait.until(ExpectedConditions.visibilityOf(section));
        }
        return false;
    }

    private void goToFirstPage() {
        while (isPreviousButtonEnabled()) {
            PREV_BTN.click();
            wait.until(ExpectedConditions.stalenessOf(
                    TO_VALIDATE_SECTION.findElements(By.cssSelector("input[id^='emailInput']"))
                            .stream().findFirst().orElse(TO_VALIDATE_SECTION)
            ));
        }
    }

    public boolean isMemberEmailPresent(String email) {
        return isMemberPresentInSection(beginnersSection, email, beginnerNextBtn)
                || isMemberPresentInSection(confirmedSection, email, confirmedNextBtn)
                || isMemberPresentInSection(expertsSection, email, expertNextBtn);
    }


    private boolean tryGoToNextPage() {
        if (!isNextButtonEnabled()) {
            return false;
        }

        List<WebElement> currentEmails = TO_VALIDATE_SECTION.findElements(
                By.cssSelector("input[id^='emailInput']")
        );

        NEXT_BTN.click();

        if (!currentEmails.isEmpty()) {
            wait.until(ExpectedConditions.stalenessOf(currentEmails.getFirst()));
        }

        return true;
    }

    private boolean isNextButtonEnabled() {
        return NEXT_BTN.isEnabled() && !Objects.requireNonNull(NEXT_BTN.getDomAttribute("class")).contains("disabled");
    }

    private boolean isPreviousButtonEnabled() {
        return PREV_BTN.isEnabled() && !Objects.requireNonNull(PREV_BTN.getDomAttribute("class")).contains("disabled");
    }
}







