package SeleniumTest;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;


public class GoogleSearchTest {
    public static void main(String[] args) throws InterruptedException {

        BasicConfigurator.configure();

        String dPath = "C:\\Users\\StreamTest\\Desktop\\repository\\JavaStudies\\chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", dPath);
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.google.com");

        driver.manage().window().maximize();

        Thread.sleep(2000);

        //driver.findElement(By.name("q")).sendKeys("java");
        driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input"))
                .sendKeys("java");


        Thread.sleep(2000);

        List<WebElement> list = driver.findElements(
                By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[5]/center/input[1]")
        );

        list.get(0).click();

        String xPath = "//*[@id=\"hdtb-msb\"]/div[1]/div/div[4]/a";

        driver.findElement(By.xpath(xPath)).click();

        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"islrg\"]/div[1]/div[1]/a[1]/div[1]/img")).click();




    }
}