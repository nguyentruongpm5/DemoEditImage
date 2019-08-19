package com.example.demoeditimage.model;

public class ProductItem {
    private long product_id;
    private String product_name;
    private String product_sku;
    private String product_status;
    private String product_decription;
    private String[] product_image;
    private String[] product_image_list;
    private String currency;
    private float discount_price; // giá sau chiết khấu
    private int stock; // tồn kho
    private float original_price; // giá gốc
    private float weight;
    private long category_id;

    public ProductItem(long product_id, String product_name, String product_sku, String product_status, String product_decription, String[] product_image, String[] product_image_list, String currency, float discount_price, int stock, float original_price, float weight, long category_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_sku = product_sku;
        this.product_status = product_status;
        this.product_decription = product_decription;
        this.product_image = product_image;
        this.product_image_list = product_image_list;
        this.currency = currency;
        this.discount_price = discount_price;
        this.stock = stock;
        this.original_price = original_price;
        this.weight = weight;
        this.category_id = category_id;
    }

    public String[] getProduct_image_list() {
        return product_image_list;
    }

    public long getProduct_id() {
        return product_id;
    }


    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_sku() {
        return product_sku;
    }

    public String getProduct_status() {
        return product_status;
    }

    public String getProduct_decription() {
        return product_decription;
    }

    public String[] getProduct_image() {
        return product_image;
    }

    public String getCurrency() {
        return currency;
    }

    public float getDiscount_price() {
        return discount_price;
    }

    public int getStock() {
        return stock;
    }

    public float getOriginal_price() {
        return original_price;
    }

    public float getWeight() {
        return weight;
    }

    public long getCategory_id() {
        return category_id;
    }
}
