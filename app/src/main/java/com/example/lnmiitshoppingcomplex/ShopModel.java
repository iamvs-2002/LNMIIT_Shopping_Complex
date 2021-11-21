package com.example.lnmiitshoppingcomplex;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShopModel {
    String name;
    String startTime;
    String endTime;
    String shopkeeperName;
    Boolean status; // 1 for open and 0 for closed
    int color;

    public ShopModel(String name, String startTime, String endTime, String shopkeeperName, int color) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shopkeeperName = shopkeeperName;
        this.color=color;
        setStatus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        setStatus();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        setStatus();
    }

    public String getShopkeeperName() {
        return shopkeeperName;
    }

    public void setShopkeeperName(String shopkeeperName) {
        this.shopkeeperName = shopkeeperName;
    }

    public Boolean getStatus() {
        return status;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private void setStatus() {
        Date currentTime = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("hh:mm");

        try {
            Date start = format.parse(startTime);
            Date end = format.parse(endTime);
            if (currentTime.after(start) && currentTime.before(end))
                this.status=true;
            else
                this.status=false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
