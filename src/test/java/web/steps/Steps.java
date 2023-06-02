package web.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConnectionManager;
import utils.ConfigFileReader;
import utils.Parser;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import static constants.QueryConstants.ADD_RESULTS;


public class Steps {
    Parser parser = new Parser();
    public static WebDriverWait wait;
    public static RemoteWebDriver driver;
    static final ConfigFileReader configFileReader = new ConfigFileReader();
    static final ConnectionManager connectionManager = new ConnectionManager(configFileReader);

    public WebElement getElement(String elementKey) throws IOException, ParseException {
        String elementValue = parser.getElementKey(elementKey);
        By selector = bySelector(elementValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        return driver.findElement(selector);
    }

    private By bySelector(String selector) {
        if (selector.matches("^#[\\w-]+$")) {
            return By.id(selector.substring(1));
        } else if (selector.charAt(0) == '/' || selector.charAt(0) == '(' || selector.startsWith("./")) {
            return By.xpath(selector);
        } else {
            return By.cssSelector(selector);
        }
    }

   @After
    public void tearDown() {
        try {
            driver.close();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }

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

    @Then("I fetch jobs")
    public void iFetchJobs() {
        takeJobs();
    }

    private void takeJobs() {
        int openJobs = driver.findElements(By.xpath("//*[@id=\"results\"]/div/section/div[2]/div")).size();
        for (int i = 1; i <= openJobs; i++) {
            String a = "//*[@id=\"results\"]/div/section/div[2]/div[";
            String department = driver.findElement(By.xpath(a + i + "]/div[1]/span/a")).getText();
            String jobTitle = driver.findElement(By.xpath(a+ i + "]/div[1]/h2/a")).getText();
            String location = driver.findElement(By.xpath(a + i + "]/div[1]/p[1]")).getText();
            connectionManager.executeQuery(String.format(ADD_RESULTS, department, jobTitle, location));
        }
    }

    @And("I click \"([^\"]*)\"")
    public void iClick(String elementKey) throws IOException, ParseException {
        if (getElement(elementKey) != null) {
            By selector = bySelector(parser.getElementKey(elementKey));
            wait.until(ExpectedConditions.elementToBeClickable(selector));
            getElement(elementKey).click();
        } else {
            driver.findElement(By.xpath("//*[text()='" + elementKey + "']")).click();
        }
    }
}

