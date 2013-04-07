package ca.esi.hd.page;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.i18n.LocaleContextHolder;
import org.testng.Assert;

import ca.esi.hd.support.ApplicationContextAccessor;

public abstract class BasePage implements FixUrlPage {
    protected RemoteWebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = (RemoteWebDriver) driver;
        Assert.assertTrue(StringUtils.contains(driver.getCurrentUrl(), getLocation()),
                "Page location error, not in right page.");
    }

    public void waitElement(final By by) {
        WebElement lang = (new WebDriverWait(driver, 20)).until(new ExpectedCondition<WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElement(by);
            }
        });
    }
    public String msg(String key){
        return ApplicationContextAccessor.getMessageSourceAccessor().getMessage(key, LocaleContextHolder.getLocale());
    }
}
