package ca.esi.hd.page.account;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ca.esi.hd.page.BasePage;

public class AccountSecurityPage extends BasePage {

    private WebElement oldPassword;
    private WebElement newPassword;
    private WebElement confirmPassword;
    private WebElement question;
    private WebElement answer;
    
    @FindBy(xpath="//div[@id='accountSecurity']//input[@id='answer']/following::button[2]")
    private WebElement saveButton;

    public AccountSecurityPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getLocation() {
        return "login/accountSecurity.do";
    }

    public WebElement getOldPassword() {
        return oldPassword;
    }

    public WebElement getNewPassword() {
        return newPassword;
    }

    public WebElement getConfirmPassword() {
        return confirmPassword;
    }

    public WebElement getQuestion() {
        return question;
    }

    public WebElement getAnswer() {
        return answer;
    }

    public WebElement getSaveButton() {
        return saveButton;
    }

}
