package ca.esi.hd.page.pub;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.esi.hd.page.BasePage;

public class HomePage extends BasePage {
    private WebElement username;
    private WebElement password;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocation() {
        return "home.do";
    }

    public WebElement getUsername() {
        return username;
    }

    public WebElement getPassword() {
        return password;
    }
}
