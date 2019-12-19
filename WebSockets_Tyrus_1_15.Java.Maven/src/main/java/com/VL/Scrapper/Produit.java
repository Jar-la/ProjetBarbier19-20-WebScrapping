/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.VL.Scrapper;

import java.util.List;

import org.json.JSONObject;

/**
 *
 * @author Alexa
 */

public class Produit {
    /** 
     * The name of the product.
     */
    private String name;
    
    /** 
     * The brand of the product.
     */
    private String brand;
    
    
    /**
     * The description of the product.
     */
    private String desc;
    
    /**
     * The price of the product.
     */
    private String  price;
    
    /**
     *  The price per Kg of the product.
     */
    private Double pricePerKg;

    /**
     * The packaging of the product.
     */
    private String pack;
    
    /**
     * Urls to Pictures of the product.
     */
    private List<String> pics;
    
    /**
     * Urls to Signs images of the product.
     */
    private List<String> sign;
    
    /**
     * Url to the nutriscore image the product.
     */
    private String nutriScore;
    
    /**
     * Ingredients of the product.
     */
    private String ingr;
    
    /**
     * Allergens in the product.
     */
    private String aller;
    
    /**
     * Additive in the product.
     */
    private String addit;
    
    /**
     * Preservatives int the product.
     */
    private String pres;
    
    /**
     * Table of nutritional values.
     */
    private List<List<String>> tabNut;
    
    /**
     * The product under it's JSON form.
     */
    private JSONObject jsonObject;
    
    Produit(){
        name = null;
        brand = null;
        desc = null;
        price = null;
        pricePerKg =null;
        pack = null;
        pics = null;
        sign = null;
        nutriScore = null;
        ingr = null;
        aller = null;
        addit = null;
        pres = null;
        tabNut = null;
        jsonObject = null;
    }
    
    /**
     * Constructor with all parameters of a Product
     * @param name          // Name
     * @param brand         // Brand
     * @param desc          // Description
     * @param price         // Price
     * @param pricePerKg    // Price per Kg
     * @param pack          // Packaging
     * @param pics          // Urls of pictures 
     * @param sign          // Urls of signaltic pictures
     * @param nutriScore    // Url of the nutri score image
     * @param ingr          // List of all ingredient
     * @param aller         // List of all allergens
     * @param addit         // List of all additives
     * @param pres          // List of all preservatives
     * @param tabNut        // Table of nutritional values
     */
    Produit(String name, String brand, String desc, String price, Double pricePerKg,  String pack, List<String> pics, 
            List<String> sign, String nutriScore, String ingr, String aller, 
            String addit, String pres, List<List<String>> tabNut){
        this.name = name;
        this.brand = brand;
        this.desc = desc;
        this.price = price;
        this.pricePerKg = pricePerKg;
        this.pack = pack;
        this.pics = pics;
        this.sign = sign;
        this.nutriScore= nutriScore;
        this.ingr = ingr;
        this.aller = aller;
        this.addit = addit;
        this.pres = pres;
        this.tabNut = tabNut;
    }
    
    Produit(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setBrand(String brand){
        this.brand = brand;
    }
    
    public void setDesc(String desc){
        this.desc = desc;
    }
    
    public void setPrice(String price){
        this.price = price;
    }

    public void setPricePerKg(Double pricePerKg){
        this.pricePerKg = pricePerKg;
    }
    
    public void setPack(String pack){
        this.pack = pack;
    }
    
    public void setPics(List<String> pics){
        this.pics = pics;
    }
    
    public void setSign(List<String> sign){
        this.sign = sign;
    }

    public void setNutriScore (String score){
        this.nutriScore = score;
    }
    
    public void setIngr(String ingr){
        this.ingr = ingr;
    }
    
    public void setAller(String aller){
        this.aller = aller;
    }

    public void setAddit(String addit){
        this.addit = addit;
    }
    
    public void setPres(String pres){
        this.pres = pres;
    }
    
    public void setTabNut(List<List<String>> tab){
        this.tabNut = tab;
    }
    /**
     * Add a picture of the product
     * @param url the url of the product image
     */
    public void pushPics(String url){
        this.pics.add(url);
    }
        
    /**
     * Add a sign to the product
     * @param url the url of the sign image
     */
    public void pushSign(String url){
        this.sign.add(url);
    }
    
    public Produit toProduct(JSONObject jProduit){
        
       return this; 
    }
    
    public JSONObject toJson(){
      
        if(this.jsonObject == null){
            jsonObject = new JSONObject();
        }
        
        
        jsonObject.put ("name" ,this.name);
        jsonObject.put ("brand" ,this.brand);
        jsonObject.put ("desc" ,this.desc);
        jsonObject.put ("price" ,this.price);
        jsonObject.put ("pricePerKg",this.pricePerKg);
        jsonObject.put ("pack" ,this.pack);
        jsonObject.put ("pics" ,this.pics);
        jsonObject.put ("sign" ,this.sign);
        jsonObject.put ("nutScore" ,this.nutriScore);
        jsonObject.put ("ingr" ,this.ingr);
        jsonObject.put ("allerg" ,this.aller);
        jsonObject.put ("addit" ,this.addit);
        jsonObject.put ("pres" ,this.pres);
        jsonObject.put ("tabNut" ,this.tabNut);
        
        return jsonObject;
    }
    
    public String toJsonString(){     
        this.toJson();
        return jsonObject.toString();
    }
    
}
