import com.sun.javafx.PlatformUtil;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlightBookingTest {

	WebDriver driver;

	@Test
	public void testThatResultsAppearForAOneWayJourney() {

		setDriverPath();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications"); //To handle Allow/Block Notification
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability(ChromeOptions.CAPABILITY, options);
		caps.setCapability("acceptInsecureCerts", true);
		driver= new ChromeDriver(caps);

		driver.get("https://www.cleartrip.com/");
		waitFor(2000);//we can use implicit/explicit wait
		driver.findElement(By.id("OneWay")).click();

		driver.findElement(By.id("FromTag")).clear();
		driver.findElement(By.id("FromTag")).sendKeys("Bangalore");

		//wait for the auto complete options to appear for the origin

		waitFor(4000);/*
        List<WebElement> originOptions = driver.findElement(By.id("ui-id-1")).findElements(By.tagName("li"));*/
		WebElement originOptions = driver.findElement(By.id("ui-id-1")).findElement(By.tagName("li"));
		originOptions.click();

		waitFor(4000);
		//driver.findElement(By.("toTag")).clear();
		//driver.findElement(By.id("toTag")).sendKeys("Delhi"); 
		driver.findElement(By.name("destination")).clear();
		driver.findElement(By.name("destination")).sendKeys("Delhi");

		//wait for the auto complete options to appear for the destination

		waitFor(4000);
		/*//select the first item from the destination auto complete list
        List<WebElement> destinationOptions = driver.findElement(By.id("ui-id-2")).findElements(By.tagName("li"));*/
		WebElement destinationOptions = driver.findElement(By.id("ui-id-2")).findElement(By.tagName("li"));
		destinationOptions.click();

		driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div[1]/table/tbody/tr[3]/td[7]/a")).click();

		//all fields filled in. Now click on search
		driver.findElement(By.id("SearchBtn")).click();

		waitFor(5000);
		//verify that result appears for the provided journey search
		Assert.assertTrue(isElementPresent(By.className("searchSummary")));

		//close the browser
		driver.quit();

	}

	private void waitFor(int durationInMilliSeconds) {
		try {
			Thread.sleep(durationInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}


	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

    	private void setDriverPath() {
        	if (PlatformUtil.isMac()) {
            		System.setProperty("webdriver.chrome.driver", "chromedriver");
        	}
        	if (PlatformUtil.isWindows()) {
            		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        	}
        	if (PlatformUtil.isLinux()) {
            		System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
        	}
    	}
}
