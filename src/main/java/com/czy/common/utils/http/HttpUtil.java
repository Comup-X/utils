package com.czy.common.utils.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Comup on 2017-05-19.
 * 发送Http请求
 */
public final class HttpUtil {

    private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();

    private HttpUtil() {
    }

    public static String doPost(String url, Map<String, String> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<>();
        params.forEach((key, value) -> pairs.add(new BasicNameValuePair(key, value)));
        httpPost.setEntity(new UrlEncodedFormEntity(pairs));
        CloseableHttpResponse response = HTTP_CLIENT.execute(httpPost);
        if (response == null) {
            throw new HttpResponseException(500, "httpclient execute but the response is null");
        }
        return getResponse(response);
    }

    public static String doGet(String url, Map<String, String> params) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("url may not be null");
        }
        if (params != null && params.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder(url);
            stringBuilder.append("?");
            params.forEach((key, value) -> stringBuilder.append(key).append("=").append(value).append("&"));
            url = stringBuilder.toString();
            url = url.substring(0, url.length() - 1);
        }

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = HTTP_CLIENT.execute(httpGet);
        if (response == null) {
            throw new HttpResponseException(500, "httpclient execute but the response is null");
        }
        return getResponse(response);
    }

    private static String getResponse(CloseableHttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }
}
