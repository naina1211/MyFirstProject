import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;


public class Test11 {

	@Test
	public void test() {
		// TODO Auto-generated method stub
		System.out.println("Test");
		WebDriver driver = new ChromeDriver();
		driver.get("http://google.com");

	}

}
