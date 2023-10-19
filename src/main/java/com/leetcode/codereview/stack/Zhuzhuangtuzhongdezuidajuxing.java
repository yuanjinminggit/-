package com.leetcode.codereview.stack;

import org.testng.annotations.Test;

import java.util.Deque;
import java.util.LinkedList;

public class Zhuzhuangtuzhongdezuidajuxing {

    /*枚举右边界*/
    public int largestRectangleArea1(int[] heights) {
        int max = heights[0];
        for (int i = 0; i < heights.length; i++) {
            int min = heights[i];
            for (int j = i; j < heights.length; j++) {
                min = Math.min(min, heights[j]);
                max = Math.max((j - i + 1) * min, max);
            }
        }
        return max;
    }


    /*枚举高*/
    public int largestRectangleArea3(int[] heights) {
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            int left, right;
            left = right = i;
            while (left - 1 >= 0 && heights[left - 1] >= heights[i]) {
                left--;
            }
            while (right + 1 < heights.length && heights[right + 1] >= heights[i]) {
                right++;
            }
            max = Math.max((right - left + 1) * heights[i], max);
        }
        return max;
    }


    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return heights[0];
        }
        int area = 0;
        int[] newHeights = new int[len + 2];
        for (int i = 0; i < len; i++) {
            newHeights[i + 1] = heights[i];
        }
        len += 2;
        heights = newHeights;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(0);
        for (int i = 0; i < len; i++) {
            while (heights[i] < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = i - stack.peek() - 1;
                area = Math.max(area, width * height);
            }
            stack.push(i);
        }

        return area;
    }


//    public String removeDuplicateLetters(String s) {
//
//    }

    @Test
    public void test() {
        largestRectangleArea(new int[]{2, 1, 2});
    }
}
