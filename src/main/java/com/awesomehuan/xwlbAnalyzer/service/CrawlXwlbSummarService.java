package com.awesomehuan.xwlbAnalyzer.service;

import com.awesomehuan.xwlbAnalyzer.dao.XwlbSummaryDao;
import com.awesomehuan.xwlbAnalyzer.domain.XwlbSummary;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Created by huanyu on 16/7/17.
 */
@Service
public class CrawlXwlbSummarService {
    @Autowired
    private HtmlParserService htmlParserService;
    @Autowired
    private XwlbSummaryDao xwlbSummaryDao;

    private final String initUrl = "http://www.xwlb.com.cn/summary";

    public void saveLatestXwlbSummary(int pageOffset, int pageSize){
        if (pageOffset<0 || pageSize <0){
            return;
        }
        Set<XwlbSummary> xwlbSummaries = Sets.newHashSet();
        for (int start = pageOffset; start < pageOffset + pageSize; start++){
            String initUrl = "http://www.xwlb.com.cn/summary" + (start == 0?"":"_"+start) +".html";
            Set<String> urls = htmlParserService.extractSummaryLinks(Sets.newHashSet(initUrl));
            xwlbSummaries.addAll(htmlParserService.extractSummaryContent(urls));
        }
        if (CollectionUtils.isEmpty(xwlbSummaries)){
            return;
        }
        for (XwlbSummary xwlbSummary : xwlbSummaries){
            xwlbSummaryDao.saveXwlbSummary(xwlbSummary);
        }

    }
}
