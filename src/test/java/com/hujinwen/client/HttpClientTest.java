package com.hujinwen.client;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HttpClientTest {

    @Test
    void test1() {
        final HttpClient httpClient = HttpClient.createDefault();

//        httpClient.doGetAsStream("https://9d772c20d782e4e5bcfa2711c7fb7553.dlied1.cdntips.com/dlied1.qq.com/qqweb/PCQQ/PCQQ_EXE/PCQQ2020.exe?mkey=5e9d6eefb4a68727&f=9943&cip=180.166.161.210&proto=https");
        httpClient.doGetAsStream("https://new.qq.com/omn/20200420/20200420A020PH00.html");
        final long contentLength = httpClient.getContentLength();


    }

    /**
     * Post 测试
     */
    @Test
    void test2() throws IOException {
        final HttpPost httpPost = new HttpPost("https://www.baidu.com");

        final List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("", ""));

        final UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps);

        httpPost.setEntity(urlEncodedFormEntity);

        final CloseableHttpClient httpClient = HttpClients.createDefault();
        httpClient.execute(httpPost);


    }


}