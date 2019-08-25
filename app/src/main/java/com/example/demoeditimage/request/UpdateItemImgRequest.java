package com.example.demoeditimage.request;


import com.example.demoeditimage.utils.MyConst;

import java.util.List;

public class UpdateItemImgRequest {
    Long item_id;
    List<String> images;
    Long partner_id = MyConst.partner_id;
    Long shopid;

    public UpdateItemImgRequest() {
    }

    public UpdateItemImgRequest(Long item_id, List<String> images, Long partner_id, Long shopid) {
        this.item_id = item_id;
        this.images = images;
        this.partner_id = partner_id;
        this.shopid = shopid;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Long partner_id) {
        this.partner_id = partner_id;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }
}
