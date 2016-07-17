package com.awesomehuan.xwlbAnalyzer.service;

import com.awesomehuan.xwlbAnalyzer.base.AbstractServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huanyu on 16/7/17.
 */
public class XwlbAnalyzerServiceTest extends AbstractServiceTest {
    @Autowired
    private CrawlXwlbSummarService crawlXwlbSummarService;
   @Test
    public void testSaveLatestXwlbSummary() throws Exception{
       crawlXwlbSummarService.saveLatestXwlbSummary(0,15);
   }
}
