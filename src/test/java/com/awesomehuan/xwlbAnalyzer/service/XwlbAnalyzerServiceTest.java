package com.awesomehuan.xwlbAnalyzer.service;

import com.awesomehuan.xwlbAnalyzer.base.AbstractServiceTest;
import com.awesomehuan.xwlbAnalyzer.task.XwlbAnalyzeTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huanyu on 16/7/17.
 */
public class XwlbAnalyzerServiceTest extends AbstractServiceTest {
    @Autowired
    private CrawlXwlbSummarService crawlXwlbSummarService;
    @Autowired
    private XwlbAnalyzeTask xwlbAnalyzeTask;
   @Test
    public void testSaveLatestXwlbSummary() throws Exception{
       crawlXwlbSummarService.saveLatestXwlbSummary(0,15);
   }

    @Test
    public void testCrawlAndAnalyze() throws Exception {
        xwlbAnalyzeTask.crawlAndAnalyze();
    }
}
