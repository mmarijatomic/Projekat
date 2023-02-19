package tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BasicTest {
    protected WebDriver driver;
    protected String baseUrl = " https://vue-demo.daniel-avellaneda.com ";
    protected WebDriverWait wait;
    protected CitiesPage citiesPage;
    protected LoginPage loginPage;
    protected MessagePopUpPage messagePopUpPage;
    protected NavPage navPage;
    protected SignupPage signupPage;

    @BeforeClass
    public void beforeClass () {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        this.driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
        this.citiesPage = new CitiesPage(driver,wait);
        this.loginPage = new LoginPage(driver,wait);
        this.messagePopUpPage = new MessagePopUpPage(driver,wait);
        this.navPage = new NavPage(driver,wait);
        this.signupPage = new SignupPage(driver,wait);

    }
    @BeforeMethod
    public void beforeMethod () {
        driver.get(baseUrl);
    }
    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException, InterruptedException {
        if (result.getStatus() == ITestResult.FAILURE) {
            File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("dd-MM-yyyy__hh-mm-ss").format(new Date());
            Files.copy(file.toPath(),
                    new File("screenshots/" + result.getName() + " - " + timestamp + ".png").toPath());
        }
    }
    @AfterClass
    public void afterClass() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }
}

