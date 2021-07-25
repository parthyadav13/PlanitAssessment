package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactPage {

	// Class to store all locator inforamtion related to contact page

	WebDriver driver;

	public ContactPage(WebDriver driver) {
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//*[@id='forename']")
	WebElement foreName;

	@FindBy(xpath = "//*[@id='forename-err']")
	WebElement foreNameError;

	@FindBy(xpath = "//*[@id='forename-err']")
	List<WebElement> foreNameErrorList;

	@FindBy(xpath = "//*[@id='surname']")
	WebElement surName;

	@FindBy(xpath = "//*[@id='email']")
	WebElement email;

	@FindBy(xpath = "//*[@id='email-err']")
	WebElement emailError;

	@FindBy(xpath = "//*[@id='email-err']")
	List<WebElement> emailErrorList;

	@FindBy(xpath = "//*[@id='telephone']")
	WebElement telephone;

	@FindBy(xpath = "//*[@id='message']")
	WebElement message;

	@FindBy(xpath = "//*[@id='message-err']")
	WebElement messageError;

	@FindBy(xpath = "//*[@id='message-err']")
	List<WebElement> messageErrorList;

	@FindBy(xpath = "//*[text()='Submit']")
	WebElement submit;

	@FindBy(css = "div.alert-success")
	WebElement successMessage;

	@FindBy(xpath = "//a[contains(text(), 'Back')]")
	WebElement back;

	public WebElement ForeName() {
		return foreName;
	}

	public WebElement ForeNameError() {
		return foreNameError;
	}

	public List<WebElement> ForeNameErrorList() {
		return foreNameErrorList;
	}

	public WebElement SurName() {
		return surName;
	}

	public WebElement Email() {
		return email;
	}

	public WebElement EmailError() {
		return emailError;
	}

	public List<WebElement> EmailErrorList() {
		return emailErrorList;
	}

	public WebElement Telephone() {
		return telephone;
	}

	public WebElement Message() {
		return message;
	}

	public WebElement MessageError() {
		return messageError;
	}

	public List<WebElement> MessageErrorList() {
		return messageErrorList;
	}

	public WebElement Submit() {
		return submit;
	}

	public WebElement SuccessMessage() {
		return successMessage;
	}

	public WebElement Back() {
		return back;
	}

}
