package Tests;

import Constants.DriverFactory;
import Constants.ExtentManager;
import Pages.BasePage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;
    BasePage basePage;


    @AfterMethod
    protected void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                test.log(LogStatus.FAIL, test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/" + basePage.random())));
            } catch (NullPointerException e) {
                System.out.println("Some Driver is not used");
            }

            test.log(LogStatus.FAIL, result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            test.log(LogStatus.PASS, "Test passed");
        }

        extent.endTest(test);
        extent.flush();
        try {
            driver.quit();
        } catch (NullPointerException e) {
            System.out.println("Some Driver is not used");
        }

    }

    @BeforeMethod
    protected void beforeMethod() {
        driver = new DriverFactory().getDriver();
       driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.manage().window().maximize();

       // Point targetPosition = new Point(0, 0);
        //driver.manage().window().setPosition(targetPosition);

       // Dimension targetSize = new Dimension(1600, 1200); //your screen resolution here
        //driver.manage().window().setSize(targetSize);
    }

    @BeforeClass
    public void M1() {
        extent = ExtentManager.Instance();
        basePage = new BasePage(driver);
    }

    @AfterClass
    public void tear() throws Exception {
        extent.endTest(test);
        extent.flush();
        extent.close();
    }

}
