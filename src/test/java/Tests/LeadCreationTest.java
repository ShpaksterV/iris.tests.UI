package Tests;

import Constants.ExtentManager;
import Constants.URLs;
import Constants.Variables;
import Pages.BasePage;
import Pages.IrisToolbar;
import Pages.LeadPage;
import Pages.LoginPage;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LeadCreationTest extends BaseTest {

    private LoginPage loginPage;
    private LeadPage leadPage;
    private IrisToolbar irisToolbar;
    private BasePage basePage;

    @BeforeMethod
    public void setUp() {
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
        irisToolbar = new IrisToolbar(driver);
        leadPage = new LeadPage(driver);
    }


    @Test
    private void irisBasicUserLeadsChecking() {
        extent.addSystemInfo("Resolution", basePage.getWindowHeight() + "X" + basePage.getWindowWidth());
        driver.get(URLs.irisDevURL);
        test = extent.startTest(BasePage.Data()
                        + "IRIS CRM Lead Creation by Basic User permissions package",
                "Try to view and create lead using insufficient permissions.")
                .assignCategory("IRIS DEV", "Stage1")
                .assignAuthor("Vladislav");

        loginPage.positiveLogin(Variables.allUsersPassword, Variables.basicUserName);
        Assert.assertEquals(loginPage.getLoggedUserTitle(), Variables.basicUserLoggedTitle);
        test.log(LogStatus.INFO, "Basic User is logged in."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/" + basePage.random())));
//Check availability of "Leads" and "New Lead" buttons and if button is present quit the test.
        if (irisToolbar.allUserLeadButtonChecking()) {
            driver.quit();
        }
//Trying to open the lead from ISO group that shouldn't be available for this user class
        driver.get(URLs.viewISOGroupLeadPage);
        Assert.assertTrue(leadPage.getErrorMessage().contains(Variables.notAuthorizedError));
        test.log(LogStatus.INFO, "Lead form isn't available for view cause of Access Denied."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/" + basePage.random())));
// Trying to open the New Lead page by the link wich is unavailable for this UC
        driver.get(URLs.newLeadPageLink);
        leadPage.newLeadCreation();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.insufficientPremissionError);

        test.log(LogStatus.INFO, "Insufficient permission error is shown."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/" + basePage.random())));
    }

    @Test
    private void irisAdvancedUserLeadsChecking() {
        extent.addSystemInfo("Resolution", basePage.getWindowHeight() + "X" + basePage.getWindowWidth());
        driver.get(URLs.irisDevURL);
        test = extent.startTest(BasePage.Data()
                        + "IRIS CRM Lead Creation by Basic User permissions package",
                "Trying to create and view lead with Groupwide access permissions")
                .assignCategory("CommandLine", "Stage1")
                .assignAuthor("Vladislav");

        loginPage.positiveLogin(Variables.allUsersPassword, Variables.advancedUserName);
        Assert.assertEquals(loginPage.getLoggedUserTitle(), Variables.advancedUserLoggedTitle);
        test.log(LogStatus.INFO, "Advanced User is logged in."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/" + basePage.random())));
//Check availability of "Leads" and "New Lead" buttons and if buttons are not present quit the test.
        if (!irisToolbar.allUserLeadButtonChecking()) {
            driver.quit();
        }
//Check that this UC can create users only for his related group;
        irisToolbar.newLeadToolbarButtonClick();
        Assert.assertTrue(leadPage.getListOfGroupsForSelectionOnNEwLead().size() == 1);
        Assert.assertEquals(leadPage.getSelectedGroupOnNewLeadForm(), Variables.advancedUserGroup);
        test.log(LogStatus.INFO, "Only one group is available in a dropdown and it's a group Advanced user is linked to."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

// Checking that tab on the lead form is available for this UC and it's selecting and checking fields permissions
        if (!leadPage.allUserElementAvailabilityChecking(leadPage.availableForEveryRoleTab)) {
            driver.quit();
        }
        leadPage.tabSelecting(leadPage.availableForEveryRoleTab);
        test.log(LogStatus.INFO, "Available tab is displayed and selected."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Trying to create the lead without filling the required fields
        leadPage.newLeadCreation();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.leadCreationRequiredFieldError);
        test.log(LogStatus.INFO, "Error message is shown while trying to save lead without filling data to required fields."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        leadPage.requiredFieldOnTabFilling(Variables.defaultTextValue);

//Opening the available for this role RecordSet and checking fields permissions
        leadPage.tabSelecting(leadPage.availableForeveryRoleRecordset);
        leadPage.addRecordSetButtonClick();

//Trying to create the lead without filling the required fields
        leadPage.newLeadCreation();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.leadCreationRequiredFieldError);
        test.log(LogStatus.INFO, "Error message is shown while trying to save lead without filling data to required fields."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        leadPage.requiredFieldOnRecordSetFilling(Variables.defaultTextValue);
//Lead saving and checking the date creation is today
        leadPage.newLeadCreation();
        Assert.assertTrue(leadPage.getLeadCreatedDateStatusSavedbuttonArea().contains("Created: " + basePage.getTodaysDate()));
        test.log(LogStatus.INFO, "After lead creation this Lead Page is opened with today date creation."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));



        //Tabs Checking on created lead (if needed can be moved to separate test)

// Checking that tab on the lead form is available for this UC and it's selecting and checking fields permissions
        if (!leadPage.allUserElementAvailabilityChecking(leadPage.availableForEveryRoleTab)) {
            driver.quit();
        }
        leadPage.tabSelecting(leadPage.availableForEveryRoleTab);
        test.log(LogStatus.INFO, "Available tab is visible for the set of this permissions and selected."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
//Checking that tab on the lead form isn't available as required
        if (leadPage.allUserElementAvailabilityChecking(leadPage.notAvailableByPermissionsTab)) {
            driver.quit();
        }
        test.log(LogStatus.INFO, "Not available tab isn't visible for the set of this permissions."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Checking that tab has read only field
        if (leadPage.readOnlyFieldAvailability()) {
            driver.quit();
        }
        test.log(LogStatus.INFO, "Read Only field is displayed."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
//Clearing previously saved data from the Required field and trying to save the lead getting and asserting the error
        leadPage.requiredFieldOnTabClearing();
        leadPage.leadSaveButtonClick();
        leadPage.getPnotifyMessageText();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.leadCreationRequiredFieldError);
        test.log(LogStatus.INFO, "User can't save lead without filling the required field "
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
//Filling the data to the required field and checking clocking on Record Set where checking unavailability to save the lead without required field filled and
// then successfully lead saving.
        leadPage.requiredFieldOnTabFilling(Variables.defaultTextValue);
        leadPage.tabSelecting(leadPage.availableForeveryRoleRecordset);
        leadPage.requiredFieldOnRecordSetClearing();
        leadPage.leadSaveButtonClick();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.leadCreationRequiredFieldError);
        test.log(LogStatus.INFO, "User can't save lead without filling the required field "
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        leadPage.requiredFieldOnRecordSetFilling(Variables.defaultTextValue);
        leadPage.leadSaveButtonClick();
        leadPage.waitInvisibilityOfPnotifyMessage();

//Checking that created lead has this UC assigned by Default
        Assert.assertTrue(leadPage.getAssignedUsersNamesAndUserClasses().toString().contains(Variables.advancedUserLoggedTitle));
        test.log(LogStatus.INFO, "Advanced assigned to the lead by default after creation."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Assigning some users, checking available to assign user list and its' size
        leadPage.assignToUserDropDownClick();
        test.log(LogStatus.INFO, "Advanced user can only assign users from his group 'Negan'."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        leadPage.getUsersInDropdownList();
        Assert.assertTrue(leadPage.getUsersInDropdownQuantity() == Variables.neganGroupUsersQuantity);
//        Assert.assertEquals(leadPage.getUsersInDropdownList(),(Variables.neganGtoupActiveUserNames));

//Assigning user and checking that selected is assigned
        leadPage.randomUserChosingFromTheDropDown();
        String assignedUsersNameOnTheDropDown = leadPage.assignedUserDropDownGetText();
        leadPage.assignUsersToLeadButtonClick();
        Assert.assertEquals(leadPage.getSuccessfullyAssignedLeadMessage(), Variables.successfullyAssignedLeadMessage);
        Assert.assertTrue(leadPage.getAssignedUsersNamesAndUserClasses().toString().contains(assignedUsersNameOnTheDropDown));
        test.log(LogStatus.INFO, "User is  successfully assigned to the lead."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Randomly unassaigning user checking the messages of successfully unassigning and that user is no more visible as assigned in the list of assigned users
        leadPage.randomUnassignButtonClick();
        driver.switchTo().alert().accept();
        test.log(LogStatus.INFO, "User is  successfully unassigned from the lead."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        Assert.assertEquals(leadPage.getSuccessfullyAssignedLeadMessage(), Variables.successfullyUnassignedUseerMessage);
        leadPage.leadSaveButtonClick();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.successfullySavedLeadMessage);
        test.log(LogStatus.INFO, "User successfully saved the lead. "
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Trying to reach the user not related to the Group of this GroupWide permission user class and error message asserting
        driver.get(URLs.viewISOGroupLeadPage);
        Assert.assertTrue(leadPage.getErrorMessage().contains(Variables.notAuthorizedError));
        test.log(LogStatus.INFO, "When trying to access to the user not from our group the 'Not authorized' error message is shown"
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
    }

    @Test
    public void adminUserLeadsChecking() throws InterruptedException {
        extent.addSystemInfo("Resolution", basePage.getWindowHeight() + "X" + basePage.getWindowWidth());
        driver.get(URLs.irisDevURL);
        test = extent.startTest(BasePage.Data()
                        + "IRIS CRM Lead Creation by Basic User permissions package",
                "Trying to create and view lead with Groupwide access premissions")
                .assignCategory("CommandLine", "Stage1")
                .assignAuthor("Vladislav");

        loginPage.positiveLogin(Variables.allUsersPassword, Variables.adminUserName);
        Assert.assertEquals(loginPage.getLoggedUserTitle(), Variables.adminUserLoggedTitle);
        test.log(LogStatus.INFO, "Admin User is logged in."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/" + basePage.random())));

//Check availability of "Leads" and "New Lead" buttons and if buttons are not present quit the test.
        if (!irisToolbar.allUserLeadButtonChecking()) {
            driver.quit();
        }
        irisToolbar.newLeadToolbarButtonClick();

//Check that this UC can create users related to every group
        Assert.assertTrue(leadPage.getListOfGroupsForSelectionOnNEwLead().size() >= 10);
        test.log(LogStatus.INFO, "User with Systemwide Leads access have the possibility to create leads for any group."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

// Checking that tab and recordset on the lead form is available for this UC and tab selecting and checking fields permissions
        if (!leadPage.allUserElementAvailabilityChecking(leadPage.availableForEveryRoleTab)) {
            driver.quit();
        }
        leadPage.tabSelecting(leadPage.availableForEveryRoleTab);
        test.log(LogStatus.INFO, "Available tab is displayed and selected."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        if (!leadPage.allUserElementAvailabilityChecking(leadPage.availableForeveryRoleRecordset)) {
            driver.quit();
        }
        test.log(LogStatus.INFO, "Available recordset is displayed."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Trying to create the lead without filling the required fields
        leadPage.newLeadCreation();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.leadCreationRequiredFieldError);
        test.log(LogStatus.INFO, "Error message is shown while trying to save lead without filling data to required fields."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        leadPage.requiredFieldOnTabFilling(Variables.defaultTextValue);

//Opening the available for this role RecordSet and checking fields permissions
        leadPage.tabSelecting(leadPage.availableForeveryRoleRecordset);
        leadPage.addRecordSetButtonClick();

//Trying to create the lead without filling the required fields
        leadPage.newLeadCreation();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.leadCreationRequiredFieldError);
        test.log(LogStatus.INFO, "Error message is shown while trying to save lead without filling data to required fields."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        leadPage.requiredFieldOnRecordSetFilling(Variables.defaultTextValue);

//Lead saving and checking the date creation is today
        leadPage.newLeadCreation();
        Assert.assertTrue(leadPage.getLeadCreatedDateStatusSavedbuttonArea().contains("Created: " + basePage.getTodaysDate()));

        //Tabs Checking on created lead (if needed can be moved to separate test)

// Checking that tab on the lead form is available for this UC and it's selecting and checking fields permissions
        if (!leadPage.allUserElementAvailabilityChecking(leadPage.availableForEveryRoleTab)) {
            driver.quit();
        }
        leadPage.tabSelecting(leadPage.availableForEveryRoleTab);
        test.log(LogStatus.INFO, "Available tab is visible for the set of this permissions and selected."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));


//Checking that tab on the lead form isn't available as required
        if (leadPage.allUserElementAvailabilityChecking(leadPage.notAvailableByPermissionsTab)) {
            driver.quit();
        }
        test.log(LogStatus.INFO, "Not available tab isn't visible for the set of this permissions."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Checking that tab has read only field
        if (leadPage.readOnlyFieldAvailability()) {
            driver.quit();
        }
        test.log(LogStatus.INFO, "Read Only field is displayed."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Clearing previously saved data from the Required field and trying to save the lead getting and asserting the error
        leadPage.requiredFieldOnTabClearing();
        leadPage.leadSaveButtonClick();
        leadPage.getPnotifyMessageText();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.leadCreationRequiredFieldError);
        test.log(LogStatus.INFO, "User can't save lead without filling the required field "
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
//Filling the data to the required field and checking clocking on Record Set where checking unavailability to save the lead without required field filled and
// then successfully lead saving.
        leadPage.requiredFieldOnTabFilling(Variables.defaultTextValue);
        leadPage.tabSelecting(leadPage.availableForeveryRoleRecordset);
        leadPage.requiredFieldOnRecordSetClearing();
        leadPage.leadSaveButtonClick();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.leadCreationRequiredFieldError);
        test.log(LogStatus.INFO, "User can't save lead without filling the required field "
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        leadPage.requiredFieldOnRecordSetFilling(Variables.defaultTextValue);
        leadPage.waitInvisibilityOfPnotifyMessage();

//Checking that created lead has this UC assigned by Default
        Assert.assertTrue(leadPage.getAssignedUsersNamesAndUserClasses().toString().contains(Variables.adminUserLoggedTitle));
        test.log(LogStatus.INFO, "Admin assigned to the lead by default."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Opening the list of users that can be assigned and random choosing user from it and successfully assigning
        leadPage.assignToUserDropDownClick();
        leadPage.randomUserChosingFromTheDropDown();
        String assignedUsersNameOnTheDropDown = leadPage.assignedUserDropDownGetText();
        leadPage.assignUsersToLeadButtonClick();
        Assert.assertEquals(leadPage.getSuccessfullyAssignedLeadMessage(), Variables.successfullyAssignedLeadMessage);
        Assert.assertTrue(leadPage.getAssignedUsersNamesAndUserClasses().toString().contains(assignedUsersNameOnTheDropDown));
        test.log(LogStatus.INFO, "User is  successfully assigned to the lead."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

//Saving the lead
        leadPage.leadSaveButtonClick();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.successfullySavedLeadMessage);
        test.log(LogStatus.INFO, "Lead is  successfully saved."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        
//Randomly uassigning the user from the lead and checking that it was unassigned
        leadPage.randomUnassignButtonClick();
        driver.switchTo().alert().accept();
        test.log(LogStatus.INFO, "User is  successfully unassigned from the lead."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        Assert.assertEquals(leadPage.getSuccessfullyAssignedLeadMessage(), Variables.successfullyUnassignedUseerMessage);
        leadPage.leadSaveButtonClick();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.successfullySavedLeadMessage);
        Assert.assertFalse(leadPage.getAssignedUsersNamesAndUserClasses().toString().contains(leadPage.getUnassignedUserName()));

    }

    @Test
    public void associatedUserLeadCreationAndUsersAssigning() {
        extent.addSystemInfo("Resolution", basePage.getWindowHeight() + "X" + basePage.getWindowWidth());
        driver.get(URLs.irisDevURL);
        test = extent.startTest(BasePage.Data()
                        + "IRIS CRM Lead Creation by Basic User permissions package",
                "Trying to create and view lead with Groupwide access permissions")
                .assignCategory("CommandLine", "Stage1")
                .assignAuthor("Vladislav");

        loginPage.positiveLogin(Variables.allUsersPassword, Variables.basicUserWithAssociatedLeads);
        Assert.assertEquals(loginPage.getLoggedUserTitle(), Variables.basicUserWithAssociatedLeadsLoggedTitle);
        test.log(LogStatus.INFO, "Advanced User is logged in."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/" + basePage.random())));

        if (!irisToolbar.allUserLeadButtonChecking()) {
            driver.quit();
        }
        irisToolbar.newLeadToolbarButtonClick();
        leadPage.newLeadCreation();
        Assert.assertTrue(leadPage.getAssignedUsersNamesAndUserClasses().toString().contains(Variables.basicUserWithAssociatedLeadsLoggedTitle));
        test.log(LogStatus.INFO, "Advanced assigned to the lead by default after creation."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

        leadPage.assignToUserDropDownClick();

        test.log(LogStatus.INFO, "Advanced user can only assign users from his group 'Negan'."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        leadPage.getUsersInDropdownList();
        Assert.assertTrue(leadPage.getUsersInDropdownQuantity() == Variables.basicAssociatedUserAssignedToLeadUsersQuantity);
        Assert.assertEquals(leadPage.getUsersInDropdownList(), (Variables.basicAssociatedUserAssignedUserNames));

        leadPage.randomUserChosingFromTheDropDown();

        String assignedUsersNameOnTheDropDown = leadPage.assignedUserDropDownGetText();
        leadPage.assignUsersToLeadButtonClick();

        Assert.assertEquals(leadPage.getSuccessfullyAssignedLeadMessage(), Variables.successfullyAssignedLeadMessage);
        Assert.assertTrue(leadPage.getAssignedUsersNamesAndUserClasses().toString().contains(assignedUsersNameOnTheDropDown));
        test.log(LogStatus.INFO, "User is  successfully assigned to the lead."
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));
        try {
            leadPage.randomUnassignButtonClick();
        } catch (IllegalArgumentException ex) {
            test.log(LogStatus.INFO, "this user class has no permissions to unasign users from the lead"
                    + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                    + basePage.random())));
        }
        leadPage.leadSaveButtonClick();
        Assert.assertEquals(leadPage.getPnotifyMessageText(), Variables.successfullySavedLeadMessage);
        test.log(LogStatus.INFO, "User successfully saved the lead. "
                + test.addScreenCapture(ExtentManager.CaptureScreen(driver, "./images/"
                + basePage.random())));

    }
}
