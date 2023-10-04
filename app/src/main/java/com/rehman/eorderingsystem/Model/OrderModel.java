package com.rehman.eorderingsystem.Model;

public class OrderModel
{
    String name,phone,timeWithAMPM,currentDate,currentDateOnly,dateWithMonth,orderKey,accountCreationKey;


    public OrderModel(String name, String phone, String timeWithAMPM, String currentDate, String currentDateOnly, String dateWithMonth, String orderKey, String accountCreationKey) {
        this.name = name;
        this.phone = phone;
        this.timeWithAMPM = timeWithAMPM;
        this.currentDate = currentDate;
        this.currentDateOnly = currentDateOnly;
        this.dateWithMonth = dateWithMonth;
        this.orderKey = orderKey;
        this.accountCreationKey = accountCreationKey;
    }

    public OrderModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeWithAMPM() {
        return timeWithAMPM;
    }

    public void setTimeWithAMPM(String timeWithAMPM) {
        this.timeWithAMPM = timeWithAMPM;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentDateOnly() {
        return currentDateOnly;
    }

    public void setCurrentDateOnly(String currentDateOnly) {
        this.currentDateOnly = currentDateOnly;
    }

    public String getDateWithMonth() {
        return dateWithMonth;
    }

    public void setDateWithMonth(String dateWithMonth) {
        this.dateWithMonth = dateWithMonth;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getAccountCreationKey() {
        return accountCreationKey;
    }

    public void setAccountCreationKey(String accountCreationKey) {
        this.accountCreationKey = accountCreationKey;
    }
}
