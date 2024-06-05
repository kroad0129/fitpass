package com.example.fitpass;

public class Item {
    private String title;
    private String subtitle;
    private String price;
    private int imageResId;
    private String username;

    public Item(String title, String subtitle, String price, int imageResId, String username) {
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.imageResId = imageResId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
