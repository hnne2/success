package com.example.myapplication.repository.api.models;

public class CanselRequestt {
    String comment;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
    public CanselRequestt(String comment){
        this.comment =comment;
    }
}
