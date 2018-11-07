package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//label[contains(.,'Welcome')]")
    private WebElement welcomeLabel;

    @FindBy(xpath = "//input[@id='inpName']")
    private WebElement userNameInputTextField;

    @FindBy(xpath = "//input[@id='inpPassword']")
    private WebElement passwordInputTextField;

    @FindBy(xpath = "//input[@id='btnLogin']")
    private WebElement loginButton;

    @FindBy(xpath = "//li[@class='current_user_name']")
    private WebElement loggedUserTitle;

    public void positiveLogin(String password, String userName){
        waitVisibilityOfElement(welcomeLabel);
        userNameInputTextField.click();
        userNameInputTextField.sendKeys(userName);
        passwordInputTextField.click();
        passwordInputTextField.sendKeys(password);
        loginButton.click();
    }

    public String getLoggedUserTitle() {
        waitVisibilityOfElement(loggedUserTitle);
        return loggedUserTitle.getText();
    }
}
