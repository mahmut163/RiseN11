package pagepattern;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.TestUtility;

public class HomePage {
    WebDriver driver;
    TestUtility testUtility;
    Actions actions;
    String config = "config.properties";


    @FindBy(css = ".btnSignIn")
    WebElement signInButton;
    @FindBy(name = "email")
    WebElement emailField;
    @FindBy(name = "password")
    WebElement passWordField;
    @FindBy(id = "loginButton")
    WebElement loginButton;
    @FindBy(id = "searchData")
    WebElement searchBox;
    @FindBy(xpath = "//div[@class=\"resultText \"]/h1")
    WebElement searchResult;
    @FindBy(xpath = "//div[@class=\"pagination\"]/a[2]")
    WebElement secondPageLink;
    @FindBy(xpath = "//span[text()='Tümünü Kabul Et']")
    WebElement cookieMessage;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        testUtility = new TestUtility(driver);
        actions = new Actions(driver);
    }

    String expectedTitle = TestUtility.readFromConfigProperties(config, "title");

    public boolean verifyHomePageOpened() {
        if (driver.getTitle() != null && driver.getTitle().contains(expectedTitle)) {
            System.out.println("Home page is opened");
            return true;
        } else {
            System.out.println("Home page could not open.");
            return false;
        }

    }

    public void cookieHandle() {
        testUtility.waitForElementPresent(cookieMessage);
        cookieMessage.click();
        testUtility.sleep(2);
    }

    public void clickOnSignInButton() {
        testUtility.waitForElementPresent(signInButton);
        signInButton.click();
        testUtility.sleep(3);
    }

    public void enterEmailAddress(String email) {
        testUtility.waitForElementPresent(emailField);
        emailField.sendKeys(email);
        System.out.println(emailField.getAttribute("value"));

    }

    public void enterPassWord(String password) {
        testUtility.waitForElementPresent(passWordField);
        passWordField.sendKeys(password);
        System.out.println(passWordField.getAttribute("value"));
    }

    public void clickOnLoginButton() {

        testUtility.waitForElementPresent(loginButton);
        testUtility.scrollToElement(loginButton);
        actions.moveToElement(loginButton).build().perform();
        testUtility.sleep(3);

    }

    public void login(String email, String password) {
        clickOnSignInButton();
        enterEmailAddress(email);
        enterPassWord(password);
        clickOnLoginButton();

    }

    public void typeProductNameInSearchBox(String pName) {
        testUtility.waitForElementPresent(searchBox);
        searchBox.sendKeys(pName + Keys.ENTER);
        testUtility.sleep(2);
    }

    public boolean verifySearchResult(String productName) {
        testUtility.waitForElementPresent(searchResult);
        return searchResult.getText().trim().equalsIgnoreCase(productName);
    }

    public void scrollToPaginationView() {
        WebElement relatedkeyword = null;
        boolean loop = true;
        WebElement pagination;
        while (loop) {
            relatedkeyword = driver.findElement(By.xpath("//div[@class = 'related-keyword']/div[@class = 'title']"));
            try {
                pagination = driver.findElement(By.xpath("//div[@class = 'pagination']"));
                loop = false;
            } catch (NoSuchElementException e) {
                testUtility.scrollToElement(relatedkeyword);
                testUtility.scrollBy(0, -300);
                testUtility.sleep(2);
            }
        }

        testUtility.scrollToElement(relatedkeyword);
        testUtility.scrollBy(0, -300);


    }

    public void clickOnSecondProductPageLink() {
        testUtility.waitForElementPresent(secondPageLink);
        secondPageLink.click();
        testUtility.sleep(4);
    }


}
