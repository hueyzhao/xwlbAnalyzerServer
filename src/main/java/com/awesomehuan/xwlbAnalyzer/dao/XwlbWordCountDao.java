package com.awesomehuan.xwlbAnalyzer.dao;

import com.awesomehuan.xwlbAnalyzer.domain.XwlbWordCount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

/**
 * Created by huanyu on 16/7/23.
 */
@Component
public interface XwlbWordCountDao {
    public static final String TABLE_NAME = "xwlb_wordcount";
    public static final String INSERT_KEYS = "keyword, count, publish_time, ctime, utime";
    public static final String INSERT_VALUES = "#{keyword}, #{count}, #{publish_time}, unix_timestamp(), unix_timestamp()";
    public static final String SELECT_KEYS = "id, keyword, count, publish_time, ctime, utime";

    @Insert("insert into " + TABLE_NAME + "(" + INSERT_KEYS + ") values (" + INSERT_VALUES + ")")
    @Options(useGeneratedKeys = true)
    public int saveXwlbWordCount(XwlbWordCount xwlbWordCount);
}
