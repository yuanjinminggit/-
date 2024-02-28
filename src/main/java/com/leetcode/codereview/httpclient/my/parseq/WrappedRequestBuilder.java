package com.leetcode.codereview.httpclient.my.parseq;

import com.linkedin.parseq.Task;
import com.linkedin.parseq.promise.Promises;
import com.linkedin.parseq.promise.SettablePromise;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.BodyGenerator;
import com.ning.http.client.ConnectionPoolPartitioning;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Param;
import com.ning.http.client.ProxyServer;
import com.ning.http.client.Realm;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import com.ning.http.client.SignatureCalculator;
import com.ning.http.client.cookie.Cookie;
import com.ning.http.client.multipart.Part;
import com.ning.http.client.uri.Uri;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class WrappedRequestBuilder {

    private final BoundRequestBuilder delegate;
    private final String method;

    public WrappedRequestBuilder(BoundRequestBuilder delegate, String method) {
        this.delegate = delegate;
        this.method = method;
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    public WrappedRequestBuilder setUri(Uri uri) {
        delegate.setUri(uri);
        return this;
    }

    public WrappedRequestBuilder addBodyPart(Part part) {
        delegate.addBodyPart(part);
        return this;
    }

    public WrappedRequestBuilder setInetAddress(InetAddress address) {
        delegate.setInetAddress(address);
        return this;
    }

    public WrappedRequestBuilder setLocalInetAddress(InetAddress address) {
        delegate.setLocalInetAddress(address);
        return this;
    }

    public WrappedRequestBuilder addCookie(Cookie cookie) {
        delegate.addCookie(cookie);
        return this;
    }

    public WrappedRequestBuilder addHeader(String name, String value) {
        delegate.addHeader(name, value);
        return this;
    }

    public WrappedRequestBuilder addFormParam(String key, String value) {
        delegate.addFormParam(key, value);
        return this;
    }

    public WrappedRequestBuilder addQueryParam(String name, String value) {
        delegate.addQueryParam(name, value);
        return this;
    }

    public Request build() {
        return delegate.build();
    }

    public WrappedRequestBuilder setBody(byte[] data) {
        delegate.setBody(data);
        return this;
    }

    public WrappedRequestBuilder setBody(InputStream stream) {
        delegate.setBody(stream);
        return this;
    }

    public WrappedRequestBuilder setContentLength(int length) {
        delegate.setContentLength(length);
        return this;
    }

    public WrappedRequestBuilder setBody(String data) {
        delegate.setBody(data);
        return this;
    }

    public WrappedRequestBuilder setHeader(String name, String value) {
        delegate.setHeader(name, value);
        return this;
    }

    public WrappedRequestBuilder setCookies(Collection<Cookie> cookies) {
        delegate.setCookies(cookies);
        return this;
    }

    public WrappedRequestBuilder setHeaders(FluentCaseInsensitiveStringsMap headers) {
        delegate.setHeaders(headers);
        return this;
    }

    public WrappedRequestBuilder setHeaders(Map<String, Collection<String>> headers) {
        delegate.setHeaders(headers);
        return this;
    }

    public WrappedRequestBuilder addOrReplaceCookie(Cookie cookie) {
        delegate.addOrReplaceCookie(cookie);
        return this;
    }

    public WrappedRequestBuilder setFormParams(Map<String, List<String>> params) {
        delegate.setFormParams(params);
        return this;
    }

    public WrappedRequestBuilder setFormParams(List<Param> params) {
        delegate.setFormParams(params);
        return this;
    }

    public WrappedRequestBuilder setUrl(String url) {
        delegate.setUrl(url);
        return this;
    }

    public WrappedRequestBuilder setVirtualHost(String virtualHost) {
        delegate.setVirtualHost(virtualHost);
        return this;
    }

    public void resetCookies() {
        delegate.resetCookies();
    }

    public void resetQuery() {
        delegate.resetQuery();
    }

    public WrappedRequestBuilder setSignatureCalculator(SignatureCalculator signatureCalculator) {
        delegate.setSignatureCalculator(signatureCalculator);
        return this;
    }

    public void resetFormParams() {
        delegate.resetFormParams();
    }

    public void resetNonMultipartData() {
        delegate.resetNonMultipartData();
    }

    public void resetMultipartData() {
        delegate.resetMultipartData();
    }

    public WrappedRequestBuilder setBody(File file) {
        delegate.setBody(file);
        return this;
    }

    public WrappedRequestBuilder setBody(List<byte[]> data) {
        delegate.setBody(data);
        return this;
    }

    public WrappedRequestBuilder setBody(BodyGenerator bodyGenerator) {
        delegate.setBody(bodyGenerator);
        return this;
    }

    public WrappedRequestBuilder addQueryParams(List<Param> params) {
        delegate.addQueryParams(params);
        return this;
    }

    public WrappedRequestBuilder setQueryParams(Map<String, List<String>> map) {
        delegate.setQueryParams(map);
        return this;
    }

    public WrappedRequestBuilder setQueryParams(List<Param> params) {
        delegate.setQueryParams(params);
        return this;
    }

    public WrappedRequestBuilder setProxyServer(ProxyServer proxyServer) {
        delegate.setProxyServer(proxyServer);
        return this;
    }

    public WrappedRequestBuilder setRealm(Realm realm) {
        delegate.setRealm(realm);
        return this;
    }

    public WrappedRequestBuilder setFollowRedirects(boolean followRedirects) {
        delegate.setFollowRedirects(followRedirects);
        return this;
    }

    public WrappedRequestBuilder setRequestTimeout(int requestTimeout) {
        delegate.setRequestTimeout(requestTimeout);
        return this;
    }

    public WrappedRequestBuilder setRangeOffset(long rangeOffset) {
        delegate.setRangeOffset(rangeOffset);
        return this;
    }

    public WrappedRequestBuilder setMethod(String method) {
        delegate.setMethod(method);
        return this;
    }

    public WrappedRequestBuilder setBodyEncoding(String charset) {
        delegate.setBodyEncoding(charset);
        return this;
    }

    public WrappedRequestBuilder setConnectionPoolKeyStrategy(ConnectionPoolPartitioning connectionPoolKeyStrategy) {
        delegate.setConnectionPoolKeyStrategy(connectionPoolKeyStrategy);
        return this;
    }

    public Task<Response> task(final String desc) {
        return Task.async(desc, () -> {
            final SettablePromise<Response> result = Promises.settable();
            delegate.execute(new AsyncCompletionHandler<Response>() {
                @Override
                public Response onCompleted(final Response response) throws Exception {
                    result.done(response);
                    return response;
                }

                @Override
                public void onThrowable(Throwable t) {
                    result.fail(t);
                }
            });
            return result;
        });
    }

    public Task<Response> task() {
        return task(method);
    }

}
