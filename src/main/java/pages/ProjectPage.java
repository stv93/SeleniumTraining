package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProjectPage extends BasePage {
	String projectName;
	
	@FindBy(css = "div.build-details > a")
	private WebElement buildHistoryDateElement;
	
	@FindBy(xpath = ".//*[@id='breadcrumbs']/li[3]/a")
	private WebElement projectNameElement;
	
	@FindBy(id = "yui-gen1-button")
	private WebElement disableButton;
				
	public ProjectPage(WebDriver driver){
		super(driver);
	}
	
	public WebElement getBuildHistoryDateElement() {		
		return  buildHistoryDateElement;
	}
	
	public String getBuildHistoryDate(WebElement element){
		return getDateOfElement(element);			
	}
	
	public void goToBuildPage(){		
		getBuildHistoryDateElement().click();
	}
	public String getProjectName(){
		return projectNameElement.getText();
	}
	
	public boolean isOnProjectPage(){
		try {
			return disableButton.isDisplayed();
		}
		catch (NoSuchElementException e) {}
		return false;
	}

	@Override
	protected void load() {
		if (!isLoggedIn()) {
			 new LoginPage(driver).get().loginAs(login, password);			 
		}
		driver.get("http://seltr-kbp1-1.synapse.com:8080/job/" + new MainPage(driver).getProjectName());
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(isLoggedIn());		 
		Assert.assertTrue(isOnProjectPage());	
	}
}

