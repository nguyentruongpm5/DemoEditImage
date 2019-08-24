package com.example.demoeditimage.model.ShopInfo;

public class ShopInfo {
    private Long id;
    private Long shop_id;
    private String name;
    private Long createDate;
    private Long status;

    public ShopInfo() {
    }

    public ShopInfo(Long id, Long shop_id, String name, Long createDate, Long status) {
        this.id = id;
        this.shop_id = shop_id;
        this.name = name;
        this.status = status;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
