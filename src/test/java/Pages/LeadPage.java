package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;


public class LeadPage extends BasePage {
    public LeadPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(xpath = "//button[@id][contains(.,'Create')]")
    private WebElement createNewLeadButton;

    @FindBy(xpath = "//div[@class='formdesc']")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@class[contains(.,'ui-pnotify-text')]]")
    private WebElement pnotifyPremissionError;

    @FindBy(xpath = "//div[@id='ddgroup_chosen']")
    private WebElement newLeadFormSelectGroupDropDown;

    @FindBy(xpath = "//a[@class='chosen-single'][ancestor::div[@id='ddgroup_chosen']]")
    private WebElement newLeadFromSelectedGroup;

    @FindBy(xpath = "//a[contains(.,'Available')][ancestor::li[@data-tabcategory]]")
    public WebElement availableForEveryRoleTab;

    @FindBy(xpath = "//div[@id[contains(.,'lead_action_save')]]")
    private WebElement leadCreatedDateStatusSavedbuttonArea;

    @FindBy(xpath = "//li/a[contains(.,'closed')]")
    public WebElement notAvailableByPermissionsTab;

    @FindBy(xpath = "//li/a[contains(.,'TabV')]")
    public WebElement availableForeveryRoleRecordset;

    @FindBy(xpath = "//li/a[contains(.,'Closed by')]")
    public WebElement notAvailableForEveryRoleRecordset;

    @FindBy(xpath = "//input[@class[contains(.,'requiredLeadField')]][ancestor::div[@class[contains(.,'lead-form')]]]")
    private WebElement requiredTextFieldOnTab;

    @FindBy(xpath = "//input[@class[contains(.,'requiredLeadField')]][ancestor::div[@class[contains(.,'accordionElement')]]]")
    private WebElement requiredTextFieldOnRecordset;

    @FindBy(xpath = "//button[contains(.,'Save')][@name='action']")
    private WebElement leadSaveButton;

    @FindBy(xpath = "//button[@name='addrecord'][contains(.,'recordSetTabV')]")
    private WebElement addRecordSetButton;

    @FindBy(xpath = "//h4[contains(.,'recordSetTabV')]")
    private WebElement recordsetAddedTab;

    @FindBy(xpath = "//div[@id[contains(.,'ddassignto_chosen')]]/a")
    private WebElement assignToUserDropDown;

    @FindBy(xpath = "//button[@id='btnAssign']")
    private WebElement assignUsersToLeadButton;

    @FindBy(xpath = "//img[@title='Unassign User'][ancestor::li]")
    private List<WebElement> unassignButton;


    @FindBy(xpath = "//div[@id='assignResponse'][@style[not(contains(.,'display'))]]")
    private WebElement seccessfullyAssignedLeadMessage;


    private String listOfGroupsInDropDown = "//li[ancestor::div[@id='ddgroup_chosen']]";

    private String readOnlyField = "input[type='text'][value]:disabled";

    private String assignedUsers = "//li[@userid][ancestor::div[descendant::legend[contains(.,'Assigned')]]]/span";

   private String assignToUsersAtDropDown = "//div[@id[contains(.,'ddassignto_chosen')]]//li[@data-option-array-index][not(contains(.,'All'))][not(contains(.,'--- Select'))]";


    private String unassignedUserName;
    private int usersInDropdownQuantity;





    public String getLeadCreatedDateStatusSavedbuttonArea() {
        return leadCreatedDateStatusSavedbuttonArea.getText();
    }

    public String getErrorMessage() {
        waitVisibilityOfElement(errorMessage);
        return errorMessage.getText();
    }


    public void newLeadCreation() {
        waitVisibilityOfElement(createNewLeadButton);
        clickOnElemenByJS(createNewLeadButton);

    }

    public String getPnotifyMessageText() {
        waitVisibilityOfElement(pnotifyPremissionError);
        return pnotifyPremissionError.getText();

    }

    public void waitInvisibilityOfPnotifyMessage() {
        waitUntilElementDisappear(pnotifyPremissionError);
    }


    public List<String> getListOfGroupsForSelectionOnNEwLead() {
        newLeadFormSelectGroupDropDown.click();
        return (listOfElements(listOfGroupsInDropDown));
    }

    public String getSelectedGroupOnNewLeadForm() {
        waitVisibilityOfElement(newLeadFromSelectedGroup);
        return newLeadFromSelectedGroup.getText();
    }

    public boolean allUserElementAvailabilityChecking(WebElement element) {
        try {
            waitVisibilityOfElement(element);
            return true;
        } catch (TimeoutException ex) {
            return false;
        }
    }

    public void tabSelecting(WebElement elementtoSelect) {
        clickOnElemenByJS(elementtoSelect);
    }

    public void requiredFieldOnTabFilling(String defaultValue) {
        waitVisibilityOfElement(requiredTextFieldOnTab);
        requiredTextFieldOnTab.click();
        requiredTextFieldOnTab.sendKeys(defaultValue);

    }

    public void requiredFieldOnTabClearing() {
        waitVisibilityOfElement(requiredTextFieldOnTab);
        requiredTextFieldOnTab.click();
        requiredTextFieldOnTab.clear();
    }

    public void requiredFieldOnRecordSetFilling(String defaultValue) {
        waitVisibilityOfElement(requiredTextFieldOnRecordset);
        requiredTextFieldOnRecordset.click();
        requiredTextFieldOnRecordset.sendKeys(defaultValue);
    }

    public void requiredFieldOnRecordSetClearing() {
        waitVisibilityOfElement(requiredTextFieldOnRecordset);
        requiredTextFieldOnRecordset.click();
        requiredTextFieldOnRecordset.clear();
    }

    public void leadSaveButtonClick() {
        waitVisibilityOfElement(leadSaveButton);
        leadSaveButton.click();
    }

    public boolean readOnlyFieldAvailability() {
        try {
            isElementPresentTrue(readOnlyField);
            return false;
        } catch (NoSuchElementException ex) {
            return true;
        }
    }


    public List<String> getAssignedUsersNamesAndUserClasses() {
        return listOfElements(assignedUsers);
    }

    public void assignToUserDropDownClick() {
        waitVisibilityOfElement(assignToUserDropDown);
        assignToUserDropDown.click();
    }

    public void addRecordSetButtonClick() {
        waitVisibilityOfElement(addRecordSetButton);
        addRecordSetButton.click();
        waitVisibilityOfElement(requiredTextFieldOnRecordset);
    }

    public void randomUserChosingFromTheDropDown() {
        List<WebElement> itemsDropDown =  new ArrayList<WebElement>((driver.findElements(By.xpath(assignToUsersAtDropDown))));
        int size = itemsDropDown.size();
        int randomNumber = ThreadLocalRandom.current().nextInt(0, size);
       moveToElementAndClick(itemsDropDown.get(randomNumber));

    }

    public String getUsersInDropdownList(){
        List<String> listOfNames = new ArrayList<String>();
        List<WebElement> userNamesInDropDown = driver.findElements(By.xpath(assignToUsersAtDropDown));
        this.usersInDropdownQuantity = userNamesInDropDown.size();
        for(WebElement w: userNamesInDropDown){
            listOfNames.add(String.valueOf(w.getText()));
        }
        return listOfNames.toString();
    }


    public int getUsersInDropdownQuantity() {
        return usersInDropdownQuantity;
    }

    public String assignedUserDropDownGetText() {
        return assignToUserDropDown.getText();
    }

    public void assignUsersToLeadButtonClick() {
        assignUsersToLeadButton.click();
    }

    public String getSuccessfullyAssignedLeadMessage() {
        waitVisibilityOfElement(seccessfullyAssignedLeadMessage);
        String messageText=seccessfullyAssignedLeadMessage.getText();
        waitUntilElementDisappear(seccessfullyAssignedLeadMessage);
        return messageText;


    }
    public void randomUnassignButtonClick() {
        List<WebElement> unassignButtonsList = unassignButton;
        List<String> assignedUsersList = listOfElements(assignedUsers);
        int size = unassignButtonsList.size();
        int randomNumber = ThreadLocalRandom.current().nextInt(0, size);
        WebElement randomUnassignButton = unassignButtonsList.get(randomNumber);
        this.unassignedUserName = assignedUsersList.get(randomNumber);
        randomUnassignButton.click();
    }

    public String getUnassignedUserName() {
        return unassignedUserName;
    }

}

