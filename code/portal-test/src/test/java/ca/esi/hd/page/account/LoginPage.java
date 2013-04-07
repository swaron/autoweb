package ca.esi.hd.page.account;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ca.esi.hd.page.BasePage;

public class LoginPage extends BasePage{
    // Here's the element
    private WebElement username;
    private WebElement password;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        driver.findElement(By.cssSelector("#loginForm button")).click();
        waitElement(By.linkText(msg("Log out")));
    }

    @Override
    public String getLocation() {
        return "login.do";
    }
}
