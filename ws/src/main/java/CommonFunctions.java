import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunaina on 2/15/2016.
 */
public class CommonFunctions {

    private static Logger logger;

    public CommonFunctions() {
        logger = Logger.getLogger("CommonFunctions");
        PropertyConfigurator.configure("log4j.properties");
    }

    public WebDriver getBrowser(WebDriver driver, String driverLoc) {
        String browser = getPropertyValue("Execution.properties", "browser");

        if (browser.equalsIgnoreCase("Firefox")) {
            driver = getFirefoxBrowser(driver);
        }

        if (browser.equalsIgnoreCase("IE") || browser.equalsIgnoreCase("InternetExplorer")) {
            driver = getIEBrowser(driver, driverLoc);
        }

        if (browser.equalsIgnoreCase("Chrome")) {
            driver = getChromeBrowser(driver, driverLoc);
        }

        return driver;
    }

    public WebDriver getFirefoxBrowser(WebDriver driver) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true);
        driver = new FirefoxDriver(profile);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver getIEBrowser(WebDriver driver, String driverLoc) {
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability("native events", true);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, false);
        System.setProperty("webdriver.ie.driver", driverLoc + "\\IEDriverServer.exe");
        driver = new InternetExplorerDriver(capabilities);
        return driver;
    }

    public WebDriver getChromeBrowser(WebDriver driver, String driver_Loc) {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", driver_Loc + "\\chromedriver.exe");
        options.addArguments("--no-sandbox");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.binary", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        capabilities.setCapability("nativeEvents", false);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public String getPropertyValue(String filename, String key){
        try {
            Properties properties = new Properties();
            FileInputStream in = new FileInputStream(filename);
            properties.load(in);
            return properties.getProperty(key);
        }
        catch(Exception e){
            System.out.println(e);
            return "null";
        }
    }

    //reads data from excel and puts in a two dimentioal array
    public String[][] readFromInputSheet(String file, String sheetName) throws IOException {
        FileInputStream fis = null;
        String[][] testdata = null;
        try {
            //fileLocation = fileLocation + "\\InputDataSheet.xls";
            //File excel = new File(fileLocation);
            fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet ws = wb.getSheet(sheetName);

            int rowNum = ws.getLastRowNum() + 1;
            int colNum = ws.getRow(0).getLastCellNum();
            testdata = new String[rowNum][colNum];
            for (int i = 0; i < rowNum; i++) {
                XSSFRow row = ws.getRow(i);
                for (int j = 0; j < colNum; j++) {
                    XSSFCell cell = row.getCell(j);
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    String value = cell.toString();
                    testdata[i][j] = value;
                }
            }
        } catch (Error e) {
            fis.close();
            logger.info(e.toString());
        } catch (Exception e) {
            fis.close();
            logger.info(e.toString());
        }
        fis.close();
        return testdata;
    }

}
