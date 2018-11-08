package Constants;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    private WebDriver driver;

    public WebDriver getDriver(String browserType) throws MalformedURLException {
        String name = System.getProperty("driver");
        /*if (name.equals("chrome")) {

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            String chromeProfile = "C:\\Users\\User\\AppData\\Local\\Google\\Chrome\\User Data\\Default";
            ArrayList<String> switches = new ArrayList<String>();
            switches.add("--user-data-dir=" + chromeProfile);
            capabilities.setCapability("chrome.switches", switches);
            driver = new ChromeDriver(capabilities);

        }*/
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.64.4444/wd/hub"),capabilities);

        /*if (name.equals("firefox")) {
            this.driver = new FirefoxDriver();
        }*/
        this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
        return this.driver;
    }
}
