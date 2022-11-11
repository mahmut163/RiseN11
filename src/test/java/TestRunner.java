import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import pagepattern.HomePage;
import pagepattern.MyFavoritePage;
import pagepattern.ProductPage;
import utility.TestBase;
import utility.TestUtility;

public class TestRunner extends TestBase {
    String excelFile = "test-data/test_data.xlsx";
    final static String config = "config.properties";
    String emailAddress = TestUtility.readFromExcelCell(excelFile,"Sheet1",1, 0);
    String password = TestUtility.readFromExcelCell(excelFile,"Sheet1",1, 1);
    String productName = TestUtility.readFromExcelCell(excelFile,"Sheet1",1,2);
    static String url= TestUtility.readFromConfigProperties(config,"URL");
    HomePage homePage=new HomePage(driver);
    ProductPage productPage=new ProductPage(driver);
    MyFavoritePage myFavoritePage;
    String selectedProductName;


    @BeforeClass
    public static void setUp(){
        browserSetUp(url);
    }

    @Test()
    public void testN11Web(){

        Assert.assertTrue(homePage.verifyHomePageOpened());
        homePage.cookieHandle();
        homePage.login(emailAddress,password);
        homePage.typeProductNameInSearchBox(productName);
        Assert.assertTrue(homePage.verifySearchResult(productName));
        homePage.scrollToPaginationView();
        homePage.clickOnSecondProductPageLink();
        productPage.verifySecondPageOpened();
        productPage.clickONThirdProductFavoriteButton();
        selectedProductName=productPage.getSelectedProductName();
        myFavoritePage= productPage.clickOnMyFavoriteLink();
        myFavoritePage.clickOnFavoriteList();
        Assert.assertEquals(selectedProductName,myFavoritePage.getTheProductNameInFavoriteList());
        myFavoritePage.clickOnDeleteButton();
        myFavoritePage.clickOnDeleteConfirmButton();
        Assert.assertTrue(myFavoritePage.verifyProductDeletedFromFavoriteList());




    }
    @AfterClass
    public static void tearDown(){
        closeBrowser();

    }
}
