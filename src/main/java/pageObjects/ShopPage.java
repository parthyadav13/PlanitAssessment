package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ShopPage {

	WebDriver driver;

	public ShopPage(WebDriver driver) {
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//*[text()='Buy']")
	List<WebElement> buy;

	@FindBy(className = "pull-right")
	WebElement rightMenu;

	@FindBy(css = "span.cart-count")
	WebElement cartCount;

	@FindBy(css = "table.table td:nth-child(1)")
	List<WebElement> table;

	@FindBy(css = "input.input-mini")
	List<WebElement> quantity;

	@FindBy(xpath = "//a[contains(text(),'Empty Cart')]")
	WebElement emptyCart;

	@FindBy(xpath = "//a[contains(text(),'Yes')]")
	WebElement yesButton;

	@FindBy(css = "h4.product-title")
	List<WebElement> products;

	@FindBy(css = "table.table td:nth-child(4)")
	List<WebElement> subTotal;

	@FindBy(css = "table.table td:nth-child(2)")
	List<WebElement> price;

	public List<WebElement> Buy() {
		return buy;
	}

	public WebElement RightMenu() {
		return rightMenu;
	}

	public WebElement CartCount() {
		return cartCount;
	}

	public List<WebElement> Table() {
		return table;
	}

	public List<WebElement> Quantity() {
		return quantity;
	}

	public List<WebElement> Price() {
		return price;
	}

	public List<WebElement> SubTotal() {
		return subTotal;
	}

	public WebElement EmptyCart() {
		return emptyCart;
	}

	public WebElement YesButton() {
		return yesButton;
	}

	public List<WebElement> Products() {
		return products;
	}
}
