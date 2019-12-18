/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.VL.Scrapper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSection;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author edwin
 */
public class Scrapper {
    String baseUrl = "https://www.mescoursescasino.fr/ecommerce/GC-catalog/fr/WE64904/?moderetrait=Z2" ;
    private WebClient client = new WebClient();
    HtmlPage page = null;
    HtmlForm form = null;
    HtmlTextInput textField = null;
    
    Scrapper(){
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setRedirectEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        page = (HtmlPage)client.getPage(baseUrl);
        form = (HtmlForm) page.getElementById("search");
        textField = form.getInputByName("query");
    }
    
    void Search(String query) throws IOException{
        page = (HtmlPage)client.getPage(baseUrl);
        form = (HtmlForm) page.getElementById("search");
        textField = form.getInputByName("query");                
        textField.type(query);
        final HtmlButton button;
        button = (HtmlButton) page.getByXPath("//button[@title='OK']").get(0);
        page = button.click();
        List<HtmlSection> nodes = page.getByXPath("//section[@class=' tagClick']");
        System.out.println(nodes.get(0).getAttributeNode("data-product-name").getNodeValue());
        List<HtmlElement> marque = page.getByXPath("//strong[@class='color6 ']");
        System.out.println(marque.get(0).asText());
        List<HtmlElement> prix = page.getByXPath("//div[@itemprop='price']");
        System.out.println(prix.get(0).asText());
        
        HtmlAnchor lienArticle;
        HtmlPage page2;
        lienArticle = page.getFirstByXPath("//a[@class='img POP_open']");
        page2 = lienArticle.click();
        HtmlElement aupif = page2.getFirstByXPath("//div[@class='cnt-info']");
        System.out.println(aupif.asText().replace("Le produit","").replaceAll("[\r\n]+", " "));
    }
}
