package Pages;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IrisToolbar extends BasePage {
    public IrisToolbar(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//li[@class[contains(.,'nav-leads')]]")
    private WebElement leadsButtonWithDropDown;

    @FindBy(xpath = "//li[@class[contains(.,'nav-newlead')]]")
    private WebElement newLeadButton;

    public boolean allUserLeadButtonChecking() {
        try {
            waitVisibilityOfElement(leadsButtonWithDropDown);
            waitVisibilityOfElement(newLeadButton);
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }
public void newLeadToolbarButtonClick(){
        newLeadButton.click();
}


}


