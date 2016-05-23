import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/*** Created by Sunaina on 2/29/2016.
 */
public class TestSaveForLater {
    WebDriver driver;
    HomeObjects home;
    CommonFunctions commonFunctions;
    Logger logger;

    @BeforeTest
    public void init()  {
        commonFunctions = new CommonFunctions();
        logger = Logger.getLogger("TestSaveForLater");
        PropertyConfigurator.configure("log4j.properties");
        driver = commonFunctions.getBrowser(driver, ".//src//main//resources");
        driver.get("http://www.williams-sonoma.com");
        home = new HomeObjects(driver);
        System.out.println("Size" + home.popupExists().size());
        if(home.popupExists().size() > 0) {
        	home.popup().click();
        }
    }

    @BeforeMethod	
    public void clickHome() {
        logger.info("in clickHome");
        home.home().click();
    }

    @Test(dataProvider="getData")
    //select product, add to save list, verify if it is added
    //Get test data from excel sheet to test different links
    public void testSaveItems(String category, String subcategory, String product) {
        logger.info("in testSaveItems");

        try {
            home.selectProductFromLeft(category, subcategory, product );
            home.addToCart().click();
            home.checkout().click();
            home.saveForLater().click();

            //Verify product exists in the saved products
            Assert.assertTrue(home.productsInSave(product).size()>0, "Product " + product + " is not saved");
            logger.info("Product " + product + " is saved");

        } catch (Exception e) {
            System.out.println(e);
            logger.error("Error testing save items: " + e);
        }
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        Object[][] data = commonFunctions.readFromInputSheet(".//src//main//resources//TestData.xlsx", "SavedItems");
        return data;
    }

}

