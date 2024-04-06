package com.leetcode.codereview.stringsimilarity;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSimilarity {

    @Test
    public void test(){
        String s = "对于SKU ID 10437326 在合肥售卖区域看不见的问题，以下是排查结果：1. 商品被识别为自营品。2. 商品的售卖信息显示商品是上架状态。3. 商品处于管控状态，但具体的管控类型为其他类型，这可能是导致商品在客户前台不展示的原因。4. 商品没有被降权，即没有信息缺失导致无法查询到。5. 商品没有设置为非卖标签、定制品标签。6. 本次查询的buId为 340100，skuId为10437326。由于商品处于特定的管控状态，这可能是导致商品在客户前台不展示的原因。如果问题仍未解决，请继续在搜索与类目反馈群中反馈。";
        String t = "对于SKU ID 10437326 在合肥售卖区域看不见的问题，以下是排查结果：\n" +
                "\n" +
                "商品被识别为自营品。\n" +
                "商品的售卖信息显示商品是上架状态。\n" +
                "商品处于管控状态，具体的管控类型为其他类型（controlActionType为9），这可能是导致商品在客户前台不展示的原因。\n" +
                "商品没有被降权，即没有信息缺失导致无法查询到。\n" +
                "商品没有设置为非卖标签、定制品标签。\n" +
                "本次查询的buId为 340100，skuId为10437326。\n" +
                "由于商品处于特定的管控状态（controlActionType为9），这可能是导致商品在客户前台不展示的原因。如果问题仍未解决，请继续在搜索与类目反馈群中反馈。";
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distance = levenshteinDistance.apply(s,t);

        System.out.println(distance);
        System.out.println(1-distance/(Math.max(s.length(),t.length())*1.00));

        s = removeWhitespaceAndNewline(s);
        System.out.println(s);
        t = removeWhitespaceAndNewline(t);
        System.out.println(t);
        System.out.println("extractLongDigits(s,5) = " + extractLongDigits(s, 5));
        System.out.println("extractLongDigits(t,5) = " + extractLongDigits(t, 5));

        int distance2 =  levenshteinDistance.apply(s,t);
        System.out.println(1-distance2/(Math.max(s.length(),t.length())*1.00));

    }

    @Test
    public void test2(){
        String s = "对于SKU ID 21594246 在合肥售卖区域看不见的问题，以下是排查结果：\n" +
                "1. 商品被识别为自营品。\n" +
                "2. 商品的售卖信息显示商品是上架状态。\n" +
                "3. 商品处于管控状态，但不处于高客诉限流或限量状态。\n" +
                "4. 商品被降权，这可能是导致在商城页面看不见该商品的原因。\n" +
                "5. 商品没有设置为非卖标签、定制品标签。\n" +
                "6. 本次查询的buId为 340100，skuId为21594246。\n" +
                "\n" +
                "由于商品被降权，说明商品信息存在空值或者错误值，补充商品信息即可解决问题，如果没有权限或者遇到其他问题，联系商品信息组张楠92解决。如果问题仍未解决，请继续在搜索与类目反馈群中反馈。";
        String t = "对于SKU ID 21905098 在重庆事业部看不见的问题，以下是排查结果：\n" +
                "1. 商品被识别为自营品。\n" +
                "2. 商品的售卖信息显示商品是上架状态。\n" +
                "3. 商品处于管控状态，且处于高客诉限流状态，这可能是导致商品在客户前台不展示的原因。\n" +
                "4. 商品没有被降权，即没有信息缺失导致无法查询到。\n" +
                "5. 商品没有设置为非卖标签、定制品标签。\n" +
                "6. 本次查询的buId为 11000009，skuId为21905098。\n" +
                "\n" +
                "由于商品处于高客诉限流状态，管控与高客诉限流限量都会导致商城页面不展示，如对定级管控有疑问联系yuezhen02咨询。如果问题仍未解决，请继续在搜索与类目反馈群中反馈。";
        s = removeWhitespaceAndNewline(s);
        t = removeWhitespaceAndNewline(t);
        System.out.println(s);
        System.out.println(t);
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        System.out.println("extractLongDigits(s,5) = " + extractLongDigits(s, 5));
        System.out.println("extractLongDigits(t,5) = " + extractLongDigits(t, 5));

        int distance2 =  levenshteinDistance.apply(s,t);
        System.out.println(1-distance2/(Math.max(s.length(),t.length())*1.00));
    }


    public static List<String> extractLongDigits(String str, int length) {
        Pattern pattern = Pattern.compile("\\d{" + length + ",}");
        Matcher matcher = pattern.matcher(str);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }
    public static String removeWhitespaceAndNewline(String str) {
        return str.replaceAll("\\s", "");
    }
}
