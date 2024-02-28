package com.leetcode.codereview.httpclient.my;

import com.leetcode.codereview.httpclient.my.bo.ConversationResponseBo;
import com.linkedin.parseq.Task;

public interface FridayHttpRequest {

    String UTF_8_CHARSET = "utf-8";
    String TOKEN_URL = "https://auth-ai.vip.sankuai.com/oauth/v2/token";
    String CONVERSATION_URL = "https://aigc.sankuai.com/conversation/v2/openapi";

    // 获取token固定参数
    String CLIENT_ID = "pxbhLGdHe44D+KOAdvoIAacA/P0LoPP+/duhOOwr97Q=";
    String CLIENT_SECRET = "5cf8a8e3a69c4f01835772d343c0a0cd";
    String GRANT_TYPE = "client_credentials";


    String getAccessTokenSync();

    Task<String> getAccessTokenAsync();

    ConversationResponseBo getConversationMessageSync();

    Task<ConversationResponseBo> getConversationMessageAsync();

}
