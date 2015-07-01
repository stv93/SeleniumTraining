package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {

	protected final WebDriver driver;
	
	@FindBy(css = ".login a[href*='logout']")
	protected WebElement logOutLink;
	
	public BasePage(WebDriver wd) {
		this(wd, false);
	}
		
	protected BasePage(WebDriver wd, boolean checkIfLoaded) {
		this.driver = wd;
		PageFactory.initElements(driver, this);
		if (checkIfLoaded) {
			isLoaded();
		}
	}
	
	public boolean isLoggedIn() {
		try {
			return logOutLink.isDisplayed();
		}
		catch (NoSuchElementException e) {}
		return false;
	}
	
	@Override
	protected void load() {
		System.out.printf("Loading page: '%s'\n", getPageUrl());
		driver.get(getPageUrl());
	}
	
	protected abstract String getPageUrl();
}
