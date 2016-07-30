package com.awesomehuan.xwlbAnalyzer.service.analyzer;

import com.awesomehuan.xwlbAnalyzer.dao.XwlbSummaryDao;
import com.awesomehuan.xwlbAnalyzer.dao.XwlbWordCountDao;
import com.awesomehuan.xwlbAnalyzer.domain.XwlbSummary;
import com.awesomehuan.xwlbAnalyzer.domain.XwlbWordCount;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/**
 * Created by huanyu on 16/7/21.
 */
@Service
public class WordSegmentService {
    @Autowired
    private XwlbSummaryDao xwlbSummaryDao;
    @Autowired
    private XwlbWordCountDao xwlbWordCountDao;

    public static void main(String [] args) throws Exception{
//        Map<String,Integer> workCountMap = calculateWordCount("央视网消息（新闻联播文字版）：全国党建研究会第六次会员代表大会23日在京召开。中共中央总书记、国家主席、中央军委主席习近平作出重要指示，向大会召开表示热烈祝贺，向全国广大党建研究工作者致以诚挚问候，希望全国党建研究会坚持正确政治方向，发挥党建高端智库作用，发扬成绩，发挥优势，围绕协调推进“五位一体”总体布局和“四个全面”战略布局，深入研究党建理论和实际问题，深入总结全面从严治党实践经验，为构建中国化的马克思主义党建理论体系，为加强和改善党的领导、确保党始终成为中国特色社会主义事业的坚强领导核心作出新的更大的贡献。");
//        List<Map.Entry<String, Integer>> wordFrenList = new ArrayList<Map.Entry<String, Integer>>(workCountMap.entrySet());
//        Collections.sort(wordFrenList, new Comparator<Map.Entry<String, Integer>>() {
//            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
//                return obj2.getValue() - obj1.getValue();
//            }
//        });
//        System.out.println(wordFrenList);
    }

    public  Map<String, Integer> calculateWordCount(String text) throws IOException {
        Map<String, Integer> wordsFren=new HashMap<String, Integer>();
        IKSegmentation ikSegmenter = new IKSegmentation(new StringReader(text), true);
        Lexeme lexeme;
        while ((lexeme = ikSegmenter.next()) != null) {
            if(lexeme.getLexemeText().length()>1){
                if(wordsFren.containsKey(lexeme.getLexemeText())){
                    wordsFren.put(lexeme.getLexemeText(),wordsFren.get(lexeme.getLexemeText())+1);
                }else {
                    wordsFren.put(lexeme.getLexemeText(),1);
                }
            }
        }
        return wordsFren;
    }

    public void calculateOneDayWordCount(long beginTime, long endTime){
        if (beginTime <= 0 || endTime <= 0){
            return;
        }
        List<XwlbSummary> xwlbSummaries = xwlbSummaryDao.getSummaryByPublishTime(beginTime,endTime);
        if (CollectionUtils.isEmpty(xwlbSummaries)){
            return;
        }

        Map<Long, String> publishTime2Content = Maps.newHashMap();
        for (XwlbSummary xwlbSummary : xwlbSummaries){
            if (publishTime2Content.containsKey(xwlbSummary.getPublish_time())){
                String content = publishTime2Content.get(xwlbSummary.getPublish_time());
                StringBuilder contentBuffer = new StringBuilder(content);
                contentBuffer.append(xwlbSummary.getTitle()).append(xwlbSummary.getContent());
                publishTime2Content.put(xwlbSummary.getPublish_time(),contentBuffer.toString());
            }else {
                publishTime2Content.put(xwlbSummary.getPublish_time(),xwlbSummary.getTitle() + xwlbSummary.getContent());
            }
        }
        for (Map.Entry<Long,String> entry : publishTime2Content.entrySet()){
            Map<String, Integer> keyWordCountMap = Maps.newHashMap();
            try {
                keyWordCountMap = calculateWordCount(entry.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Map.Entry<String,Integer> keyWordCountEntry: keyWordCountMap.entrySet()){
                XwlbWordCount wordCount = new XwlbWordCount();
                wordCount.setKeyword(keyWordCountEntry.getKey());
                wordCount.setCount(keyWordCountEntry.getValue());
                wordCount.setPublish_time(entry.getKey());
                xwlbWordCountDao.saveXwlbWordCount(wordCount);
            }
        }


    }


}
