package com.rehman.eorderingsystem.Model;

public class ComplainModel
{
    String key,message;

    public ComplainModel(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public ComplainModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
