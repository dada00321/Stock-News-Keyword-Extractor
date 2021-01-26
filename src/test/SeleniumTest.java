package test;

import org.openqa.selenium.By;  
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.firefox.FirefoxDriver;  

public class SeleniumTest {
	@SuppressWarnings("unchecked")
    public static void main(String[] args) throws InterruptedException {
    	String headline;
    	System.setProperty("webdriver.gecko.driver", "D:\\geckodriver\\firefox\\geckodriver-v0.28.0-win64\\geckodriver.exe");  
    	WebDriver driver = new FirefoxDriver();
    	driver.navigate().to("http://www.javatpoint.com/");
    	//driver.get("https://money.udn.com/money/story/12509/5145659");
    	
    	/*
    	headline = driver.findElement(By.xpath("//h2[@id='story_art_title']")).getText();
    	System.out.printf("headline: %s", headline);
    	// close(): 一般關閉一個tab    quit(): 完全關閉瀏覽器
    	driver.quit();*/
    }
}