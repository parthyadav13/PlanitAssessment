package Assessment.PlanitAutomationAssessment;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.ShopPage;
import resources.Base;
import resources.GlobalValues;
import reusableFunctions.ConvertDollarStringToFloat;
import reusableFunctions.ReadDataFromExcel;

public class ShopPageTest extends Base {
	GlobalValues globValue = new GlobalValues();

//Test case to navigate to the base url
	@BeforeTest
	public void basePageNavigation() throws IOException {
		driver = initialiseDriver();
		Properties data = globValue.PropertyFile();
		String url = data.getProperty("url");
		driver.get(url);

	}

	// Test case to Navigate on Contact page(pre-requisite)
	@Test
	public void navigateToShop() {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		HomePage homePage = new HomePage(driver);
		WebElement topNavigation = homePage.TopNavigation();

		List<WebElement> links = wait
				.until(ExpectedConditions.visibilityOfAllElements(topNavigation.findElements(By.tagName("li"))));
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).getText().equalsIgnoreCase("shop")) {
				links.get(i).click();
			}
		}
	}

//TestCase 3 TO verfy the product is added to the cart and checking the quantity after addition  

	@Test(priority = 1, dependsOnMethods = { "navigateToShop" }, dataProvider = "shopDataProduct")
	public void verifyShop(String bunny, String countBunny, String cow, String countCow) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 40);

		ShopPage shopPage = new ShopPage(driver);

		String[] expectedProducts = { bunny, cow };
		int countFluffyBunny = Integer.parseInt(countBunny);
		int countFunnyCow = Integer.parseInt(countCow);

		List<String> productsNeededList = Arrays.asList(expectedProducts);

		List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElements(shopPage.Products()));
		int countOfItems = 0;
		for (int i = 0; i < products.size(); i++) {
			String name = products.get(i).getText();

			if (productsNeededList.contains(name)) {

				if (name.contains(bunny)) {
					for (int b = 0; b < countFluffyBunny; b++) {
						wait.until(ExpectedConditions.visibilityOf(shopPage.Buy().get(i))).click();

						countOfItems++;
					}
				}
				if (name.contains(cow)) {
					for (int c = 0; c < countFunnyCow; c++) {
						wait.until(ExpectedConditions.visibilityOf(shopPage.Buy().get(i))).click();

						countOfItems++;
					}
				}
			}

		}

		WebElement rightMenu = shopPage.RightMenu();
		List<WebElement> rightMenuItem = rightMenu.findElements(By.tagName("li"));

		for (int j = 0; j < rightMenuItem.size(); j++) {

			if (rightMenuItem.get(j).getText().contains("Cart")) {
				wait.until(ExpectedConditions.visibilityOf(rightMenuItem.get(j))).click();

			}
		}

		String verifyCountOfItems = Integer.toString(countOfItems);
		assertTrue(
				wait.until(ExpectedConditions.visibilityOf(shopPage.CartCount())).getText().equals(verifyCountOfItems));

		// verify the details of items added in the cart
		List<WebElement> items = shopPage.Table();
		for (int a = 0; a < items.size(); a++) {
			String itemName = items.get(a).getText();

			if (productsNeededList.contains(itemName)) {
				if (itemName.contains(bunny)) {

					assertTrue(shopPage.Quantity().get(a).getAttribute("value").equals(countBunny));

				}

				if (itemName.contains(cow)) {

					assertTrue(shopPage.Quantity().get(a).getAttribute("value").equals(countCow));

				}

			}
		}

		wait.until(ExpectedConditions.visibilityOf(shopPage.EmptyCart())).click();

		java.util.Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		String subWindowHandler = null;
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler);
		System.out.println(subWindowHandler);
		driver.switchTo().window(subWindowHandler);
		wait.until(ExpectedConditions.visibilityOf(shopPage.YesButton())).click();

	}

//TestCase 4 TO verfity the cart table- price, subtotal and total with more number of items

	@Test(priority = 2, dependsOnMethods = { "navigateToShop" }, dataProvider = "cartDataProduct")
	public void verifyCart(String bunny, String priceOfBunny, String countOfBunny, String frog, String priceOfFrog,
			String countOfFrog, String bear, String priceOfBear, String countOfBear) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 20);

		ShopPage shopPage = new ShopPage(driver);

		ConvertDollarStringToFloat floatstr = new ConvertDollarStringToFloat();
		String[] expectedProducts = { frog, bunny, bear };
		int countFluffyBunny = Integer.parseInt(countOfBunny);
		int countStuffedFrog = Integer.parseInt(countOfFrog);
		int countValentineBear = Integer.parseInt(countOfBear);

		float price_bunny;
		float subtotal_bunny = 0;
		int count_bunny;

		float price_frog;
		float subtotal_frog = 0;
		int count_frog;

		float price_bear;
		float subtotal_bear = 0;
		int count_bear;

		List<String> productsNeededList = Arrays.asList(expectedProducts);

		HomePage homePage = new HomePage(driver);
		WebElement topNavigation = homePage.TopNavigation();

		List<WebElement> links = wait
				.until(ExpectedConditions.visibilityOfAllElements(topNavigation.findElements(By.tagName("li"))));
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).getText().equalsIgnoreCase("shop")) {
				links.get(i).click();
			}
		}
		List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElements(shopPage.Products()));
		int countOfItems = 0;
		for (int i = 0; i < products.size(); i++) {
			String name = products.get(i).getText();

			if (productsNeededList.contains(name)) {

				if (name.contains(bunny)) {
					for (int a = 0; a < countFluffyBunny; a++) {
						wait.until(ExpectedConditions.visibilityOf(shopPage.Buy().get(i))).click();

						countOfItems++;
					}
				}
				if (name.contains(frog)) {
					for (int a = 0; a < countStuffedFrog; a++) {
						wait.until(ExpectedConditions.visibilityOf(shopPage.Buy().get(i))).click();

						countOfItems++;
					}
				}
				if (name.contains(bear)) {
					for (int a = 0; a < countValentineBear; a++) {
						wait.until(ExpectedConditions.visibilityOf(shopPage.Buy().get(i))).click();
						Thread.sleep(4000);
						countOfItems++;
					}
				}
			}

		}

		WebElement rightMenu = shopPage.RightMenu();
		List<WebElement> rightMenuItem = rightMenu.findElements(By.tagName("li"));

		for (int j = 0; j < rightMenuItem.size(); j++) {

			if (rightMenuItem.get(j).getText().contains("Cart")) {
				wait.until(ExpectedConditions.visibilityOf(rightMenuItem.get(j))).click();

			}
		}

		// verify cart details(price)
		List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElements(shopPage.Table()));
		for (int a = 0; a < items.size(); a++) {
			String itemName = items.get(a).getText();
			if (productsNeededList.contains(itemName)) {
				if (itemName.contains(bunny)) {

					String priceOfBunnyActual = shopPage.Price().get(a).getText();

					assertTrue(priceOfBunnyActual.equals(priceOfBunny));

					String countBunnyActual = shopPage.Quantity().get(a).getAttribute("value");
					String SubOfBunny = shopPage.SubTotal().get(a).getText();
					price_bunny = floatstr.Remove$AndConvertToFloat(priceOfBunnyActual);
					subtotal_bunny = floatstr.Remove$AndConvertToFloat(SubOfBunny);
					count_bunny = Integer.parseInt(countBunnyActual);
					Assert.assertEquals((price_bunny * count_bunny), subtotal_bunny, 0.00003);

				}

				if (itemName.contains(frog)) {
					String priceOfFrogActual = shopPage.Price().get(a).getText();
					assertTrue(priceOfFrogActual.equals(priceOfFrog));
					String countFrogActual = shopPage.Quantity().get(a).getAttribute("value");
					String SubOfFrog = shopPage.SubTotal().get(a).getText();
					price_frog = floatstr.Remove$AndConvertToFloat(priceOfFrogActual);
					subtotal_frog = floatstr.Remove$AndConvertToFloat(SubOfFrog);
					count_frog = Integer.parseInt(countFrogActual);
					Assert.assertEquals(price_frog * count_frog, subtotal_frog, 0.00003);

				}
				if (itemName.contains(bear)) {
					String priceOfBearActual = shopPage.Price().get(a).getText();
					assertTrue(priceOfBearActual.equals(priceOfBear));
					String countBearActual = shopPage.Quantity().get(a).getAttribute("value");
					String SubOfBear = shopPage.SubTotal().get(a).getText();

					price_bear = floatstr.Remove$AndConvertToFloat(priceOfBearActual);
					subtotal_bear = floatstr.Remove$AndConvertToFloat(SubOfBear);
					count_bear = Integer.parseInt(countBearActual);
					Assert.assertEquals(price_bear * count_bear, subtotal_bear, 0.00003);
				}
			}

			// Comparing Total

			if (items.get(a).getText().contains("Total")) {
				String totalString = items.get(a).getText();
				totalString = totalString.substring(7);
				float total = Float.parseFloat(totalString);
				Assert.assertEquals(subtotal_bear + subtotal_frog + subtotal_bunny, total, 0.00003);

			}

		}

		wait.until(ExpectedConditions.visibilityOf(shopPage.EmptyCart())).click();

		java.util.Set<String> handles = driver.getWindowHandles();
		Iterator<String> iterator = handles.iterator();
		String subWindowHandler = null;
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler);
		System.out.println(subWindowHandler);
		driver.switchTo().window(subWindowHandler);
		wait.until(ExpectedConditions.visibilityOf(shopPage.YesButton())).click();

	}

	@AfterTest
	public void CloseBrowser() {
		driver.close();
	}

	@DataProvider
	public Object[][] shopDataProduct() throws IOException {
		Object data[][] = new Object[1][4];
		ReadDataFromExcel readdata = new ReadDataFromExcel();
		List<List<Object>> excelData = readdata.ReadDataAll("shopdata1", "Product");
		data[0][0] = excelData.get(0).get(0);
		data[0][1] = excelData.get(0).get(1);
		data[0][2] = excelData.get(1).get(0);
		data[0][3] = excelData.get(1).get(1);

		return data;
	}

	@DataProvider
	public Object[][] cartDataProduct() throws IOException {
		Object data[][] = new Object[1][9];
		ReadDataFromExcel readdata = new ReadDataFromExcel();
		List<List<Object>> excelData = readdata.ReadDataAll("shopdata2", "Product");

		data[0][0] = excelData.get(0).get(0);
		data[0][1] = excelData.get(0).get(1);
		data[0][2] = excelData.get(0).get(2);
		data[0][3] = excelData.get(1).get(0);
		data[0][4] = excelData.get(1).get(1);
		data[0][5] = excelData.get(1).get(2);
		data[0][6] = excelData.get(2).get(0);
		data[0][7] = excelData.get(2).get(1);
		data[0][8] = excelData.get(2).get(2);

		return data;
	}
}
