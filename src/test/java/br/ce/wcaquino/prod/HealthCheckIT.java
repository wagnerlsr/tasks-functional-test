package br.ce.wcaquino.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {

	@Test
	public void healthCheck() throws MalformedURLException {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");

		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setPlatform(Platform.LINUX);
		cap.setCapability(ChromeOptions.CAPABILITY, options);

		WebDriver driver = new RemoteWebDriver(new URL("http://172.18.0.1:5555/wd/hub"), cap);

		try {
			driver.navigate().to("http://172.18.0.1:9999/tasks");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			String version = driver.findElement(By.id("version")).getText();

			Assert.assertTrue(version.startsWith("build"));

		} catch (Exception e) {
			driver.quit();
		}

	}

}
