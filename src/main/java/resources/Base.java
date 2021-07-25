package resources;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Base {

	public static WebDriver driver;
	GlobalValues globValue = new GlobalValues();

// Method for Brower initialisation
	public WebDriver initialiseDriver() throws IOException {
		Properties data = globValue.PropertyFile();
		String browerName = data.getProperty("chromebrowser");
		String driverPath = data.getProperty("chromedriver");
		int timeout = Integer.parseInt(data.getProperty("timeout"));

		if (browerName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", driverPath);
			driver = new ChromeDriver();

		} else if (browerName.equals("ie")) {
			System.setProperty("webdriver.ie.driver", driverPath);
			driver = new InternetExplorerDriver();

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

		return driver;
	}
}
