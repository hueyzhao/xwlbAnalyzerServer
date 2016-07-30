package com.awesomehuan.xwlbAnalyzer.task;

import com.awesomehuan.xwlbAnalyzer.service.CrawlXwlbSummarService;
import com.awesomehuan.xwlbAnalyzer.service.analyzer.WordSegmentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huanyu on 16/7/17.
 */
@Service
public class XwlbAnalyzeTask {
    @Autowired
    private CrawlXwlbSummarService crawlXwlbSummarService;
    @Autowired
    private WordSegmentService wordSegmentService;

    public void crawlAndAnalyze(){
        crawlXwlbSummarService.saveLatestXwlbSummary(0,1);
        DateTime todayDateTime = new DateTime();
        DateTime lastYear = todayDateTime.minusDays(365);
        long startTimeStamp = lastYear.getMillis() /1000;
        long endTimeStamp = todayDateTime.getMillis() / 1000;
        wordSegmentService.calculateOneDayWordCount(startTimeStamp,endTimeStamp);
    }
}
