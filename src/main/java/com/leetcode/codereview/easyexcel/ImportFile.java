package com.leetcode.codereview.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.leetcode.codereview.easyexcel.listener.Sample1DataListener;
import com.leetcode.codereview.easyexcel.sample.Sample1;
import com.leetcode.codereview.easyexcel.sample.Sample2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ImportFile {
    // 文件路径
    public static final String fileName = "/Users/yjm/Desktop/文件.xlsx";

    public static void main(String[] args) {
        // 读取sample1
        ArrayList<Sample1> sample1List = new ArrayList<>();
        EasyExcel.read(fileName, Sample1.class, new Sample1DataListener(sample1List)).sheet("Sample 1").doRead();
        // 读取sample2
//        ArrayList<Sample2> sample2List = new ArrayList<>();
//        EasyExcel.read(fileName, Sample2.class, new Sample2DataListener(sample2List)).sheet("Sample 2").doRead();
//
//        // 读取sample2
//        ArrayList<Sample3> sample3List = new ArrayList<>();
//        EasyExcel.read(fileName, Sample3.class, new Sample3DataListener(sample3List)).sheet("Sample 3").doRead();
        question3(sample1List);
//        question4(sample1List);
//        question8(sample1List);
//        question12(sample2List);
//        question13(sample2List);
    }

    private static void question13(ArrayList<Sample2> sample2List) {
        double incomeSum = sample2List.stream().mapToDouble(Sample2::getIncome).sum();
        double orderNumSum = sample2List.stream().mapToDouble(Sample2::getOrderComplete).sum();
        double incomeOneOrderNo = incomeSum / orderNumSum;
        double res =500/incomeOneOrderNo;
        System.out.println(res);

    }

    private static void question12(ArrayList<Sample2> sample2List) {
        double incomeSum = sample2List.stream().mapToDouble(Sample2::getIncome).sum();
        double onlineSum = sample2List.stream().mapToDouble(Sample2::getOnlineTime).sum();
        double kiloSum = sample2List.stream().mapToDouble(Sample2::getTotalKilo).sum();
        double oneHourIncome = incomeSum / onlineSum;
        double oneHourKilo = kiloSum / onlineSum;
        double response = (200000 + 8000 * 10 + (double) 365 * 10 / 7 * 2000) / ((oneHourIncome - (oneHourKilo * 9 / 100 * 6.6)) * 365 * 10);
        System.out.println("每天需要工作" + response + "小时");
    }

    private static void question8(ArrayList<Sample1> sample1List) {
        List<Sample1> list = sample1List.stream().filter(v -> "厦门市".equals(v.getCity())).collect(Collectors.toList());
    }

    private static void question3(ArrayList<Sample1> data) {
        Map<String, List<Sample1>> groupedData = data.stream()
                .collect(Collectors.groupingBy(Sample1::getCity, Collectors.mapping(Function.identity(), Collectors.toList())));
        String maxCity = "";
        int maxOrder = 0;
        String maxTime = "";
        HashMap<String, Integer> map = new HashMap<String, Integer>() {{
            put("周一", 1);
            put("周二", 2);
            put("周三", 3);
            put("周四", 4);
            put("周五", 5);
            put("周六", 6);
            put("周日", 7);
        }};
        // 城市
        for (Map.Entry<String, List<Sample1>> entry : groupedData.entrySet()) {
            List<Sample1> cityData = entry.getValue();
            cityData.sort(new Comparator<Sample1>() {
                @Override
                public int compare(Sample1 o1, Sample1 o2) {
                    return map.get(o1.getWeek()).equals(map.get(o2.getWeek())) ? o1.getPeriod() - o2.getPeriod() : map.get(o1.getWeek()).intValue() - (map.get(o2.getWeek()));
                }
            });
            // 星期时段，此问题可以用滑动窗口进行性能优化，此处不予优化
            for (int i = 0; i < cityData.size() * 2; i++) {
                int sum = 0;
                for (int j = i; j < i + 12 && j < cityData.size() * 2; j++) {
                    sum += cityData.get(j % cityData.size()).getComNum();
                }
                if (sum > maxOrder) {
                    maxOrder = sum;
                    maxCity = entry.getKey();
                    maxTime = cityData.get(i % cityData.size()).getPeriod() + " to " + cityData.get((i + 12) % cityData.size()).getPeriod();
                }
            }
        }

        System.out.println("城市: " + maxCity);
        System.out.println("时间: " + maxTime);
        System.out.println("订单数: " + maxOrder);
    }

    private static void question4(ArrayList<Sample1> data) {
        Map<String, List<Sample1>> map = data.stream().collect(Collectors.groupingBy(Sample1::getCity, Collectors.mapping(Function.identity(), Collectors.toList())));
        double max = 0;
        String maxCity = "";
        for (Map.Entry<String, List<Sample1>> entry : map.entrySet()) {
            List<Sample1> list = entry.getValue();
            long callSum = list.stream().mapToLong(Sample1::getCallNum).sum();
            long resSum = list.stream().mapToLong(Sample1::getResNUm).sum();

            double res = (double) callSum / resSum;
            max = Math.max(max, Math.abs(res - 1));
            if (max == Math.abs(res - 1)) {
                maxCity = entry.getKey();
            }
        }
        Integer maxPeriod = 0;
        double maxRes = 0;
        List<Sample1> list = map.get(maxCity);
        Map<Integer, List<Sample1>> listMap = list.stream().collect(Collectors.groupingBy(Sample1::getPeriod, Collectors.mapping(Function.identity(), Collectors.toList())));
        for (Map.Entry<Integer, List<Sample1>> entry : listMap.entrySet()) {
            Integer period = entry.getKey();
            List<Sample1> sample1List = entry.getValue();
            long callSum = sample1List.stream().mapToLong(Sample1::getCallNum).sum();
            long resSum = sample1List.stream().mapToLong(Sample1::getResNUm).sum();
            double res = (double) callSum / resSum;
            maxRes = Math.max(maxRes, Math.abs(res - 1));
            if (maxRes == Math.abs(res - 1)) {
                maxPeriod = period;
            }
        }
        System.out.println("供需最不匹配的城市为" + maxCity);
        System.out.println("供需最不匹配的时段为" + maxPeriod);
    }

}
