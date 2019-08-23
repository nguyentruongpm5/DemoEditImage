package com.example.demoeditimage.phung.model.response;

import java.util.List;

public class UpdateItemImgResponse {
    List<String> images;
    Long shopid;
    Long partner_id;
    Long item_id;
    String msg;

    public UpdateItemImgResponse() {
    }

    public UpdateItemImgResponse(List<String> images, Long shopid, Long partner_id, Long item_id) {
        this.images = images;
        this.shopid = shopid;
        this.partner_id = partner_id;
        this.item_id = item_id;
    }


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public Long getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Long partner_id) {
        this.partner_id = partner_id;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
