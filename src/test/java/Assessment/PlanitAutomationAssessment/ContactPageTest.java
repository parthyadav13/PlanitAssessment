package Assessment.PlanitAutomationAssessment;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.ContactPage;
import pageObjects.HomePage;
import resources.Base;
import resources.GlobalValues;
import reusableFunctions.ElementIsVisible;
import reusableFunctions.ReadDataFromExcel;

public class ContactPageTest extends Base {
	GlobalValues globValue = new GlobalValues();

//Test case to navigate to the base url
	@BeforeTest
	public void basePageNavigation() throws IOException {
		driver = initialiseDriver();
		Properties data = globValue.PropertyFile();
		String url = data.getProperty("url");
		driver.get(url);

	}

//Test case to Navigate on Contact page(pre-requisite)
	@Test
	public void navigateToContact() {

		HomePage homePage = new HomePage(driver);
		WebElement topNavigation = homePage.TopNavigation();

		List<WebElement> links = topNavigation.findElements(By.tagName("li"));
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).getText().equalsIgnoreCase("contact")) {
				links.get(i).click();
			}
		}
	}

// TestCase 1 (Validating Error messages for mandatory fields)

	@Test(priority = 1, dependsOnMethods = { "navigateToContact" }, dataProvider = "readData")
	public void validateErrorMessages(String forename, String email, String message) {
		ContactPage contactPage = new ContactPage(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);

// error messages to validate
		String expectedForeNameError = "Forename is required";
		String expectedEmailError = "Email is required";
		String expectedMessageError = "Message is required";

// validate that the error messages are displayed as expected
		wait.until(ExpectedConditions.visibilityOf(contactPage.Submit())).click();

		assertTrue(wait.until(ExpectedConditions.visibilityOf(contactPage.ForeNameError())).getText()
				.equals(expectedForeNameError));
		assertTrue(contactPage.EmailError().getText().equals(expectedEmailError));
		assertTrue(contactPage.MessageError().getText().equals(expectedMessageError));

// validate error messages are displayed or not after providing values
		contactPage.ForeName().sendKeys(forename);
		boolean errorMessageDisplayedForeName = ElementIsVisible.IsVisible(contactPage.ForeNameErrorList());
		assertTrue(errorMessageDisplayedForeName);

		contactPage.Email().sendKeys(email);
		boolean errorMessageDisplayedEmail = ElementIsVisible.IsVisible(contactPage.EmailErrorList());
		assertTrue(errorMessageDisplayedEmail);

		contactPage.Message().sendKeys(message);
		boolean errorMessageDisplayedMessage = ElementIsVisible.IsVisible(contactPage.MessageErrorList());
		assertTrue(errorMessageDisplayedMessage);

		contactPage.Submit().click();

		wait.until(ExpectedConditions.visibilityOf(contactPage.Back())).click();
	}

// TestCase 2(Positive scenarios for 5 sets of data)

	@Test(priority = 2, dependsOnMethods = { "navigateToContact" }, dataProvider = "readDataAll")
	public void successfullSubmition(String forename, String email, String message) throws InterruptedException {
		ContactPage contactPage = new ContactPage(driver);
		WebDriverWait wait = new WebDriverWait(driver, 40);

		// validate message
		String expectedMessage = "Thanks " + forename + ", we appreciate your feedback.";

		// validate error messages are displayed or not after providing values
		wait.until(ExpectedConditions.visibilityOf(contactPage.ForeName())).sendKeys(forename);

		wait.until(ExpectedConditions.visibilityOf(contactPage.Email())).sendKeys(email);

		wait.until(ExpectedConditions.visibilityOf(contactPage.Message())).sendKeys(message);

		wait.until(ExpectedConditions.visibilityOf(contactPage.Submit())).click();
		// Thread.sleep(40000);
		String actualMessage = wait.until(ExpectedConditions.visibilityOf(contactPage.SuccessMessage())).getText();
		assertTrue(actualMessage.equals(expectedMessage));

		contactPage.Back().click();
	}

	@DataProvider
	public Object[][] readDataAll() throws IOException {
		Object data[][] = new Object[5][3];
		ReadDataFromExcel readdata = new ReadDataFromExcel();
		List<List<Object>> excelData = readdata.ReadDataAll("testdata", "TestCase");
		for (int i = 0; i < 5; i++) {
			for (int j = 1; j < 4; j++) {
				data[i][j - 1] = excelData.get(i).get(j);
			}
		}
		return data;
	}

	@DataProvider
	public Object[][] readData() throws IOException {
		Object data[][] = new Object[1][3];

		List<String> excelData = ReadDataFromExcel.ReadData("TC3");

		for (int j = 1; j < 4; j++) {
			data[0][j - 1] = excelData.get(j);
		}
		return data;

	}

	@AfterTest
	public void CloseBrowser() {
		driver.close();
	}

}
