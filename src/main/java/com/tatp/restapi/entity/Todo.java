package com.tatp.restapi.entity;

import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class Todo {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String content;
    private Boolean done;

    public Todo(String content) {
        this.content = content;
        this.done = false;
    }

    public Todo(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
