/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.VL.Srapper;

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
     * The description of the product.
     */
    private String desc;
    
    /**
     * The price of the product.
     */
    private Float  price;
    
    /**
     * The packaging of the product.
     */
    private String pack;
    
    /**
     * Links to Pictures of the product.
     */
    private String pics[];
    
    /**
     * Links to Signs of the product.
     */
    private String sign[];
    
    /**
     * Link to the nutriscore the product.
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
    private String tabNut[][];
    
    Produit(){
        name = null;
        desc = null;
        price = null;
        pack = null;
        pics = null;
        sign = null;
        nutriScore = null;
        ingr = null;
        aller = null;
        addit = null;
        pres = null;
        tabNut = null;
    }
    
    public void setName(String n){
        this.name = n;
    }
    
    public void setDesc(String d){
        this.desc = d;
    }
    
    public void setPrice(Float p){
        this.price = p;
    }
    
    public void setPack(String p){
        this.pack = p;
    }
    
    public void setPics(String s[]){
        this.pics = s;
    }
    
    public void setSign(String s[]){
        this.sign = s;
    }

    public void setNutriScore (String s){
        this.nutriScore = s;
    }
    
    public void setIngr(String s){
        this.ingr = s;
    }
    
    public void setAller(String s){
        this.aller = s;
    }

    public void setAddit(String s){
        this.addit = s;
    }
    
    public void setPres(String s){
        this.pres = s;
    }
    
    public void setTabNut(String s[][]){
        this.tabNut = s;
    }
    
    
}




