package com.schoolcoophub.lib.data.stock.adapter;

public class DataPengeluaranStock {
    private String productName;
    private int quantity;
    private int unitPrice;
    private int totalPrice;
    private String transactionDate;
    private String customerEmail;
    private String customerName;

    // Constructor
    public DataPengeluaranStock(String productName, int quantity, int unitPrice, int totalPrice,
                            String transactionDate, String customerEmail, String customerName) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.transactionDate = transactionDate;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
    }

    // Getters and Setters (Optional)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
