package com.example.project.Level2.GraphRepresentation;

import java.util.Vector;

public class Post {
    private String body;
    private Vector<String>topics;
    Post(){
        body = "";
        topics = new Vector<String>();
    }
    Post(String body,Vector<String>topics){
        this.body=body;
        this.topics = topics;
    }

    public String getBody() {
        return body;
    }

    public Vector<String> getTopics() {
        return topics;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTopics(Vector<String> topics) {
        this.topics = topics;
    }

    void addTopic(String topic){
        this.topics.add(topic);
    }

}
