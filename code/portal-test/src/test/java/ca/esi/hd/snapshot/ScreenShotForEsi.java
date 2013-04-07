package ca.esi.hd.snapshot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.Test;

import ca.esi.hd.page.account.AccountSecurityPage;
import ca.esi.hd.page.member.OverviewPage;
import ca.esi.hd.page.pricedrug.SelectDrugPage;
import ca.esi.hd.page.pub.HomePage;
import ca.esi.hd.test.BaseTest;

public class ScreenShotForEsi extends BaseTest {

    @Value("${language}")
    String language;
    
    @Test
    public void testAll() throws Exception {
        resetAccountToFirsttime();
        HomePage page = capatureHomePage();
        OverviewPage overviewPage = firstTimeLoginCase(page);
        overviewPage.getSavingLink().click();
        SelectDrugPage selectDrugPage = PageFactory.initElements(driver, SelectDrugPage.class);
        selectExtComboBoxFirstItem("patient");
        selectDrugPage.getDrug().sendKeys("ama");
        WebElement firstDrugItem = selectDrugPage.getFirstDrugItem();
//        waitElementPresent(item, "can't select a ext combobox because there are no list items.");
        firstDrugItem.click();
        
    }

    public HomePage capatureHomePage() {
        HomePage page = gotoHomePage();
        takeScreenShot("1.home/1.first.png");
        return page;
    }

    public OverviewPage firstTimeLoginCase(HomePage page) {
        // first time login
        page.getUsername().sendKeys(accountService.getUsername());
        page.getPassword().sendKeys(accountService.getPassword());
        By xpath = By.xpath(String.format("//button[contains(.,'%s')]", msg("button.signIn")));
        driver.findElement(xpath).click();
        // wait security page
        waitElement(By.id("securityForm"), "expect account security page and secrity form.");
        AccountSecurityPage securityPage = PageFactory.initElements(driver, AccountSecurityPage.class);
        securityPage.getOldPassword().sendKeys(accountService.getPassword());
        securityPage.getNewPassword().sendKeys(accountService.getPassword());
        securityPage.getConfirmPassword().sendKeys(accountService.getPassword());
        selectExtComboBoxFirstItem("question");
        securityPage.getAnswer().sendKeys("123");
        securityPage.getSaveButton().click();
        waitForAndClickDailog(msg("title.success"), msg("button.ok"));
        waitLocation("/prescription/overview.do");
        return PageFactory.initElements(driver, OverviewPage.class);
    }

    public void resetAccountToFirsttime() {
        // initial account status
        int count = jdbcTemplate.queryForInt("select count(*) from web_user where login_id=?",
                accountService.getUsername());
        Assert.assertEquals(count, 1);
        jdbcTemplate.update("update web_user set login_state = 1, locale = ? where login_id=?", language, accountService.getUsername());
    }

    public HomePage gotoHomePage() {
        // goto page
        String homeUrl = toAbsolutePath("home.do");
        logger.debug("goto home page {}", homeUrl);
        driver.get(homeUrl);
        return PageFactory.initElements(driver, HomePage.class);
    }
}
