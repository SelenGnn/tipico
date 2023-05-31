package web.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConnectionManager;
import utils.ConfigFileReader;
import utils.Parser;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static constants.QueryConstants.ADD_RESULTS;


public class Steps {
    Parser parser = new Parser();
    public static WebDriverWait wait;
    public static RemoteWebDriver driver;
    static final ConfigFileReader configFileReader = new ConfigFileReader();
    static final ConnectionManager connectionManager = new ConnectionManager(configFileReader);

   /* @After
    public void tearDown() {
        try {
            driver.close();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }*/

    @Given("I open browser")
    public void iOpenBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @And("I maximize browser")
    public void iMaximizeBrowser() {
        driver.manage().window().maximize();
    }


    @And("I open \"([^\"]*)\" page")
    public void iOpenPage(String pageKey) throws IOException, ParseException {
        parser.setPageKey(pageKey);
        driver.get(parser.getPageObject("urlKey"));
    }

    @When("I fetch jobs")
    public void iFetchJobs() {
        int openJobs = driver.findElements(By.xpath("//*[@id=\"results\"]/div/section/div[2]/div")).size();
        for (int i = 1; i <= openJobs; i++) {
            String department = driver.findElement(By.xpath("//*[@id=\"results\"]/div/section/div[2]/div[" + i + "]/div[1]/span/a")).getText();
            String jobTitle = driver.findElement(By.xpath("//*[@id=\"results\"]/div/section/div[2]/div[" + i + "]/div[1]/h2/a")).getText();
            String location = driver.findElement(By.xpath("//*[@id=\"results\"]/div/section/div[2]/div[" + i + "]/div[1]/p[1]")).getText();
        }
    }
        @And("I accept Cookies")
        public void iAcceptCookies () {
            driver.findElement(By.id("_evidon-accept-button")).click();
        }
    }

