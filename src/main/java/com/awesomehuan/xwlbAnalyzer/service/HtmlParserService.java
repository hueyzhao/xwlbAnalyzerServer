package com.awesomehuan.xwlbAnalyzer.service;

import com.awesomehuan.xwlbAnalyzer.domain.XwlbSummary;
import com.awesomehuan.xwlbAnalyzer.util.CommonThreadPool;
import com.awesomehuan.xwlbAnalyzer.util.IThreadWorker;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.io.IOException;
import java.util.*;

import org.jsoup.nodes.Attributes;
import org.springframework.util.StringUtils;

/**
 * Created by huanyu on 16/7/3.
 */
@Service
public class HtmlParserService {
    @Autowired
    private CommonThreadPool<Set<XwlbSummary>> commonThreadPool;
    private static final int TEN_SECONDS = 10 * 1000;
    public Set<String> extractSummaryLinks(Set<String> urls){
        Set<String> extractedUrls = Sets.newHashSet();
        if (CollectionUtils.isEmpty(urls)){
            return extractedUrls;
        }
        for (String url : urls){
            try {
                Document doc = Jsoup.connect(url).timeout(TEN_SECONDS).get();
                Elements eles = doc.select("div.post.cate3 h2>a[href]");
                for (Element element : eles){
                    Attributes attributes = element.attributes();
                    String link = attributes.get("href").toString();
                    extractedUrls.add(link);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(extractedUrls);
        return extractedUrls;
    }

    public Set<XwlbSummary> extractSummaryContent(Set<String> urls){
        final Set<XwlbSummary> xwlbSummaries = Sets.newHashSet();
        if (CollectionUtils.isEmpty(urls)){
            return xwlbSummaries;
        }
        for (final String url : urls){
            commonThreadPool.addWork(new IThreadWorker() {

                @Override
                public Object work() {
                    Set<XwlbSummary> xwlbSummarieSet = Sets.newHashSet();
                    try {
                        Document doc = Jsoup.connect(url).timeout(TEN_SECONDS).get();
                        long timeStamp = extractContentTimeStamp(doc);
                        xwlbSummarieSet = extractPostContent(doc);
                        for (XwlbSummary xwlbSummary : xwlbSummarieSet) {
                            xwlbSummary.setPublish_time(timeStamp);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return xwlbSummarieSet;
                }
            });
        }
        List<Set<XwlbSummary>> results = commonThreadPool.constructCallBacks();
        if (CollectionUtils.isEmpty(results)){
            return xwlbSummaries;
        }
        for (Set<XwlbSummary> xwlbSummarySet : results){
            xwlbSummaries.addAll(xwlbSummarySet);
        }
        System.out.println(xwlbSummaries);
        return xwlbSummaries;
    }

    private Set<XwlbSummary> extractPostContent(Document document){
        Elements titleEles = document.select("div.post_content strong > a");
        Set<XwlbSummary> xwlbSummaries = Sets.newHashSet();
        for (Element titleEle : titleEles){
          String url = titleEle.attr("href");
            try {
                Document doc = Jsoup.connect(url).timeout(15*1000).get();
                if (doc == null){
                    System.out.println("#######url:" + url + "#####");
                    continue;
                }
                if (CollectionUtils.isEmpty(doc.select("div.post_body > h2"))){
                    System.out.println("#######url:" + url + "#####" + doc.select("div.post_body > h2"));
                    continue;
                }
                String title = doc.select("div.post_body > h2").first().text();
                Elements contentEles = doc.select("div.post_content p");
                XwlbSummary xwlbSummary = new XwlbSummary();
                StringBuilder contentBuffer = new StringBuilder();
                for (Element contentEle : contentEles){
                    contentBuffer.append(contentEle.text());
                }
                if (StringUtils.isEmpty(contentBuffer)){
                    continue;
                }
                xwlbSummary.setTitle(title);
                xwlbSummary.setContent(contentBuffer.toString());
                xwlbSummary.setUrl(url);
                xwlbSummaries.add(xwlbSummary);

            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                System.out.println("time out url is : " + url);
            }
        }
        return xwlbSummaries;
    }



    private long extractContentTimeStamp(Document doc) {
        Set<XwlbSummary> xwlbSummaries;
        Element timeElement = doc.select("div.post.cate3.auth1 > div").first();
        if (timeElement == null){
            return 0;
        }
        List<Node> timeChildNodes = timeElement.childNodes();
        List<Integer> dateValues = Lists.newArrayList();
        for (Node timeNode : timeChildNodes){
            if (CollectionUtils.isEmpty(timeNode.childNodes())){
                continue;
            }
            for (Node childNode : timeNode.childNodes()){
                if (Ints.tryParse(childNode.toString()) == null){
                    continue;
                }
                dateValues.add(Ints.tryParse(childNode.toString()));
            }
        }
        if (dateValues.size()!=3){
            return 0;
        }
        int day = dateValues.get(0);
        int year = dateValues.get(1);
        int month = dateValues.get(2);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        Date dateOfContent = calendar.getTime();
        return dateOfContent.getTime() / 1000;
    }


    public static void main(String [] args){
        HtmlParserService parserService = new HtmlParserService();
        for (int page = 3; page <= 13; page ++){
            String initUrl = "http://www.xwlb.com.cn/summary" + (page == 0?"":"_"+page) +".html";
            Set<String> urls = parserService.extractSummaryLinks(Sets.newHashSet(initUrl));
            parserService.extractSummaryContent(urls);
        }
//        parserService.extractSummaryLinks(Sets.newHashSet("http://www.xwlb.com.cn/summary.html"));
//        parserService.extractSummaryContent(Sets.newHashSet("http://www.xwlb.com.cn/7108.html"));
    }
}
