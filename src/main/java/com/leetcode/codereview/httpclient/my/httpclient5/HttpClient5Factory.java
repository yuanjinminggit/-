package com.leetcode.codereview.httpclient.my.httpclient5;

import org.apache.hc.client5.http.async.HttpAsyncClient;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.cookie.StandardCookieSpec;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManager;
import org.apache.hc.client5.http.impl.nio.PoolingAsyncClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;

public class HttpClient5Factory {

    private static final Integer SO_TIMEOUT = 1;
    private static final Integer SOCKET_TIMEOUT = 1;
    private static final Integer CONNECTION_TIMEOUT = 1;
    private static final Integer TIME_TO_LIVE = 10;
    private static final Integer HAND_SHAKE_TIMEOUT = 1;

    private static final Integer CONNECTION_REQUEST_TIMEOUT = 1;

    /*
     * 最大链接数
     */
    private final static int MAX_TOTAL_CONS = 600;
    /*
     * 每个路由最大连接数
     */
    private final static int MAX_ROUTE_CONS = 600;

    private static HttpClient httpClient;
    private static HttpAsyncClient httpAsyncClient;

    public static HttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (HttpClient5Factory.class) {
                if (httpClient == null) {
                    httpClient = httpClient();
                }
            }
        }
        return httpClient;
    }

    public static HttpAsyncClient getHttpAsyncClient() {
        if (httpAsyncClient == null) {
            synchronized (HttpClient5Factory.class) {
                if (httpAsyncClient == null) {
                    httpAsyncClient = httpAsyncClient();
                }
            }
        }
        return httpAsyncClient;
    }

    private static HttpClient httpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(StandardCookieSpec.STRICT)
                        .setConnectionRequestTimeout(Timeout.ofSeconds(CONNECTION_REQUEST_TIMEOUT))
                        .build())
                .build();

    }

    private static PoolingHttpClientConnectionManager httpClientConnectionManager() {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(SSLContexts.createSystemDefault())
                        .setTlsVersions(TLS.V_1_2)
                        .build())
                .setDefaultSocketConfig(SocketConfig.custom()
                        .setSoTimeout(Timeout.ofMinutes(SO_TIMEOUT))
                        .build())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setDefaultConnectionConfig(ConnectionConfig.custom()
                        .setSocketTimeout(Timeout.ofMinutes(SOCKET_TIMEOUT))
                        .setConnectTimeout(Timeout.ofMinutes(CONNECTION_TIMEOUT))
                        .setTimeToLive(TimeValue.ofMinutes(TIME_TO_LIVE))
                        .build())
                .setMaxConnPerRoute(MAX_ROUTE_CONS)
                .setMaxConnTotal(MAX_TOTAL_CONS)
                .build();
    }

    private static PoolingAsyncClientConnectionManager asyncHttpClientConnectionManager() {
        return PoolingAsyncClientConnectionManagerBuilder.create()
                .setTlsStrategy(ClientTlsStrategyBuilder.create()
                        .setSslContext(SSLContexts.createSystemDefault())
                        .setTlsVersions(TLS.V_1_2)
                        .build())
                .setPoolConcurrencyPolicy(PoolConcurrencyPolicy.STRICT)
                .setConnPoolPolicy(PoolReusePolicy.LIFO)
                .setDefaultConnectionConfig(ConnectionConfig.custom()
                        .setSocketTimeout(Timeout.ofMinutes(SO_TIMEOUT))
                        .setConnectTimeout(Timeout.ofMinutes(CONNECTION_TIMEOUT))
                        .setTimeToLive(TimeValue.ofMinutes(TIME_TO_LIVE))
                        .build())
                .setDefaultTlsConfig(TlsConfig.custom()
                        .setVersionPolicy(HttpVersionPolicy.NEGOTIATE)
                        .setHandshakeTimeout(Timeout.ofMinutes(1))
                        .build())
                .setMaxConnPerRoute(MAX_ROUTE_CONS)
                .setMaxConnTotal(MAX_TOTAL_CONS)
                .build();
    }


    private static HttpAsyncClient httpAsyncClient() {
        return HttpAsyncClients.custom()
                .setConnectionManager(asyncHttpClientConnectionManager())
                .setIOReactorConfig(IOReactorConfig.custom()
                        .setSoTimeout(Timeout.ofMinutes(HAND_SHAKE_TIMEOUT))
                        .build())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(StandardCookieSpec.STRICT)
                        .setConnectionRequestTimeout(Timeout.ofSeconds(CONNECTION_REQUEST_TIMEOUT))
                        .build())
                .build();

    }
}
