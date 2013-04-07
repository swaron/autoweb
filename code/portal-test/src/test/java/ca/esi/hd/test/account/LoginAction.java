package ca.esi.hd.test.account;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import ca.esi.hd.page.account.LoginPage;
import ca.esi.hd.page.member.OverviewPage;
import ca.esi.hd.test.BaseTest;

public class LoginAction extends BaseTest {

    @Test
    public void afterLogin() {
        // Navigate to the right place
        driver.get(toAbsolutePath("login.do"));
        // Create a new instance of the search page class
        // and initialise any WebElement fields in it.
        LoginPage login = PageFactory.initElements(driver, LoginPage.class);
        // And now do the search.
        login.login("nancy@longtop.com", "a123456");
        OverviewPage page = PageFactory.initElements(driver, OverviewPage.class);
        Assert.assertTrue(isElementPresent(By.linkText("Log out")));
    }
}
