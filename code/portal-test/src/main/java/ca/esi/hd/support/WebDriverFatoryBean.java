package ca.esi.hd.support;

import javax.annotation.PreDestroy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class WebDriverFatoryBean implements FactoryBean<WebDriver> {
    WebDriver driver;

    @Override
    public WebDriver getObject() throws Exception {
        ProfilesIni allProfiles = new ProfilesIni();
        FirefoxProfile profile = allProfiles.getProfile("webdriver");
        profile.setPreference("foo.bar", 23);
        profile.setEnableNativeEvents(true);
        driver = new FirefoxDriver(profile);
        return driver;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public Class<WebDriver> getObjectType() {
        return WebDriver.class;
    }

    @PreDestroy
    public void cleanUp(){
        if(driver != null){
            driver.close();
        }
    }
}
