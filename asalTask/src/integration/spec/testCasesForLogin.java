package integration.spec;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Listeners(integration.spec.ListenerTest.class)

public class testCasesForLogin{
    static WebDriver driver;
    static ChromeOptions opts = new ChromeOptions();
    static WebDriverWait wait;


    @Test
    public static void case1() throws InterruptedException {
        //Verify if a user will be able to login with a valid username and valid password.
        //Positive

        opts.addArguments("use-fake-ui-for-media-stream");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Family\\libs\\Selenium-jars\\chromedriver.exe");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://web.openrainbow.net/rb/2.103.0/index.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("mohammad0598898689@gmail.com");
        Thread.sleep(500);
        driver.findElement(By.className("c-button__label")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"authPwd\"]")));
        driver.findElement(By.id("authPwd")).clear();
        driver.findElement(By.id("authPwd")).sendKeys("Mohd@12345");
        Thread.sleep(500);
        driver.findElement(By.id("authPwd")).sendKeys(Keys.ENTER);

    }

    @Test
    public static void case2() throws InterruptedException {
        //Verify if a user cannot login with a valid username and an invalid password.
        // Negative

        driver.get("https://web.openrainbow.net/rb/2.103.2/index.html#/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("mohibjaradat@gmail.com");
        driver.findElement(By.id("username")).sendKeys(Keys.RETURN);
        driver.findElement(By.className("c-button__label")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"authPwd\"]")));
        driver.findElement(By.id("authPwd")).clear();
        driver.findElement(By.id("authPwd")).sendKeys("Mohdas@12345");
        driver.findElement(By.id("authPwd")).sendKeys(Keys.RETURN);
        Thread.sleep(500);
        driver.findElement(By.className("c-button__label")).click();
        Thread.sleep(500);
        String x1 = driver.findElement(By.xpath("/html/body/div[1]/authentication-component/authentication-window/div/div/main/authenticationwindowcontent/h2/div")).getText();
        Assert.assertEquals("Incorrect username or password", x1);
        System.out.println("Assert case2 passed");

    }

    @Test
    public static void case3() {
        //Verify the login page, when the field of username is blank and Submit button is clicked.
        // Negative

        driver.get("https://web.openrainbow.net/rb/2.103.2/index.html#/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("");
        driver.findElement(By.id("username")).sendKeys(Keys.RETURN);
        driver.findElement(By.className("c-button__label")).click();

    }

    @Test
    public static void case4() {
        //Verify the login page, when the field of password is blank and Submit button is clicked.
        // Negative

        driver.get("https://web.openrainbow.net/rb/2.103.2/index.html#/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("mohammad0598898689@gmail.com");
        driver.findElement(By.id("username")).sendKeys(Keys.RETURN);
        driver.findElement(By.className("c-button__label")).click();
        driver.findElement(By.className("c-button__label")).click();
        driver.close();
    }

    @Test
    public static void case5() throws InterruptedException {
        //Verify the ‘Forgot your Password?’ functionality.
        // Positive
        opts.addArguments("use-fake-ui-for-media-stream");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Family\\libs\\Selenium-jars\\chromedriver.exe");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://web.openrainbow.net/rb/2.103.2/index.html#/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("mohammad0598898689@gmail.com");
        driver.findElement(By.id("username")).sendKeys(Keys.RETURN);
        driver.findElement(By.className("c-button__label")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/div[4]/a")));
        driver.findElement(By.xpath("//*[@id=\"form\"]/div/div[4]/a")).click();
        String x1 = driver.findElement(By.xpath("/html/body/div[1]/create-or-reset-account-component/authentication-window/div/div/main/authenticationwindowcontent/div")).getText();
        Assert.assertEquals("Reset your password", x1);
        System.out.println("Assert case5 passed");
        driver.close();

    }

    @Test
    public static void case6() throws InterruptedException {
        //	Verify the messages for invalid login.
        // Positive.
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("use-fake-ui-for-media-stream");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Family\\libs\\Selenium-jars\\chromedriver.exe");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://web.openrainbow.net/rb/2.103.2/index.html#/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("lascsamghchgc@mds.com");
        Thread.sleep(500);
        driver.findElement(By.className("c-button__label")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"authPwd\"]")));
        driver.findElement(By.id("authPwd")).clear();
        driver.findElement(By.id("authPwd")).sendKeys("Mohdasdasas@12345");
        Thread.sleep(500);
        driver.findElement(By.className("c-button__label")).click();
        Thread.sleep(500);
        String x1 = driver.findElement(By.className("authWindowContent__information")).getText();
        System.out.println(x1);
        Assert.assertEquals("Incorrect username or password", x1);
        System.out.println("Assert case6 passed");
        driver.close();
    }

    @Test
    public static void case7() throws InterruptedException {
        //Verify the ‘Keep my session alive’ functionality.
        // Positive
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("use-fake-ui-for-media-stream");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Family\\libs\\Selenium-jars\\chromedriver.exe");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://web.openrainbow.net/rb/2.103.2/index.html#/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("mohammad0598898689@gmail.com");
        Thread.sleep(500);
        driver.findElement(By.className("c-button__label")).click();
        Thread.sleep(500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"authPwd\"]")));
        driver.findElement(By.id("authPwd")).clear();
        driver.findElement(By.id("authPwd")).sendKeys("Mohd@12345");
        Thread.sleep(1000);
        driver.findElement(By.id("authPwd")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        driver.get("https://web.openrainbow.net/rb/2.103.0/index.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/section[1]/channels/channelslist/div/div/channelnoitem/p")));
        String x1 = driver.findElement(By.xpath("/html/body/div[1]/section[1]/channels/channelslist/div/div/channelnoitem/p")).getText();
        Assert.assertEquals("You're not following any channel.", x1);
        System.out.println("Assert with Keep session alive passed");
        signOut1();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("mohammad0598898689@gmail.com");
        Thread.sleep(500);
        driver.findElement(By.className("c-button__label")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"authPwd\"]")));
        driver.findElement(By.className("attribute-boolean__label")).click();
        driver.findElement(By.id("authPwd")).clear();
        driver.findElement(By.id("authPwd")).sendKeys("Mohd@12345");
        Thread.sleep(1000);
        driver.findElement(By.id("authPwd")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        driver.get("https://web.openrainbow.net/rb/2.103.0/index.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/authentication-component/authentication-window/div/div/main/authenticationwindowcontent/h2/div")));
        x1 = driver.findElement(By.xpath("/html/body/div[1]/authentication-component/authentication-window/div/div/main/authenticationwindowcontent/h2/div")).getText();
        Assert.assertEquals("Connect to Rainbow", x1);
        System.out.println("Assert without Keep session alive passed");
        driver.close();

    }
    public static void signOut1(){

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