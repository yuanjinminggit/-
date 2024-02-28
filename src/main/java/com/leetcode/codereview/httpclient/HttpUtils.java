/*
package com.leetcode.codereview.httpclient;


import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



public final class HttpUtils {
    */
/*
     * 最大链接数
     *//*

    private final static int MAX_TOTAL_CONS = 600;
    */
/*
     * 每个路由最大连接数
     *//*

    private final static int MAX_ROUTE_CONS = 600;
    */
/*
     * 链接超时时间，默认5秒
     *//*

    private final static int CON_TIMEOUT = 5 * 1000;
    */
/*
     * 读取超时时间，默认20秒
     *//*

    private final static int READ_TIMEOUT = 20 * 1000;

    private static RequestConfig defaultRequestConfig;
    private static Registry<ConnectionSocketFactory> defaultSocketFactoryRegistry;
    private static PoolingHttpClientConnectionManager defaultPoolConnectionManager;
    private static CloseableHttpClient defaultPoolHttpClient;

    */
/**
     * 获取支持http和https的Registry（创建PoolingHttpClientConnectionManager时，需要）
     *
     * @return Registry
     *//*

    public static Registry<ConnectionSocketFactory> getDefaultSocketFactoryRegistry() {
        if (defaultSocketFactoryRegistry == null) {
            synchronized (com.sensetime.tsc.cobra.utils.http.HttpUtils.class) {
                if (defaultSocketFactoryRegistry == null) {
                    //采用绕过验证的方式处理https请求
                    SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(SSLUtils.getIgnoreSslContext(), SSLUtils.getSupportedProtocols(), null, NoopHostnameVerifier.INSTANCE);
                    //设置协议http和https对应的处理socket链接工厂的对象
                    defaultSocketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.INSTANCE)
                            .register("https", sslConnectionSocketFactory)
                            .build();
                }
            }
        }
        return defaultSocketFactoryRegistry;
    }

    */
/**
     * 采用绕过https验证的方式，创建PoolingHttpClientConnectionManager（创建CloseableHttpClient的时候需要）
     *
     * @return PoolingHttpClientConnectionManager
     *//*

    private static PoolingHttpClientConnectionManager getDefaultPoolConnectionManager() {
        if (defaultPoolConnectionManager == null) {
            synchronized (com.sensetime.tsc.cobra.utils.http.HttpUtils.class) {
                if (defaultPoolConnectionManager == null) {
                    //采用绕过https验证的方式，创建PoolingHttpClientConnectionManager
                    defaultPoolConnectionManager = new PoolingHttpClientConnectionManager(getDefaultSocketFactoryRegistry());
                    defaultPoolConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONS);
                    defaultPoolConnectionManager.setMaxTotal(MAX_TOTAL_CONS);
                }
            }
        }
        return defaultPoolConnectionManager;
    }

    */
/**
     * 创建链接超时和读去超时为默认值的 RequestConfig（创建CloseableHttpClient的时候需要）
     *
     * @return RequestConfig
     *//*

    public static RequestConfig getDefaultRequestConfig() {
        if (defaultRequestConfig == null) {
            synchronized (HttpUtils.class) {
                if (defaultRequestConfig == null) {
                    //创建httpclient
                    defaultRequestConfig = RequestConfig.custom()
                            .setNormalizeUri(false)//是否对URL进行修正
                            .setConnectTimeout(CON_TIMEOUT)
                            .setSocketTimeout(READ_TIMEOUT).build();
                }
            }
        }
        return defaultRequestConfig;
    }

    */
/**
     * 返回默认的httpClient
     *
     * @return CloseableHttpClient
     *//*

    private static CloseableHttpClient getDefaultPoolHttpClient() {
        if (defaultPoolHttpClient == null) {
            synchronized (com.sensetime.tsc.cobra.utils.http.HttpUtils.class) {
                if (defaultPoolHttpClient == null) {
                    //创建httpclient
                    defaultPoolHttpClient = HttpClients.custom()
                            .setDefaultRequestConfig(getDefaultRequestConfig())
                            .setConnectionManager(getDefaultPoolConnectionManager())
                            .build();
                }
            }
        }
        return defaultPoolHttpClient;
    }


    */
/**
     * 根据传入的参数，创建具有缓存功能的httpClient。如果需要关闭，需要自己显示的close httpClient,close之后，不可再通过该client发起请求。
     *
     * @param connectTimeout  链接超时时间（秒）
     * @param readTimeout     读取数据超时时间（秒）
     * @param maxRouteConnect 默认的最大链接数
     * @param maxTotalConnect 每个路由最大连接数
     * @param normalizeUri    是否对URL进行修正（例如http://127.0.0.1/abc//dfs会修正为：http://127.0.0.1/abc/dfs）
     * @return CloseableHttpClient
     *//*

    public static CloseableHttpClient createHttpClient(int connectTimeout, int readTimeout, int maxRouteConnect, int maxTotalConnect, boolean normalizeUri) {
        checkArgument(connectTimeout > 0, "connectTimeout时间必须大于0秒，传参为：" + connectTimeout);
        checkArgument(readTimeout > 0, "readTimeout时间必须大于0秒，传参为：" + readTimeout);
        checkArgument(maxRouteConnect > 0, "maxRouteConnect时间必须大于0秒，传参为：" + maxRouteConnect);
        checkArgument(maxTotalConnect > 0, "maxRouteConnect时间必须大于0秒，传参为：" + maxRouteConnect);

        RequestConfig customConfig = RequestConfig.custom()
                .setNormalizeUri(normalizeUri)
                .setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(readTimeout * 1000).build();

        //这里必须重新创建一个PoolingHttpClientConnectionManager，否则在其他CloseableHttpClient进行close的时候，这个新建的HttpClient的PoolingHttpClientConnectionManager也会关闭
        PoolingHttpClientConnectionManager customPollHttpClient = new PoolingHttpClientConnectionManager(getDefaultSocketFactoryRegistry());
        customPollHttpClient.setDefaultMaxPerRoute(maxRouteConnect);
        customPollHttpClient.setMaxTotal(maxTotalConnect);
        return HttpClients.custom()
                .setDefaultRequestConfig(customConfig)
                .setConnectionManager(customPollHttpClient)
                .build();
    }
    */
/**
     * 创建基于Http Basic 认证的请求client，会自动对请求的url中添加Authorization的header，填充的值为：base64编码的username:password的字符串
     *
     * @param connectTimeout  链接超时时间（秒）
     * @param readTimeout     读取数据超时时间（秒）
     * @param maxRouteConnect 默认的最大链接数
     * @param maxTotalConnect 每个路由最大连接数
     * @param normalizeUri    是否对URL进行修正（例如http://127.0.0.1/abc//dfs会修正为：http://127.0.0.1/abc/dfs）
     * @param userName 用户名
     * @param password 密码
     * @return CloseableHttpClient
     *//*


    public static CloseableHttpClient createHttpClientWithBasicAuth(int connectTimeout, int readTimeout, int maxRouteConnect, int maxTotalConnect, boolean normalizeUri, String userName,String password) {
        checkArgument(connectTimeout > 0, "connectTimeout时间必须大于0秒，传参为：" + connectTimeout);
        checkArgument(readTimeout > 0, "readTimeout时间必须大于0秒，传参为：" + readTimeout);
        checkArgument(maxRouteConnect > 0, "maxRouteConnect时间必须大于0秒，传参为：" + maxRouteConnect);
        checkArgument(maxTotalConnect > 0, "maxRouteConnect时间必须大于0秒，传参为：" + maxRouteConnect);

        RequestConfig customConfig = RequestConfig.custom()
                .setNormalizeUri(normalizeUri)
                .setConnectTimeout(connectTimeout * 1000)
                .setSocketTimeout(readTimeout * 1000).build();

        //这里必须重新创建一个PoolingHttpClientConnectionManager，否则在其他CloseableHttpClient进行close的时候，这个新建的HttpClient的PoolingHttpClientConnectionManager也会关闭
        PoolingHttpClientConnectionManager customPollHttpClient = new PoolingHttpClientConnectionManager(getDefaultSocketFactoryRegistry());
        customPollHttpClient.setDefaultMaxPerRoute(maxRouteConnect);
        customPollHttpClient.setMaxTotal(maxTotalConnect);
        return HttpClients.custom()
                .addInterceptorFirst(new HttpBasicAuthInterceptor(userName,password))
                .setDefaultRequestConfig(customConfig)
                .setConnectionManager(customPollHttpClient)
                .build();
    }


    */
/**
     * 根据用户自定义的httpclient，调用方法。如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @param header     可为null的header
     * @param jsonBody   json格式的参数
     * @return {@link HttpStringResponse}
     * @throws IOException 抛出异常
     *//*

    public static HttpStringResponse sendJsonPost(CloseableHttpClient httpClient, String url, Map<String, String> header, String jsonBody) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity se = new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
        httpPost.setEntity(se);
        dealHeader(httpPost, header, true);
        try {
            return executeWrapperStringResponse(httpClient, httpPost);
        } catch (IOException ioException) {
            log.error("sendJsonPost has error,url:{},param:{}.", url, jsonBody, ioException);
            throw ioException;
        }
    }


    */
/**
     * 发送post json请求，如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param url      url链接
     * @param header   可为null的header
     * @param jsonBody json格式的参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendJsonPost(String url, Map<String, String> header, String jsonBody) throws IOException {
        return sendJsonPost(null, url, header, jsonBody);
    }

    */
/**
     * 发送post json请求，如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param url      url链接
     * @param jsonBody json格式的参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendJsonPost(String url, String jsonBody) throws IOException {
        return sendJsonPost(null, url, null, jsonBody);
    }

    */
/**
     * 根据用户自定义的httpclient，调用方法。
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @param header     可为null的header
     * @param body       请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendFormPost(CloseableHttpClient httpClient, String url, Map<String, String> header, Map<String, Object> body) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(convertToUrlEncodedFormEntity(body));
        } catch (UnsupportedEncodingException e) {
            log.error("convertToUrlEncodedFormEntity has error,url:{},param:{}.", url, mapParamToStringLog(body), e);
            throw e;
        }
        dealHeader(httpPost, header, false);
        try {
            return executeWrapperStringResponse(httpClient, httpPost);
        } catch (IOException ioException) {
            log.error("executeWrapperStringResponse has error,url:{},param:{}.", url, mapParamToStringLog(body), ioException);
            throw ioException;
        }

    }

    */
/**
     * Form格式的post请求
     *
     * @param url    url链接
     * @param header 可为null的header
     * @param body   请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendFormPost(String url, Map<String, String> header, Map<String, Object> body) throws IOException {
        return sendFormPost(null, url, header, body);
    }

    */
/**
     * Form格式的post请求
     *
     * @param url  url链接
     * @param body 请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendFormPost(String url, Map<String, Object> body) throws IOException {
        return sendFormPost(null, url, null, body);
    }

    */
/**
     * 根据用户自定义的httpclient，发送delete 的json请求。如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @param header     可为null的header
     * @param jsonBody   请求参数  JSON格式字符串
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendJsonDelete(CloseableHttpClient httpClient, String url, Map<String, String> header, String jsonBody) throws IOException {
        HttpDeleteWithBody delete = new HttpDeleteWithBody(url);
        StringEntity se;
        try {
            se = new StringEntity(jsonBody);
        } catch (UnsupportedEncodingException e) {
            log.error("jsonbody转为StringEntity has error,url:{},param:{}.", url, jsonBody, e);
            throw e;
        }
        delete.setEntity(se);
        dealHeader(delete, header, true);
        try {
            return executeWrapperStringResponse(httpClient, delete);
        } catch (IOException ioException) {
            log.error("executeWrapperStringResponse has error,url:{},param:{}.", url, jsonBody, ioException);
            throw ioException;
        }
    }

    */
/**
     * 发送delete 的json请求，调用方法。如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param url      url链接
     * @param header   可为null的header
     * @param jsonBody 请求参数  JSON格式字符串
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendJsonDelete(String url, Map<String, String> header, String jsonBody) throws IOException {
        return sendJsonDelete(null, url, header, jsonBody);
    }

    */
/**
     * 发送delete 的json请求，调用方法。如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param url      url链接
     * @param jsonBody 请求参数  JSON格式字符串
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendJsonDelete(String url, String jsonBody) throws IOException {
        return sendJsonDelete(null, url, null, jsonBody);
    }

    */
/**
     * 发送delete请求
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @param header     可为null的header
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendDelete(CloseableHttpClient httpClient, String url, Map<String, String> header) throws IOException {
        HttpDelete delete = new HttpDelete(url);
        dealHeader(delete, header, false);
        try {
            return executeWrapperStringResponse(httpClient, delete);
        } catch (IOException ioException) {
            log.error("executeWrapperStringResponse has error,url:{} .", url, ioException);
            throw ioException;
        }
    }

    */
/**
     * 发送delete请求
     *
     * @param url    url链接
     * @param header 可为null的header
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendDelete(String url, Map<String, String> header) throws IOException {
        HttpDelete delete = new HttpDelete(url);
        dealHeader(delete, header, false);
        try {
            return executeWrapperStringResponse(null, delete);
        } catch (IOException ioException) {
            log.error("executeWrapperStringResponse has error,url:{} .", url, ioException);
            throw ioException;
        }
    }

    */
/**
     * 发送delete请求
     *
     * @param url url链接
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendDelete(String url) throws IOException {
        return sendDelete(null, url, null);
    }


    */
/**
     * 发送get请求
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @param header     可为null的header
     * @param params     请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendGet(CloseableHttpClient httpClient, String url, Map<String, String> header, Map<String, Object> params) throws IOException {
        HttpGet get = new HttpGet(formatUrlParams(url, params));
        dealHeader(get, header, false);
        try {
            return executeWrapperStringResponse(httpClient, get);
        } catch (IOException ioException) {
            log.error("executeWrapperStringResponse has error,url:{} ,param:{}.", url, mapParamToStringLog(params), ioException);
            throw ioException;
        }
    }

    */
/**
     * 发送get请求
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendGet(CloseableHttpClient httpClient, String url, Map<String, Object> params) throws IOException {
        return sendGet(httpClient, url, null, Collections.emptyMap());
    }

    */
/**
     * 发送get请求
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendGet(CloseableHttpClient httpClient, String url) throws IOException {
        return sendGet(httpClient, url, null, Collections.emptyMap());
    }

    */
/**
     * 发送get请求
     *
     * @param url    url链接
     * @param header 可为null的header
     * @param params 请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendGet(String url, Map<String, String> header, Map<String, Object> params) throws IOException {
        return sendGet(null, url, header, params);
    }

    */
/**
     * 发送get请求
     *
     * @param url    url链接
     * @param params 请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendGet(String url, Map<String, Object> params) throws IOException {
        return sendGet(null, url, null, params);
    }

    */
/**
     * 发送get请求
     *
     * @param url url链接
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendGet(String url) throws IOException {
        return sendGet(null, url, null, Collections.emptyMap());
    }

    */
/**
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @param header     可为null的header
     * @param body       请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendFormPut(CloseableHttpClient httpClient, String url, Map<String, String> header, Map<String, Object> body) throws IOException {
        HttpPut httpput = new HttpPut(url);
        try {
            httpput.setEntity(convertToUrlEncodedFormEntity(body));
        } catch (UnsupportedEncodingException e) {
            log.error("convertToUrlEncodedFormEntity has error,url:{},param:{}.", url, mapParamToStringLog(body), e);
            throw e;
        }
        dealHeader(httpput, header, false);
        try {
            return executeWrapperStringResponse(httpClient, httpput);
        } catch (IOException ioException) {
            log.error("executeWrapperStringResponse has error,url:{},param:{}.", url, mapParamToStringLog(body), ioException);
            throw ioException;
        }
    }

    */
/**
     * 发送Form格式的Put请求
     *
     * @param url    url链接
     * @param header 可为null的header
     * @param body   请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendFormPut(String url, Map<String, String> header, Map<String, Object> body) throws IOException {
        return sendFormPut(null, url, header, body);
    }

    */
/**
     * 发送Form格式的Put请求
     *
     * @param url  url链接
     * @param body 请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendFormPut(String url, Map<String, Object> body) throws IOException {
        return sendFormPut(null, url, null, body);
    }


    */
/**
     * 根据用户自定义的httpclient，调用json put方法。。如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param url        url链接
     * @param header     可为null的header
     * @param body       请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendJsonPut(CloseableHttpClient httpClient, String url, Map<String, String> header, String body) throws IOException {
        HttpPut httpput = new HttpPut(url);
        StringEntity stringEntity = new StringEntity(body, "UTF-8");
        httpput.setEntity(stringEntity);
        dealHeader(httpput, header, true);
        try {
            return executeWrapperStringResponse(httpClient, httpput);
        } catch (IOException ioException) {
            log.error("executeWrapperStringResponse has error,url:{} ,param:{}.", url, body, ioException);
            throw ioException;
        }

    }

    */
/**
     * 调用json put方法。如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param url    url链接
     * @param header 可为null的header
     * @param body   请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendJsonPut(String url, Map<String, String> header, String body) throws IOException {
        return sendJsonPut(null, url, header, body);
    }

    */
/**
     * 调用json put方法。如果未设置HTTP.CONTENT_TYPE,则设置为"application/json"。
     * 如果消息体是application/xml，只需要设置header即可。
     *
     * @param url  url链接
     * @param body 请求参数
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse sendJsonPut(String url, String body) throws IOException {
        return sendJsonPut(null, url, null, body);
    }

    */
/**
     * 执行 http请求，并返回HttpResponseBody
     *
     * @param request 可以传入HttpGet、HttpPut等HttpRequestBase的实现类
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse executeWrapperStringResponse(HttpRequestBase request) throws IOException {
        return executeWrapperStringResponse(null, request);
    }

    */
/**
     * 执行 http请求，并返回HttpResponseBody
     *
     * @param httpClient 自定义的httpClient， @see com.sensetime.cobra.utils.http.HttpUtils#createHttpClient(int, int , int , int )
     * @param request    可以传入HttpGet、HttpPut等HttpRequestBase的实现类
     * @return {@link HttpStringResponse}
     * @throws IOException
     *//*

    public static HttpStringResponse executeWrapperStringResponse(CloseableHttpClient httpClient, HttpRequestBase request) throws IOException {
        CloseableHttpResponse response = null;
        if (httpClient == null) {
            httpClient = getDefaultPoolHttpClient();
        }
        try {
            response = httpClient.execute(request);
            if (isRedirect(response, request)) {
                request.setURI(URI.create(response.getFirstHeader("location").getValue()));
                return executeWrapperStringResponse(httpClient, request);
            } else {
                HttpEntity entity = response.getEntity();
                return new HttpStringResponse(entity.getContentType() == null ? "" : entity.getContentType().getValue(), response.getStatusLine().getReasonPhrase(), response.getStatusLine().getStatusCode(), EntityUtils.toString(entity, "utf-8"));
            }
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("executeWrapperStringResponse方法关闭http流出错（影响不大）,url:{}.", request.getURI().toString(), e);
            }
        }
    }

    */
/**
     * 发送http请求，并把返回值包装成HttpByteResponse对象。
     *
     * @param request {@link org.apache.http.client.methods.HttpRequestBase}
     * @return com.sensetime.cobra.utils.http.HttpByteResponse。具体查看：{@link HttpByteResponse}
     * @throws IOException
     *//*

    public static HttpByteResponse executeWrapperByteResponse(HttpRequestBase request) throws IOException {
        return executeWrapperByteResponse(null, request);
    }

    */
/**
     * 发送http请求，并把返回值包装成HttpByteResponse对象。
     *
     * @param httpClient 自定义的httpClient  {@link org.apache.http.impl.client.CloseableHttpClient}
     * @param request    delete、get、post等请求  {@link org.apache.http.client.methods.HttpRequestBase}
     * @return com.sensetime.cobra.utils.http.HttpByteResponse。具体查看：{@link HttpByteResponse}
     * @throws IOException
     *//*

    public static HttpByteResponse executeWrapperByteResponse(CloseableHttpClient httpClient, HttpRequestBase request) throws IOException {
        CloseableHttpResponse response = null;
        if (httpClient == null) {
            httpClient = getDefaultPoolHttpClient();
        }
        try {
            response = httpClient.execute(request);
            if (isRedirect(response, request)) {
                request.setURI(URI.create(response.getFirstHeader("location").getValue()));
                return executeWrapperByteResponse(httpClient, request);
            } else {
                HttpEntity entity = response.getEntity();
                return new HttpByteResponse(entity.getContentType() == null ? "" : entity.getContentType().getValue(), response.getStatusLine().getReasonPhrase(), response.getStatusLine().getStatusCode(), EntityUtils.toByteArray(entity));
            }
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("executeWrapperByteResponse方法关闭http流出错（影响不大）,url:{}.", request.getURI().toString(), e);
            }
        }
    }

    */
/**
     * 把参数封装在URL中，以？abc=xx&bd=xx的格式封装
     *
     * @param url   url地址
     * @param param 参数map
     * @return String
     *//*

    public static String formatUrlParams(String url, Map<String, Object> param) {
        StringBuilder sb = new StringBuilder(url);
        if (param != null && param.size() > 0) {
            sb.append("?");
            int count = 0;
            for (String key : param.keySet()) {
                count++;
                if (count == param.size()) {
                    sb.append(key).append("=").append(param.get(key));
                } else {
                    sb.append(key).append("=").append(param.get(key)).append("&");
                }
            }
        }
        return sb.toString();
    }

    */
/**
     * 为http请求处理header,把header内容放到request中
     *
     * @param request       HttpUriRequest
     * @param header        可为null的header
     * @param addJsonHeader 若为true，则添加json的header
     *//*

    private static void dealHeader(HttpUriRequest request, Map<String, String> header, boolean addJsonHeader) {
        if (header == null) {
            header = new HashMap<>();
        }
        if (addJsonHeader) {
            header.putIfAbsent(HTTP.CONTENT_TYPE, "application/json");
        }
        header.forEach(request::addHeader);
    }

    */
/**
     * 根据传入的Map转换为Form格式的Entity请求体
     *
     * @param params 请求参数
     * @return UrlEncodedFormEntity
     * @throws UnsupportedEncodingException
     *//*

    private static UrlEncodedFormEntity convertToUrlEncodedFormEntity(Map<String, Object> params) throws UnsupportedEncodingException {
        if (params == null || params.isEmpty()) {
            return new UrlEncodedFormEntity(Collections.EMPTY_LIST);
        }
        List<NameValuePair> pairs = new ArrayList<>();
        for (String key : params.keySet()) {
            pairs.add(new BasicNameValuePair(key, params.get(key) == null ? null : params.get(key).toString()));
        }
        return new UrlEncodedFormEntity(pairs);
    }

    */
/**
     * 判断服务端是否返回3XX重定向
     *
     * @param response {@link CloseableHttpResponse}
     * @param request  {@link HttpRequestBase}
     * @return boolean 具体查看：{@link boolean}
     *//*

    private static boolean isRedirect(CloseableHttpResponse response, HttpRequestBase request) {
        if (response.getStatusLine().getStatusCode() < 300 || response.getStatusLine().getStatusCode() > 399) {
            return false;
        }
        if (response.getFirstHeader("location") != null) {
            log.info("服务端[{}]返回{}重定向，程序继续请求。redirect url:{}", request.getURI().toString(), response.getStatusLine().getStatusCode(), response.getFirstHeader("location").getValue());
            return true;
        } else {
            log.error("服务端[{}]返回{}重定向，但从header中获取location为空。header:{}", request.getURI().toString(), response.getStatusLine().getStatusCode(), GsonUtils.toJsonString(response.getAllHeaders()));
        }
        return false;
    }


    */
/**
     * 把map转为可输出的日志字符串
     *
     * @param params 请求参数
     * @return String
     *//*


    private static String mapParamToStringLog(Map<String, Object> params) {
        if (params != null) {//以null:null,ab:null格式打印日志
            return params.entrySet().stream().map(entry -> entry == null ? "null" : entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining(",", "{", "}"));
        }
        return "param map is null";
    }


}
*/
