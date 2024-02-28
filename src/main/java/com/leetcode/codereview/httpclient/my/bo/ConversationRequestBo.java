package com.leetcode.codereview.httpclient.my.bo;

import java.util.List;
import java.util.Map;

public class ConversationRequestBo {
    private String appId;
    private String userId;

    private String userType;

    private List<String> utterances;

    private boolean stream = true;

    private boolean debug = false;

    private String accessToken;
    private boolean multiTurn = true;

    private Map<String, String> businessInfo;

    private String conversationId;

    private Enum<ExpirationPolicyEnum> expirationPolicy;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<String> getUtterances() {
        return utterances;
    }

    public void setUtterances(List<String> utterances) {
        this.utterances = utterances;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isMultiTurn() {
        return multiTurn;
    }

    public void setMultiTurn(boolean multiTurn) {
        this.multiTurn = multiTurn;
    }

    public Map<String, String> getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(Map<String, String> businessInfo) {
        this.businessInfo = businessInfo;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Enum<ExpirationPolicyEnum> getExpirationPolicy() {
        return expirationPolicy;
    }

    public void setExpirationPolicy(Enum<ExpirationPolicyEnum> expirationPolicy) {
        this.expirationPolicy = expirationPolicy;
    }

    @Override
    public String toString() {
        return "ConversationRequestBo{" +
                "appId='" + appId + '\'' +
                ", userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", utterances=" + utterances +
                ", stream=" + stream +
                ", debug=" + debug +
                ", accessToken='" + accessToken + '\'' +
                ", multiTurn=" + multiTurn +
                ", businessInfo=" + businessInfo +
                ", conversationId='" + conversationId + '\'' +
                ", expirationPolicy=" + expirationPolicy +
                '}';
    }
}
