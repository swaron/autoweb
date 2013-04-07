package ca.esi.hd.test;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.seleniumemulation.IsElementPresent;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import ca.esi.hd.account.AccountService;
import ca.esi.hd.page.account.LoginPage;
import ca.esi.hd.support.ApplicationContextAccessor;

@Profile("dev")
@ContextConfiguration({ "classpath:/test-context.xml" })
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected WebDriver driver;
    @Autowired
    protected AccountService accountService;

    protected JdbcTemplate jdbcTemplate;
    
    public WebDriverWait getDriverWait() {
        return new WebDriverWait(driver, 20);
    }

    /**
     * Set the DataSource, typically provided via Dependency Injection.
     * 
     * @param dataSource the DataSource to inject
     */
    @Autowired
    @Qualifier("dataSource_webportal")
    public void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Value("${portal.url}")
    private String url;
    @Value("${snapshot.basedir}")
    private String snapshotBasePath;

    @BeforeSuite
    public static void initJndi() throws Exception {
        if (SimpleNamingContextBuilder.getCurrentContextBuilder() == null) {
            SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
            builder.bind("java:comp/env/main_location", "http://172.16.113.211:8090/");
            builder.activate();
            Assert.notNull(SimpleNamingContextBuilder.getCurrentContextBuilder());
        }
    }

    @BeforeClass
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    public void tryLoginIfNot() {
        By logoutLink = By.xpath("//div[@id='headcontainer']//a[contains(@href, 'logout')]");
        boolean elementPresent = isElementPresent(logoutLink);
        if (!elementPresent) {
            logger.info("trying to login with pre-defined username,password.");
        }
        // Navigate to the right place
        driver.get(toAbsolutePath("login.do"));
        // Create a new instance of the search page class
        // and initialise any WebElement fields in it.
        LoginPage login = PageFactory.initElements(driver, LoginPage.class);
        // And now do the search.
        login.login(accountService.getUsername(), accountService.getPassword());

        waitElementPresent(logoutLink, "logout link not found, failed to login");
    }

    public String toAbsolutePath(String path) {
        Assert.notNull(url);
        return URI.create(url).resolve(path).toString();
    }

    public String msg(String key) {
        return ApplicationContextAccessor.getMessageSourceAccessor().getMessage(key, LocaleContextHolder.getLocale());
    }

    protected boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitElementPresent(final By by, String desc) {
        try {
            getDriverWait().until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver d) {
                    WebElement element = d.findElement(by);
                    if (element.isDisplayed()) {
                        return element;
                    } else {
                        return null;
                    }
                }
            });
        } catch (TimeoutException e) {
            org.testng.Assert.fail(desc, e);
        }

    }

    public void waitElement(final By by, String desc) {
        try {
            getDriverWait().until(new ExpectedCondition<WebElement>() {
                @Override
                public WebElement apply(WebDriver d) {
                    return d.findElement(by);
                }
            });
        } catch (TimeoutException e) {
            org.testng.Assert.fail(desc, e);
        }
    }

    public void waitLocation(final String location) {
        try {
            getDriverWait().until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver d) {
                    return StringUtils.contains(driver.getCurrentUrl(), location);
                }
            });
        } catch (TimeoutException e) {
            org.testng.Assert.fail("Time out on waiting new page: " + location, e);
        }
    }

    protected void selectExtComboBoxFirstItem(String comboId) {
        By arrow = By.cssSelector(String.format("#x-form-el-%s img.x-form-trigger", comboId));
        driver.findElement(arrow).click();
        By item = By.cssSelector("div.x-combo-list > div.x-combo-list-inner > div.x-combo-list-item:first-child");
        waitElementPresent(item, "can't select a ext combobox because there are no list items.");
        driver.findElement(item).click();
    }

    public void takeScreenShot(String filename) {
        if (driver instanceof TakesScreenshot) {
            TakesScreenshot it = (TakesScreenshot) driver;
            byte[] screenshot = it.getScreenshotAs(OutputType.BYTES);
            String path = FilenameUtils.concat(snapshotBasePath, filename);
            try {
                File parent = new File(FilenameUtils.getFullPathNoEndSeparator(path));
                File file = new File(path);
                if (!parent.exists()) {
                    FileUtils.forceMkdir(parent);
                }
                FileUtils.writeByteArrayToFile(file, screenshot);
            } catch (IOException e) {
                logger.error("failed to take screen shot. writing file error.");
            }
        }
    }

    protected void waitForAndClickDailog(final String title, String buttonText) {
        String dialogTitle = String.format("//div[contains(@class, 'x-window-dlg')]//div[contains(child::text(),'%s')]",
                title);
        waitElementPresent(By.xpath(dialogTitle), "expect a ext dailog with title: " + title);
        String button = String.format("//div[contains(@class, 'x-window-dlg')]//div[contains(child::text(),'%s')]//following::button[contains(child::text(),'%s')]",
                title,buttonText);
        driver.findElement(By.xpath(button)).click();
    }
}
