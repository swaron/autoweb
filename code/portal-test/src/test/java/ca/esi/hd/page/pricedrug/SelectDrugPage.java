package ca.esi.hd.page.pricedrug;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class SelectDrugPage {
    private final WebDriver driver;

    protected WebElement drug;
    @FindBy(xpath = "//div[contains(concat(' ',@class,' '),' x-combo-list ')][1]/div/div[1]")
    protected WebElement firstPatientItem;
    @FindBy(xpath = "//div[contains(concat(' ',@class,' '),' x-combo-list ')][2]//div[contains(concat(' ',@class,' '),' x-combo-list-item ')][1]")
    protected WebElement firstDrugItem;

    @FindBy(xpath = "//div[contains(concat(' ',@class,' '),' x-combo-list ')][2]//div[contains(concat(' ',@class,' '),' x-combo-list-item ')]")
    protected List<WebElement> drugItems;
    
    public SelectDrugPage(WebDriver driver) {
        this.driver = driver;
    }

    public void validateUrl() {
        Assert.assertTrue(StringUtils.contains(driver.getCurrentUrl(), "saving/selectDrug.do"));
    }

    public WebElement getDrug() {
        return drug;
    }

    public WebElement getFirstPatientItem() {
        return firstPatientItem;
    }

    public WebElement getFirstDrugItem() {
        return firstDrugItem;
    }
    public List<WebElement> getDrugItems() {
        return drugItems;
    }
}
