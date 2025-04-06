package com.example.ticket_sale.model;

public class SettingItem {
    public static final int TYPE_FRAGMENT = 0;
    public static final int TYPE_ACTIVITY = 1;
    public static final int TYPE_PHONE = 2;
    public static final int TYPE_EMAIL = 3;
    public static final int TYPE_WEB = 4;
    public static final int TYPE_WEBVIEW = 5;
    private int type;
    private String title;
    private String data;
    private Integer iconResId;

    public SettingItem(int type, String title, String data, Integer iconResId) {
        this.type = type;
        this.title = title;
        this.data = data;
        this.iconResId = iconResId;
    }
    public SettingItem(){};

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getIconResId() {
        return iconResId;
    }

    public void setIconResId(Integer iconResId) {
        this.iconResId = iconResId;
    }
}
