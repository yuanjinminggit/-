package com.leetcode.codereview.httpclient.my.bo.content;

public class TextContent extends Content {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextContent{" +
                "text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}