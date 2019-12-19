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
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

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
    
    Scrapper() throws IOException{
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setRedirectEnabled(true);
        client.getOptions().setThrowExceptionOnScriptError(false);
        page = (HtmlPage)client.getPage(baseUrl);
        form = (HtmlForm) page.getElementById("search");
        textField = form.getInputByName("query");
    }
    
    JSONArray Search(String query, int qty) throws IOException{
        HtmlPage page2 = null;
        page = (HtmlPage)client.getPage(baseUrl);
        form = (HtmlForm) page.getElementById("search");
        textField = form.getInputByName("query");
        textField.type(query);
        final HtmlButton button;
        button = (HtmlButton) page.getByXPath("//button[@title='OK']").get(0);
        page = button.click();
        
        List<HtmlAnchor> lienArticle;
        lienArticle = page.getByXPath("//a[@class='img POP_open']");
        
        List <JSONObject> jsonProduits = new ArrayList<>();
        
        List<HtmlSection> nodes = page.getByXPath("//section[@class=' tagClick']");
        List<HtmlElement> marque = page.getByXPath("//strong[@class='color6 ']");
        List<HtmlElement> prix = page.getByXPath("//div[@itemprop='price']");
        List<HtmlElement> prixKiloMasse = page.getByXPath("//span[@class='info']");
        
        for(int i = 0; i < qty; i++)
        {
            Produit prod = new Produit();
          
            // System.out.println(nodes.get(0).getAttributeNode("data-product-name").getNodeValue());
            prod.setName(nodes.get(i).getAttributeNode("data-product-name").getNodeValue());

            // System.out.println(marque.get(0).asText());
            prod.setBrand(marque.get(i).asText());

            // System.out.println(prix.get(0).asText());
            prod.setPrice(prix.get(i).asText());
            
            page2 = lienArticle.get(i).click();
            List<HtmlElement> details = page2.getByXPath("//div[@class='cnt-info']");
            prod.setDesc(details.get(0).asText().replace("Le produit","").replaceFirst("\r\n+", ""));
            
            
            //prod.setIngr(details.get(3).asText());
            if(details.size()>=2)
            {
                boolean isIngr = false;
                for(int j = 1; j<details.size(); j++){
                    if(details.get(j).asText().contains("Que contient le produit ?")){
                        String splitArray_ingr[] = details.get(j).asText().replace("Que contient le produit ?","").replaceFirst("\r\n+", "").split("[.]");
                        if (splitArray_ingr[0].length()>5){
                            prod.setIngr(splitArray_ingr[0]);
                            isIngr = true;
                        }else{
                            prod.setIngr("Casino ne fourni pas d'informations à ce sujet.");
                        }
                    }
                }
                if(!isIngr){
                    prod.setIngr("Casino ne fourni pas d'informations à ce sujet.");
                }
                
            }else{
                prod.setIngr("Casino ne fourni pas d'informations à ce sujet.");
            }
            
            if(prixKiloMasse.get(i).asText().contains(" | "))
            {
                String splitArray[] = prixKiloMasse.get(i).asText().split(" | ");
                prod.setPack(splitArray[0]);
                prod.setPricePerKg(splitArray[2]);
            }else{
                prod.setPack(prixKiloMasse.get(i).asText());
            }
            
            
            HtmlElement picture = page2.getFirstByXPath("//a[@class='zoom-img1']");
            prod.setPic("https:" + picture.getAttributeNode("href").getNodeValue());
            
            jsonProduits.add(prod.toJson());
        }
        
        JSONArray jProduits = new JSONArray(jsonProduits);  
        System.out.println("JSONArray.toString" + jProduits.toString());
        
        return(jProduits);
    }
}
