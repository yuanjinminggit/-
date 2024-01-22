package com.leetcode.codereview.designing;

import java.util.*;

class Twitter {

    private class Tweet {
        private int id;

        private int timestamp;
        private Tweet next;

        public Tweet(int id, int timestamp) {
            this.id = id;
            this.timestamp = timestamp;
        }
    }

    // 用户id和推文单链表的对应关系
    private Map<Integer, Tweet> twitterMap;

    // 追随用户
    private Map<Integer, Set<Integer>> followings;

    private static int timestamp = 0;

    private static PriorityQueue<Tweet> maxHeap;

    public Twitter() {
        followings = new HashMap<>();
        twitterMap = new HashMap<>();
        maxHeap = new PriorityQueue<Tweet>((a, b) -> b.timestamp - a.timestamp);
    }

    public void postTweet(int userId, int tweetId) {
        timestamp++;
        if (twitterMap.containsKey(userId)) {
            Tweet oldTweet = twitterMap.get(userId);
            Tweet newTweet = new Tweet(tweetId, timestamp);
            newTweet.next = oldTweet;
            twitterMap.put(userId, newTweet);
        } else {
            twitterMap.put(userId, new Tweet(tweetId, timestamp));
        }
    }

    public List<Integer> getNewsFeed(int userId) {
        maxHeap.clear();
        if (twitterMap.containsKey(userId)) {
            maxHeap.offer(twitterMap.get(userId));
        }
        Set<Integer> followingList = followings.get(userId);
        if (followingList != null && followingList.size() > 0) {
            for (Integer followingId : followingList) {
                Tweet tweet = twitterMap.get(followingId);
                if (tweet != null) {
                    maxHeap.offer(tweet);
                }
            }
        }
        ArrayList<Integer> res = new ArrayList<>(10);
        int count = 0;
        while (!maxHeap.isEmpty() && count < 10) {
            Tweet poll = maxHeap.poll();
            res.add(poll.id);
            if (poll.next != null) {
                maxHeap.add(poll.next);
            }
            count++;
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        if (followeeId == followerId) {
            return;
        }
        Set<Integer> set = followings.get(followerId);
        if (set == null) {
            HashSet<Integer> init = new HashSet<>();
            init.add(followeeId);
            followings.put(followerId, init);
        } else {
            set.add(followeeId);
        }
    }

    public void unfollow(int followerId, int followeeId) {
        if (followeeId == followerId) {
            return;
        }
        Set<Integer> set = followings.get(followerId);
        if (set != null) {
            set.remove(followeeId);
        }
    }
}