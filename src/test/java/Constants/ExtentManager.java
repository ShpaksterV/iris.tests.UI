package Constants;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ExtentManager {
    public static ExtentReports Instance()
    {
        ExtentReports extent;
        String Path = "./Report.html";
        System.out.println(Path);
        extent = new ExtentReports(Path, false, DisplayOrder.NEWEST_FIRST);
        extent.config()
                .documentTitle("Automation Report")
                .reportName("Regression");

        return extent;
    }
    public static String CaptureScreen(WebDriver driver, String ImagesPath)
    {
        TakesScreenshot oScn = (TakesScreenshot) driver;
        File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
        File oDest = new File(ImagesPath+".jpg");
        try {
            FileUtils.copyFile(oScnShot, oDest);
        } catch (IOException e) {System.out.println(e.getMessage());}
        return ImagesPath+".jpg";
    }
}
