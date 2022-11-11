package utility;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenShotUtility {
    public void takeScreenshot(String folder,String fileName, WebDriver driver)
    {
        DateTime dt1=new DateTime();
        DateTimeFormatter formatter= DateTimeFormat.forPattern("yyyy-MM-dd-HH-mm-ss-SSS");
        String timestamp=dt1.toString(formatter);
        fileName=fileName+"-"+timestamp;
        File imageFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(imageFile,new File(folder+File.separator+fileName+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
