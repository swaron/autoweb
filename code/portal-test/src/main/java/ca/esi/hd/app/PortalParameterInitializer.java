package ca.esi.hd.app;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.LocaleUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PortalParameterInitializer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WebDriver webDriver;

    @Value("${window.size.x}")
    Integer xSize;
    @Value("${window.size.y}")
    Integer ySize;

    @Value("${language}")
    String language;

    @PostConstruct
    public void init() {
        RemoteWebDriver driver = (FirefoxDriver) webDriver;
        Dimension targetSize = new Dimension(xSize, ySize);
        driver.manage().window().setSize(targetSize);
        logger.info("initialize window to {}", targetSize);
        Locale locale = LocaleUtils.toLocale(language);
        logger.info("initialize language to {}", locale);
        LocaleContextHolder.setLocale(locale);
    }

}
