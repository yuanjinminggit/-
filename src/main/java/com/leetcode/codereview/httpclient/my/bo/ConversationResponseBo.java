package com.leetcode.codereview.httpclient.my.bo;

import com.leetcode.codereview.httpclient.my.bo.content.Content;

import java.util.List;

public class ConversationResponseBo {
    private List<Content> contents;
    private String conversationId;

    private String messageId;

    private String requestId;

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "ConversationResponseBo{" +
                "contents=" + contents +
                ", conversationId='" + conversationId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
