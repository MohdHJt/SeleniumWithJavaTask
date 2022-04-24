package integration.spec;


import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.IOException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
@Listeners(integration.spec.ListenerTest.class)

public class MainTask {
    static WebDriver driver;
    static WebDriverWait wait;

    public static void main(String[] args) throws IOException,
            CsvValidationException, InterruptedException {

        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("use-fake-ui-for-media-stream");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Family\\libs\\Selenium-jars\\chromedriver.exe");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        integration.spec.GenericSelector.login();
        Thread.sleep(1000);
        LinkedList<String> ll = new LinkedList<String>();
        LinkedList<String> outlist = new LinkedList<String>();
        ll = GenericSelector.readData();//read data from CSV file and save it in linkedList ll
        outlist = GenericSelector.SearchRes(ll);//Search on names that read and save the result in linkedList outList
        integration.spec.GenericSelector.WritrToSCVFile(outlist);//write the result from OutList to Out.SCV file
        GenericSelector.sendMess(ll);//send message to the first result and print is the message was sent
        signOut();//sing out from the website
        driver.close();
    }
    public static void signOut(){

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"userAvatar\"]")));
        driver.findElement(By.xpath("/html/body/div[1]/header/section/user-menu/div/dropdown/div")).click();
        driver.findElement(By.xpath("//*[@id=\"userAreaMenu\"]/dropdown-item[10]/li/div[2]/div")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"popup\"]/userwindow/userwindow-content/div[1]")));
        driver.findElement(By.xpath("//*[@id=\"popup\"]/userwindow/userwindow-footer/square-button[2]")).click();
    }

    @Test
    public void testToFail(){
        System.out.println("this method to test fail");
        Assert.assertTrue(false);
    }
}
