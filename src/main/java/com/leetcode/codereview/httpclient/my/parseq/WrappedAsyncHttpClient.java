package com.leetcode.codereview.httpclient.my.parseq;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;

import java.util.concurrent.atomic.AtomicReference;

public class WrappedAsyncHttpClient {

  private static final AtomicReference<AsyncHttpClient> asyncHttpClient =
      new AtomicReference<AsyncHttpClient>();


  public static synchronized AsyncHttpClient getNingClient() {
    if (asyncHttpClient.get() == null) {
      initialize(new AsyncHttpClientConfig.Builder().build());
    }
    return asyncHttpClient.get();
  }


  @SuppressWarnings("resource")
  public static synchronized void initialize(AsyncHttpClientConfig cfg) {
    if (!asyncHttpClient.compareAndSet(null, new AsyncHttpClient(cfg))) {
      throw new RuntimeException("async http client concurrently initialized");
    }
  }

  public static void close() {
    if (asyncHttpClient.get() != null) {
      asyncHttpClient.get().close();
    }
  }

  public static WrappedRequestBuilder get(String url) {
    return new WrappedRequestBuilder(getNingClient().prepareGet(url), "GET " + url);
  }

  public static WrappedRequestBuilder connect(String url) {
    return new WrappedRequestBuilder(getNingClient().prepareConnect(url), "CONNECT " + url);
  }

  public static WrappedRequestBuilder options(String url) {
    return new WrappedRequestBuilder(getNingClient().prepareOptions(url), "OPTIONS " + url);
  }

  public static WrappedRequestBuilder head(String url) {
    return new WrappedRequestBuilder(getNingClient().prepareHead(url), "HEAD " + url);
  }

  public static WrappedRequestBuilder post(String url) {
    return new WrappedRequestBuilder(getNingClient().preparePost(url), "POST " + url);
  }

  public static WrappedRequestBuilder put(String url) {
    return new WrappedRequestBuilder(getNingClient().preparePut(url), "PUT " + url);
  }

  public static WrappedRequestBuilder delete(String url) {
    return new WrappedRequestBuilder(getNingClient().prepareDelete(url), "DELETE " + url);
  }

}
