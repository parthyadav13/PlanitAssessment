package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GlobalValues {

	public Properties PropertyFile() throws IOException {

		Properties property = new Properties();
		FileInputStream fis = new FileInputStream(".\\src\\main\\java\\resources\\data.properties");
		property.load(fis);

		// Can be used in Future
		// FileOutputStream fos=new FileOutputStream("");
		return property;
	}

}
