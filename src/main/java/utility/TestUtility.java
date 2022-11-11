package utility;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestUtility {
    private int timeout = Integer.parseInt(readFromConfigProperties(
            "config.properties", "timeout"
    ));

    private static WebDriver driver;

    public TestUtility(WebDriver driver) {

        this.driver = driver;
    }

    public void waitForElementPresent(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void javaScriptClick(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", element);
    }


    public void waitForAlertPresent() {
        WebDriverWait wai = new WebDriverWait(driver, timeout);
        wai.until(ExpectedConditions.alertIsPresent());
    }

    public void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String readFromConfigProperties(String fileName, String key) {
        Properties properties = new Properties();
        String workingDirectory = System.getProperty("user.dir");
        String value;
        try {
            properties.load(new FileInputStream(workingDirectory + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        value = properties.getProperty(key);
        System.out.println(String.format("%s=%s", key, value));
        return value;
    }

    public static String readFromExcelCell(String fileName, String sheetName, int rowNumber, int columNumber) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XSSFWorkbook workbook = null;
        try {
            assert fileInputStream != null;
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //XSSFSheet sheet=workbook.getSheetAt(0);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        XSSFRow row = sheet.getRow(rowNumber);
        String cellValue = null;
        if (row == null) {
            System.out.println("Empty row!!!!");
        } else {
            XSSFCell cell = row.getCell(columNumber);
            CellType cellType = cell.getCellType();
            switch (cellType) {
                case NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    cellValue = cellValue.replace(".", "").substring(0, cellValue.lastIndexOf("E") - 1);
                    System.out.println(cellValue);
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    // System.out.println(cellValue);
                    break;
            }
        }
        return cellValue;
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);

    }

    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,600)");

    }

    public void scrollBy(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(" + x + "," + y + ")");

    }

    public void highLightElement(WebElement element) {
        String backGroundColor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 10; i++) {
            changeElementColor("#D42D1B", element, driver);
            changeElementColor(backGroundColor, element, driver);
        }
    }

    public void changeElementColor(String color, WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;//downcasting
        js.executeScript("arguments[0].style.backgroundColor= '" + color + "'", element);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
