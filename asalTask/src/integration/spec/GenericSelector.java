package integration.spec;

import com.google.common.base.Charsets;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static integration.spec.MainTask.*;

public class GenericSelector {

    public static LinkedList readData(){
        var fileName = "C:\\Users\\Family\\IdeaProjects\\asalTask\\src\\integration\\spec\\inputData.csv";
        LinkedList<String> ll = new LinkedList<String>();
        int j = 0, k = 1;
        String name1, name2 = "", name3 = "";

        //read data from CSV file to ll list
        try (var fr = new FileReader(fileName, StandardCharsets.UTF_8);
             var reader = new CSVReader(fr)) {
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                ll.add(nextLine[0]);
                ll.add(nextLine[1]);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        String roar = ll.getFirst();
        ll.removeFirst();
        ll.addFirst(roar.substring(1, roar.length()));
        return ll;
    }

    public static LinkedList SearchRes(LinkedList<String> ll) {
        By selector = By.xpath("//*[@id=\"leftArea\"]/conversations/conversations-search/div/input");
        LinkedList<String> outlist = new LinkedList<String>();
        int j = 0, k = 1;
        String name1, name2 = "", name3 = "";
        for (int x = 0; x < ll.size(); x++) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"leftArea\"]/conversations/conversations-search/div/input")));
            driver.findElement(selector).clear();
            WebElement textbox = driver.findElement(selector);
            driver.findElement(selector).sendKeys(ll.get(x));
            textbox.sendKeys(Keys.ENTER);
            outlist.add(ll.get(x++));
            //driver.findElement(By.linkText(ll.get(x)))
            String filter=filter(ll.get(x));
            //System.out.println(filter);
            outlist.add(ll.get(x));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"leftArea\"]/conversations/div/div/div/conversation-cell[1]/div/div/div[1]/p")));
            name1 = driver.findElement(By.xpath("//*[@id=\"leftArea\"]/conversations/div/div/div/conversation-cell[" + k++ + "]/div/div/div[1]/p")).getText();
            outlist.add(name1);
            while (j < 1) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"leftArea\"]/conversations/div/div/div/conversation-cell[" + k + "]/div/div/div[1]/p")));
                name2 = driver.findElement(By.xpath("//*[@id=\"leftArea\"]/conversations/div/div/div/conversation-cell[" + k + "]/div/div/div[1]/p")).getText();
                if ((name2.compareTo(outlist.getLast())) != 0) {
                    j++;
                    outlist.add(name2);
                }
                k++;
            }
            while (j < 2) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"leftArea\"]/conversations/div/div/div/conversation-cell[" + k + "]/div/div/div[1]/p")));
                name3 = driver.findElement(By.xpath("//*[@id=\"leftArea\"]/conversations/div/div/div/conversation-cell[" + k + "]/div/div/div[1]/p")).getText();
                if ((name3.compareTo(outlist.getLast())) != 0) {
                    j++;
                    outlist.add(name3);
                }
                k++;
            }
            j = 0;
            k = 1;
            driver.navigate().refresh();
        }

        return outlist;
    }


    public static void sendMess(LinkedList<String> ll) throws InterruptedException {
        By selector = By.xpath("//*[@id=\"leftArea\"]/conversations/conversations-search/div/input");
        LinkedList<String> outlist = new LinkedList<String>();
        for (int x = 0; x < ll.size(); x++) {
            //driver.navigate().refresh();
            driver.findElement(selector).clear();
            WebElement textbox = driver.findElement(selector);
            driver.findElement(selector).sendKeys(ll.get(x));
            textbox.sendKeys(Keys.ENTER);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"leftArea\"]/conversations/div/div/div/conversation-cell[1]/div/div/div[1]/p")));
            driver.findElement(By.xpath("//*[@id=\"leftArea\"]/conversations/div/div/div/conversation-cell[1]/div/div/div[1]")).click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("/html/body/div[1]/section[1]/conversation-area/main/div/chat-view/footer/chat-input-view/div/div[2]/emojitextarea/div")).sendKeys("hi "+ll.get(x)+Keys.ENTER);
            Thread.sleep(500);
            String x1=driver.findElement(By.className("chat-view-item-text__right")).getText();
            Assert.assertEquals("hi "+ll.get(x), x1);
            System.out.println("Message Sent successfully to "+ll.get(x));
            ++x;


        }
    }

    public static void WritrToSCVFile(LinkedList<String> outlist) throws IOException {
        String outputFile = "C:\\Users\\Family\\IdeaProjects\\asalTask\\src\\integration\\spec\\outputData.csv";
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(outputFile), Charsets.UTF_8);
                CSVWriter csvWriter = new CSVWriter(writer,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ) {
            csvWriter.writeNext(new String[]{"Name","Filter", "Result 1", "Result 2","Result 3"});

            for (int i = 0; i < outlist.size(); i++) {
                csvWriter.writeNext(new String[]{outlist.get(i++), outlist.get(i++), outlist.get(i++), outlist.get(i++), outlist.get(i)});
            }
        }
    }

    public static String filter(String s){
        String path="";
        switch (s) {
            case "People":
                path="//*[@id=\"leftArea\"]/conversations/conversations-search/div[2]/div[1]";
                break;
            case "Bubbles":
                path="//*[@id=\"leftArea\"]/conversations/conversations-search/div[2]/div[2]";
                break;
            case "Text":
                path="//*[@id=\"leftArea\"]/conversations/conversations-search/div[2]/div[3]";
                break;
            case "Channels":
                path="//*[@id=\"leftArea\"]/conversations/conversations-search/div[2]/div[4]";
                break;
            case "Companies":
                path="//*[@id=\"leftArea\"]/conversations/conversations-search/div[2]/div[5]";
                break;
            default:
                path="//*[@id=\"leftArea\"]/conversations/conversations-search/div[2]/div[1]";
                break;
        }
        return path;
    }
    public static void login() throws InterruptedException {
        //Verify if a user will be able to login with a valid username and valid password.
        //Positive

        driver.get("https://web.openrainbow.net/rb/2.103.0/index.html");
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"form\"]/div/p")));
        Thread.sleep(1000);
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

}
