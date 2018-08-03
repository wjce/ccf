package com.wjc.ccf.test;

public class Message {
    private String content;
    private Type type;

    public enum Type{
        success,
        error,
        warn
    }

    public Message(){}

    public Message(String content, Type type) {
        this.content = content;
        this.type = type;
    }

    public static Message success(String content){
        return new Message(content, Type.success);
    }

    public static Message error(String content){
        return new Message(content, Type.error);
    }

    public static Message warn(String content){
        return new Message(content, Type.warn);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
