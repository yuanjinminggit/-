package com.leetcode.codereview.httpclient.my;

import com.alibaba.fastjson.JSONObject;
import com.leetcode.codereview.httpclient.my.bo.ConversationRequestBo;
import com.leetcode.codereview.httpclient.my.enums.UserTypeEnum;

import java.util.List;
import java.util.Map;

public interface FridayHttpRequestUtil {

    public static String createConversationRequestBOJson(String appId, String userId, List<String> utterance,
                                                         String accessToken, Map<String, String> businessInfo) {
        ConversationRequestBo conversationRequestBo = new ConversationRequestBo();
        conversationRequestBo.setAppId(appId);
        conversationRequestBo.setUserId(userId);
        conversationRequestBo.setUserType(UserTypeEnum.DX_UID.getValue());
        conversationRequestBo.setUtterances(utterance);
        conversationRequestBo.setStream(Boolean.FALSE);
        conversationRequestBo.setAccessToken(accessToken);
        conversationRequestBo.setBusinessInfo(businessInfo);
        return JSONObject.toJSONString(conversationRequestBo);
    }

}
