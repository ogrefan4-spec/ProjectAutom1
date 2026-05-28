package fr.autom13.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Inscription {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Inscription(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
}
