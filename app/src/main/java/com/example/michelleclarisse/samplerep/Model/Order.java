package com.example.michelleclarisse.samplerep.Model;

public class Order {

    private String ProdId;
    private String ProdDiscount;
    private String ProdName;
    private String ProdPrice;
    private String ProdQty;

    public Order() {

    }

    public Order(String prodId, String prodName, String prodQty, String prodPrice, String prodDiscount ) {
        ProdId = prodId;
        ProdName = prodName;
        ProdQty = prodQty;
        ProdPrice = prodPrice;
        ProdDiscount = prodDiscount;
    }

    public String getProdId() {
        return ProdId;
    }

    public void setProdId(String prodId) {
        ProdId = prodId;
    }

    public String getProdDiscount() {
        return ProdDiscount;
    }

    public void setProdDiscount(String prodDiscount) {
        ProdDiscount = prodDiscount;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public String getProdPrice() {
        return ProdPrice;
    }

    public void setProdPrice(String prodPrice) {
        ProdPrice = prodPrice;
    }

    public String getProdQty() {
        return ProdQty;
    }

    public void setProdQty(String prodQty) {
        ProdQty = prodQty;
    }
}
