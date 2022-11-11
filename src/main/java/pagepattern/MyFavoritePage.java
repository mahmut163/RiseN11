package pagepattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.ScreenShotUtility;
import utility.TestUtility;

public class MyFavoritePage {
    WebDriver driver;
    TestUtility testUtility;
    ScreenShotUtility screenShotUtility;

    @FindBy(xpath = "(//h4[@class=\"listItemTitle\"])[1]")
    WebElement favoriteList;
    @FindBy(xpath = "//h3[text()=\" Samsung Galaxy A32 128 GB (Samsung Türkiye Garantili)\"]")
    WebElement productInFavoriteList;
    @FindBy(css = ".deleteProFromFavorites")
    WebElement deleteFavoriteButton;
    @FindBy(css = ".btn.btnBlack.confirm")
    WebElement confirmDeleteButton;
    @FindBy(xpath = "Ürününüz listeden silindi.")
    WebElement confirmDeleteMessage;

    public MyFavoritePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        testUtility = new TestUtility(driver);
    }

    public void clickOnFavoriteList() {
        testUtility.waitForElementPresent(favoriteList);
        favoriteList.click();
        testUtility.sleep(2);
    }

    public String getTheProductNameInFavoriteList() {
        testUtility.waitForElementPresent(productInFavoriteList);
        return productInFavoriteList.getText();
    }

    public void clickOnDeleteButton() {
        testUtility.waitForElementPresent(deleteFavoriteButton);
        deleteFavoriteButton.click();

    }

    public void clickOnDeleteConfirmButton() {
        testUtility.waitForElementPresent(confirmDeleteButton);
        confirmDeleteButton.click();
        testUtility.sleep(2);
        screenShotUtility.takeScreenshot("image", "delete-result", driver);

    }

    public boolean verifyProductDeletedFromFavoriteList() {
        testUtility.waitForElementPresent(confirmDeleteMessage);
        if (confirmDeleteMessage.getText().equalsIgnoreCase("Ürününüz listeden silindi.")) {
            return true;
        } else {
            return false;
        }
    }

}
