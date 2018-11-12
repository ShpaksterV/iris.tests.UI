package Constants;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

    private WebDriver driver;

    public WebDriver getDriver()  {


            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            driver = new ChromeDriver(capabilities);



        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
        return this.driver;
    }
}
