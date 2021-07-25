package reusableFunctions;

import java.util.List;

import org.openqa.selenium.WebElement;

public class ElementIsVisible {

// Method to check elements presence on the current page

	public static boolean IsVisible(List<WebElement> list) {

		if (list.isEmpty()) {

			return true;
		} else {

			return list.get(0).isDisplayed();
		}
	}

}
