
        package com.test;

import com.sun.xml.internal.ws.client.ClientSchemaValidationTube;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SavvyTest {
    WebDriver Driver;
    @Test(priority=1)
    public void savvyPage() {
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();// by using dependiences it sets the chrome browser
        Driver = new ChromeDriver();
        Driver.get("https://savvytime.com/converter");//url open
        Driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @Test(priority=2)
    public void verfiy(){
       // WebElement element =Driver.findElement(By.xpath("//h1[@class='title']"));
        WebElement element =Driver.findElement(By.className("title"));
       Assert.assertTrue(element.getText().contains("Time Zone Converter"),"incorrect page");
    }

    @Test(priority=3)
    public void searching() {
        Driver.findElement(By.id("time-search")).sendKeys("nizamabad");
        Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        List<WebElement> elements=Driver.findElements(By.xpath("//div[@id='converter-quick-search-result']"));
        elements.get(0).click();
//        WebElement element =Driver.findElement(By.xpath("//a[@class='time-abb']"));
//        System.out.println(element.getText());
//        Assert.assertTrue(element.getText().contains("Nizamabad, India"),"incorrect page");
        List<WebElement> total=Driver.findElements(By.xpath("//a[@class='time-abb']"));
        Assert.assertTrue(total.get(0).getText().contains("Nizamabad, India"),"incorrect one");

    }

    @Test(priority=4)
     public void searching2(){

       // Driver.get( "https://savvytime.com/converter/india-nizamabad");
        Driver.findElement(By.id("time-search")).sendKeys("hyderabad");
        Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        List<WebElement> elements=Driver.findElements(By.xpath("//div[@id='converter-quick-search-result']//a"));
        elements.get(0).click();
//        WebElement element =Driver.findElement(By.xpath("//div[@data-id='india-hyderabad']"));
//        System.out.println(element.getText());

//        Assert.assertTrue(element.getText().contains("Hyderabad, India"),"incorrect page");
        List<WebElement> total=Driver.findElements(By.xpath("//a[@class='time-abb']"));
        Assert.assertTrue(total.get(1).getText().contains("Hyderabad, India"),"incorrect one");
    }


    @Test(priority=5)
    public void swap(){
       // List<WebElement> element= Driver.findElements(By.className("btn-group"));
       // element.get(0).click();
        List<WebElement> beforeSwap=Driver.findElements(By.xpath("//a[@class='time-abb']"));
        List<String> bs =new ArrayList<String>();
        for(WebElement WebElement:beforeSwap)
            bs.add(WebElement.getText());
       // System.out.println(beforeSwap.get(0).getText());
        Driver.findElement(By.xpath("//a[@class='swap-tz btn']")).click();
        List<WebElement> afterSwap=Driver.findElements(By.xpath("//a[@class='time-abb']"));

        List<String> as =new ArrayList<String>();
        for(WebElement WebElement:afterSwap)
            as.add(WebElement.getText());
        Collections.reverse(as);
        Assert.assertEquals(as,bs);

//        System.out.println(beforeSwap.get(0).getText());
//        Assert.assertEquals(beforeSwap.get(0).getText(),afterSwap.get(1).getText());

    }
    @Test(priority=6)
    public void delete() {
        List<WebElement> ElementsBeforeDelete=Driver.findElements(By.xpath("//div[@class='table-time row']"));
        System.out.println(ElementsBeforeDelete.size());
        int a=ElementsBeforeDelete.size();
        Driver.findElement(By.xpath("//div[@data-id='india-hyderabad']")).click();
        Driver.findElement(By.xpath("//a[@class='delete-btn btn']")).click();
        List<WebElement> ElementsAfterDelete=Driver.findElements(By.xpath("//div[@class='table-time row']"));
        System.out.println(ElementsAfterDelete.size());
        int b=ElementsAfterDelete.size();
       Assert.assertEquals(a,b+1);

    }

    @Test(priority=7)
    public void cityDetails() {
        Driver.findElement(By.xpath("//a[@class='time-abb']")).click();

    }

    @Test(priority=8)
    public void quit(){
       Driver.quit();
    }

}
