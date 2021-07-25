package reusableFunctions;

public class ConvertDollarStringToFloat {

// method to convert $string into float value for calculations
	public float Remove$AndConvertToFloat(String s) {
		s = s.substring(1);
		float f = Float.parseFloat(s);
		return f;
	}

}
