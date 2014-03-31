package org.openqa.selenium.seleniumquery.functions;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.seleniumquery.SeleniumQueryObject;
import org.openqa.selenium.support.ui.Select;

public class ValFunction {

	/**
	 * $(".selector").val();
	 */
	public static String val(List<WebElement> elements) {
		if (elements.isEmpty()) {
			return null;
		}
		return val(elements.get(0));
	}

	/**
	 * Gets the value of the given element.
	 * 
	 * @param element The element you want the value of.
	 * @return The value of the element.
	 * 
	 * @author acdcjunior
	 * @since 0.3.0
	 */
	public static String val(WebElement element) {
		if ("input".equals(element.getTagName())) {
			return element.getAttribute("value");
		} else if ("select".equals(element.getTagName())) {
			return new Select(element).getFirstSelectedOption().getText();
		} else {
			return element.getText();
		}
	}
	
	/**
	 * $(".selector").val(123);
	 */
	public static SeleniumQueryObject val(SeleniumQueryObject caller, List<WebElement> elements, Number value) {
		return val(caller, elements, value.toString());
	}

	/**
	 * $(".selector").val("string");
	 */
	public static SeleniumQueryObject val(SeleniumQueryObject seleniumQueryObject, List<WebElement> elements, String value) {
		for (WebElement element : elements) {
			val(element, value);
		}
		return seleniumQueryObject;
	}
	
	private static void val(WebElement element, String value) {
		if ("select".equals(element.getTagName())) {
			new Select(element).selectByVisibleText(value);
		} else if ("input".equals(element.getTagName()) && "file".equals(element.getAttribute("type"))) {
			element.sendKeys(value);
		} else {
			element.clear();
			element.sendKeys(value);
		}
	}

}