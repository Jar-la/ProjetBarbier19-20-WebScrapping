/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.VL.Scrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 *
 * @author edwin
 */
public class Scrapper {
        
        
        final String baseUrl = "https://www.mescoursescasino.fr/ecommerce/GC-catalog/fr/WE64904/?moderetrait=Z2" ;
        private WebElement form = null;
       
        
        void search(String query){
            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(false);
            WebDriver driver = new FirefoxDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            driver.navigate().to(baseUrl);
            form = driver.findElement(By.id("inpSearch"));
            form.sendKeys(query + Keys.ENTER);
            wait.until(ExpectedConditions.urlContains("rechercheNormaleResultat"));
            System.out.println("URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL URL " + driver.getCurrentUrl());
            
        }

        
        
}
