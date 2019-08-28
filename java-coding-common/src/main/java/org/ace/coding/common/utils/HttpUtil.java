package org.ace.coding.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 发送http请求
 * Created by LiangShujie
 * Date: 2019/8/5 19:49
 */
public class HttpUtil implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    private static final int MAX_CONN_PER_ROUTE = 1000;
    private static final int CONN_TIME_OUT=5000;
    private static final int SOCK_TIME_OUT=15000;
    private CloseableHttpClient httpClient ;
    private RequestConfig requestConfig;

    private Map<String, String> headers = new HashMap<>();

    public HttpUtil() {
        httpClient = HttpClients.custom().setMaxConnPerRoute(MAX_CONN_PER_ROUTE).setMaxConnTotal(2 * MAX_CONN_PER_ROUTE).build();
        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONN_TIME_OUT).setConnectTimeout(CONN_TIME_OUT).setSocketTimeout(SOCK_TIME_OUT).build();

    }

    /**
     * 设置代理
     * @param host
     * @param port
     */
    public void setProxy(String host, int port){
        HttpHost proxy = new HttpHost(host, port);
        requestConfig = RequestConfig.custom().setProxy(proxy).setConnectionRequestTimeout(CONN_TIME_OUT).setConnectTimeout(CONN_TIME_OUT).setSocketTimeout(SOCK_TIME_OUT).build();;
    }

    /**
     * 增加头信息
     * @param key
     * @param value
     */
    public void addHeader(String key, String value){
        headers.put(key, value);
    }

    /**
     * 发送post请求
     * @param url 目标url
     * @param data 数据
     * @param retryCount 发送异常时重试次数
     * @return response
     * @throws IOException 重试后还是不成功，抛出异常
     */
    public String postAndRetry(String url, String data, int retryCount) throws IOException{
        try {
            return post(url, data);
        } catch (IOException e) {
            log.error("请求异常，url={},retryCount={}", url, retryCount);
            if(retryCount >= 1){
                try {
                    TimeUnit.MICROSECONDS.sleep(2000);
                } catch (InterruptedException e1) {
                }
                log.error("重试，url={},retryCount={}", url, retryCount);
                postAndRetry(url, data, --retryCount);
            } else {
                throw e;
            }
        }
        return null;
    }

    public String post(String url, String data) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        for(Map.Entry<String, String> kv :headers.entrySet()){
            httpPost.setHeader(kv.getKey(), kv.getValue());
        }
        if(data != null) {
            httpPost.setEntity(new StringEntity(data, "UTF-8"));
        }

        CloseableHttpResponse response = httpClient.execute(httpPost);
        int status = response.getStatusLine().getStatusCode();
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            if(status != 200){
                throw new RuntimeException("请求失败,url=" + url + ",response:" + EntityUtils.toString(resEntity, "UTF-8"));
            }
            return EntityUtils.toString(resEntity, "UTF-8");
        } else {
            throw new RuntimeException("请求无返回");
        }
    }

    /**
     * 发送get请求
     * @param url 目标url
     * @param retryCount 发送异常时重试次数
     * @return response
     * @throws IOException 重试后还是不成功，抛出异常
     */
    public String getAndRetry(String url, int retryCount) throws IOException{
        try {
            return get(url);
        } catch (IOException e) {
            log.error("请求异常，url={},retryCount={}", url, retryCount);
            if(retryCount >= 1){
                try {
                    TimeUnit.MICROSECONDS.sleep(2000);
                } catch (InterruptedException e1) {
                }
                log.error("重试，url={},retryCount={}", url, retryCount);
                getAndRetry(url, --retryCount);
            } else {
                throw e;
            }
        }
        return null;
    }

    public String get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        for(Map.Entry<String, String> kv :headers.entrySet()){
            httpGet.setHeader(kv.getKey(), kv.getValue());
        }

        CloseableHttpResponse response = httpClient.execute(httpGet);
        int status = response.getStatusLine().getStatusCode();
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            if(status != 200){
                throw new RuntimeException("请求失败,url=" + url + ",response:" + EntityUtils.toString(resEntity, "UTF-8"));
            }
            return EntityUtils.toString(resEntity, "UTF-8");
        } else {
            throw new RuntimeException("请求无返回");
        }
    }

    public static void main(String[] args) throws IOException {
        // get请求
       HttpUtil httpUtil = new HttpUtil();
       System.out.println(httpUtil.getAndRetry("https://httpbin.org/get", 3));

       // 使用代理和增加header
       httpUtil.setProxy("10.204.49.66", 88);
       httpUtil.addHeader("User-Agent", "userAgent");
       System.out.println(httpUtil.getAndRetry("https://httpbin.org/get", 3));

       // post请求
        System.out.println(httpUtil.postAndRetry("https://httpbin.org/post", "{\"a\":\"b\"}",3));
    }
}
