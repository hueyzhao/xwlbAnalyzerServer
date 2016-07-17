package com.awesomehuan.xwlbAnalyzer.helper;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by huanyu on 16/6/26.
 */
@Service
public class CrawlerHelper {
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36";
    private static final String ACCEPT_LANG = "zh-CN,zh;q=0.8";

    public String fetchDataFromUrl(String url){
        if (StringUtils.isEmpty(url)){
            return null;
        }
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("Accept-Language",ACCEPT_LANG);
        HttpResponse response = null;
        StringBuffer result = new StringBuffer();
        try {
            response = httpClient.execute(request);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String [] args){
        CrawlerHelper helper = new CrawlerHelper();
        String result = helper.fetchDataFromUrl("http://www.xwlb.com.cn/summary.html");
        System.out.print(result);
    }
}
