package com.leetcode.codereview.httpclient.my;

import com.alibaba.excel.util.StringUtils;
import com.leetcode.codereview.httpclient.my.bo.ConversationResponseBo;
import com.leetcode.codereview.httpclient.my.bo.content.Content;
import com.leetcode.codereview.httpclient.my.bo.content.ImageContent;
import com.leetcode.codereview.httpclient.my.bo.content.LinkContent;
import com.leetcode.codereview.httpclient.my.bo.content.TextContent;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.MissingNode;
import org.codehaus.jackson.node.NullNode;

import java.util.ArrayList;
import java.util.List;

public class FridayHttpResponseUtil {
    private static final Integer SUCCESS_CODE = 0;
    private static final String SUCCESS_MESSAGE = "成功";
    private static final String SUCCESS = "success";

    public static String parseToken(JsonNode jsonNode) {
        if (jsonNode instanceof NullNode || !SUCCESS_CODE.equals(jsonNode.path("errcode").getIntValue()) || !SUCCESS.equals(jsonNode.get("errmsg").getTextValue())) {
//            log.error("parseToken error, jsonNode:{}", jsonNode);
            return null;
        }
        JsonNode path = jsonNode.path("data").path("access_token");
        if (path.equals(MissingNode.getInstance())) {
            return null;
        }
        return path.getTextValue();
    }

    public static ConversationResponseBo parseConversationMessage(JsonNode jsonNode) {
        if (jsonNode == NullNode.instance) {
            return null;
        }
        Integer code = jsonNode.get("code").getIntValue();
        if (!SUCCESS_CODE.equals(code)) {
            return null;
        }
        String message = jsonNode.get("message").getTextValue();
        if (StringUtils.isNotBlank(message) && !SUCCESS_MESSAGE.equals(message)) {
            return null;
        }
        ConversationResponseBo conversationResponseBo = new ConversationResponseBo();
        JsonNode data = jsonNode.get("data");
        List<Content> contents = new ArrayList<>();
        for (JsonNode content : data.get("contents")) {
            String type = content.get("type").getTextValue();
            if ("TEXT".equals(type)) {
                TextContent textContent = new TextContent();
                textContent.setText(content.get("text").getTextValue());
                textContent.setType(type);
                contents.add(textContent);
            } else if ("IMAGE".equals(type)) {
                ImageContent imageContent = new ImageContent();
                imageContent.setMime(content.get("mime").getTextValue());
                imageContent.setType(type);
                imageContent.setData(content.get("data").getTextValue());
                imageContent.setName(content.get("name").getTextValue());
                contents.add(imageContent);
            } else if ("LINK".equals(type)) {
                LinkContent linkContent = new LinkContent();
                linkContent.setName(content.get("name").getTextValue());
                linkContent.setHref(content.get("href").getTextValue());
                linkContent.setType(type);
                contents.add(linkContent);
            }
        }
        conversationResponseBo.setConversationId(data.get("conversationId").getTextValue());
        conversationResponseBo.setMessageId(data.get("messageId").getTextValue());
        conversationResponseBo.setRequestId(data.get("requestId").getTextValue());
        conversationResponseBo.setContents(contents);
        return conversationResponseBo;
    }

}
