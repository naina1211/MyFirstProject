import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by Sunaina on 2/28/2016.
 */
public class HomeObjects {
    private WebDriver driver;
    public HomeObjects(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //private String categoryXpath = "//a[@class='topnav-cookware '][contains(text(),'Cookware')]";
    private String categoryXpath = "//a[contains(@class, 'topnav')][contains(text(),'xxxx')]";
    private String subcategoryXpath = "//a[contains(@href, 'cm_type=gnav')][contains(text(), 'xxxx')]";
    private String leftMenuXpath = "//a[contains(@href, 'cm_type=lnav')][contains(text(), 'xxxx')]";
    private String productXpath = "//a[contains(., 'xxxx')]";
    private String productLinkInSaveXpath = "//a[contains(text(), 'xxxx')]";

    @FindBy(xpath="//*[@id='sub-brand-bar-container']/section/ul/li[1]/a")
    WebElement home;

    @FindBy(xpath="//*[@id=\"pip\"]/div[1]/div[6]/div[2]/div[2]/section/div/div/fieldset[1]/button/span")
    WebElement addToCart;

    @FindBy(xpath="//a[@title='Close' and contains(@class, 'overlayCloseX')]")
    WebElement popup;

    @FindBy(xpath="//a[@id=\"anchor-btn-checkout\"]")
    WebElement checkout;

    @FindBy(xpath="//a[contains(., 'Save For Later')]")
    WebElement saveForLater;

    @FindBy(xpath="//a[@class='view-cart'][contains(text(),'Cart')]")
    WebElement cart;

    public WebElement category(String categoryText){
          return driver.findElement(By.xpath(categoryXpath.replace("xxxx", categoryText)));
    }

    public WebElement subcategory(String subcategoryText){
        return driver.findElement(By.xpath(subcategoryXpath.replace("xxxx", subcategoryText)));
    }

    public WebElement leftMenu(String subcategoryText){
        return driver.findElement(By.xpath(leftMenuXpath.replace("xxxx", subcategoryText)));
    }
    public WebElement product(String product){
        return driver.findElement(By.xpath(productXpath.replace("xxxx", product)));
    }

    public List<WebElement> productsInSave(String product){
        return driver.findElements(By.xpath(productLinkInSaveXpath.replace("xxxx", product)));
    }

    public WebElement addToCart(){
        return addToCart;
    }

    public WebElement checkout(){
        return checkout;
    }

    public WebElement saveForLater(){
        return saveForLater;
    }

    public WebElement popup(){
        return popup;
    }

    public WebElement home(){
        return home;
    }

    public List<WebElement> saveList(String product){
        return driver.findElements(By.xpath("//div[class='save-for-later-section'][contains(., '" + product + "')]"));
    }

    //use ajax menu to select a product
    public void selectProductFromMenu(String category, String subcategory, String product){
        Actions actions = new Actions(driver);
        actions.moveToElement(category(category)).build().perform();
        actions.click(subcategory(subcategory)).build().perform();
        product(product).click();
    }

    //USe left navigation to select  a product
    public void selectProductFromLeft(String category, String subcategory, String product){
        category(category).click();
        leftMenu(subcategory).click();
        product(product).click();
    }
    
    //check if popup exists
    public List<WebElement> popupExists(){
    	return driver.findElements(By.xpath("//a[@title='Close' and contains(@class, 'overlayCloseX')]"));
    }
  

}
