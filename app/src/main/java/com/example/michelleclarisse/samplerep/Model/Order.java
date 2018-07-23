package com.example.michelleclarisse.samplerep.Model;

public class Order {

    private String ProdDiscount;
    private String ProdName;
    private String ProdPrice;
    private String ProdQty;
    private String ProdSubtotal;

    public Order() {

    }

    public Order(String prodDiscount, String prodName, String prodPrice, String prodQty, String prodSubtotal) {
        ProdDiscount = prodDiscount;
        ProdName = prodName;
        ProdPrice = prodPrice;
        ProdQty = prodQty;
        ProdSubtotal = prodSubtotal;
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

    public String getProdSubtotal() {
        return ProdSubtotal;
    }

    public void setProdSubtotal(String prodSubtotal) {
        ProdSubtotal = prodSubtotal;
    }
}
