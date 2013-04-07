package ca.esi.hd.page.member;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class OverviewPage {
    private final WebDriver driver;
    // no id can be used, shame
    @FindBy(xpath = "//a[contains(@href,'saving/selectDrug.do')]")
    private WebElement savingLink;
    private WebElement password;

    public OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public void validateUrl() {
        Assert.assertTrue(StringUtils.contains(driver.getCurrentUrl(), "prescription/overview.do"));
    }

    public WebElement getSavingLink() {
        return savingLink;
    }

}
