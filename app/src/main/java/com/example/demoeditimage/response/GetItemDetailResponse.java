package com.example.demoeditimage.response;

import com.example.demoeditimage.model.Product;

public class GetItemDetailResponse {
    Product item;

    public GetItemDetailResponse() {
    }

    public GetItemDetailResponse(Product item) {
        this.item = item;
    }

    public Product getItem() {
        return item;
    }

    public void setItem(Product item) {
        this.item = item;
    }
}
