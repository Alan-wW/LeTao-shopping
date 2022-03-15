package com.example.shopping.Entity;

/**
 * @Author A_xiba
 * @Date 2022/2/8 15:09
 * @Version 1.0
 */
public class Content {
    private String img;
    private String price;
    private String title;

    public Content() {
    }

    public Content(String img, String price, String title) {
        this.img = img;
        this.price = price;
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
