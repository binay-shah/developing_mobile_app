package com.example.todoappfragment.model;

import java.util.Date;
import java.util.UUID;

public class Todo {

    private String title;
    private String detail;
    private Date date;
    private int  isComplete;
    private UUID id;

    public Todo(UUID id){
        this.id = id;
        date = new Date();
    }


    public Todo() {
        this(UUID.randomUUID());
        /*
        mId = UUID.randomUUID();
        mDate = new Date();
        */
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int isComplete() {
        return isComplete;
    }

    public void setComplete(int complete) {
        isComplete = complete;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID mId) {
        this.id = mId;
    }
}
