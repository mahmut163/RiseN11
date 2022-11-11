package pagepattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.TestUtility;

public class ProductPage {
    WebDriver driver;
    TestUtility testUtility;
    Actions actions;
    String config = "config.properties";

    @FindBy(xpath = "(//span[@title='Favorilere ekle'])[3]")
    WebElement addFavoriteButton;
    @FindBy(css = ".myFavorities ")
    WebElement myFavoriteLink;
    @FindBy(xpath = "//h3[text()=\"Samsung Galaxy A32 128 GB (Samsung Türkiye Garantili)\"]")
    WebElement selectedProduct;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        testUtility = new TestUtility(driver);
        actions = new Actions(driver);
    }

    String expectedTitle = TestUtility.readFromConfigProperties(config, "title2");

    public boolean verifySecondPageOpened() {
        if (driver.getTitle() != null && driver.getTitle().contains(expectedTitle)) {
            System.out.println("second page is opened");
            return true;
        } else {
            System.out.println("second page could not open.");
            return false;
        }

    }

    public void clickONThirdProductFavoriteButton() {
        testUtility.waitForElementPresent(addFavoriteButton);
        addFavoriteButton.click();
        testUtility.sleep(2);
    }

    public String getSelectedProductName() {
        testUtility.waitForElementPresent(selectedProduct);
        return selectedProduct.getText();
    }

    public MyFavoritePage clickOnMyFavoriteLink() {
        testUtility.waitForElementPresent(myFavoriteLink);
        actions.moveToElement(myFavoriteLink).click();
        testUtility.sleep(3);
        return new MyFavoritePage(driver);
    }

}
