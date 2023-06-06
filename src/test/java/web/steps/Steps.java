package web.steps;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConnectionManager;
import utils.ConfigFileReader;
import static constants.QueryConstants.*;
import java.util.concurrent.TimeUnit;


public class Steps {
    public static WebDriverWait wait;
    public static RemoteWebDriver driver;
    static final ConfigFileReader configFileReader = new ConfigFileReader();
    static final ConnectionManager connectionManager = new ConnectionManager(configFileReader);
    String jobBody = "//*[@id='results']/div/section/div[2]/div[";
    String openJobs = "//*[@id='results']/div/section/div[2]/div";
    String nextButton = "//li[contains(@class,'page-next')]//a";


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


    @And("I open Tipico Careers page")
    public void iOpenTipicoCareersPage() {
        driver.navigate().to("https://www.tipico-careers.com/en/jobs/");
    }

    @And("I click acceptCookies")
    public void iClickAcceptCookies() {
        driver.findElement(By.id("_evidon-accept-button")).click();
    }

    @Then("I fetch jobs")
    public void iFetchJobs() throws  Exception {
        takeJobs();
    }

    private void takeJobs() throws  Exception {
        boolean isPageExists = true;
        int nextPageIndex = 2;
        while (isPageExists) {
            int openJobsCount = driver.findElements(By.xpath(openJobs)).size();
            for (int i = 1; i <= openJobsCount; i++) {
                String department = driver.findElement(By.xpath(jobBody + i + "]/div[1]/span/a")).getText();
                String jobTitle = driver.findElement(By.xpath(jobBody + i + "]/div[1]/h2/a")).getText();
                String location = driver.findElement(By.xpath(jobBody + i + "]/div[1]/p[1]")).getText();
                connectionManager.executeQuery(String.format(ADD_RESULTS, department, jobTitle, location));
            }
            try {
                driver.findElement(By.xpath(nextButton));
                driver.navigate().to("https://www.tipico-careers.com/en/jobs/?page=" + nextPageIndex + "#results");
            } catch (NoSuchElementException e) {
                isPageExists = false;
            }
            nextPageIndex++;
        }
        connectionManager.close();
    }
}