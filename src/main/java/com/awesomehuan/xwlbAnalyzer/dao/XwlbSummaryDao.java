package com.awesomehuan.xwlbAnalyzer.dao;

import com.awesomehuan.xwlbAnalyzer.domain.XwlbSummary;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

/**
 * Created by huanyu on 16/7/17.
 */
@Component
public interface XwlbSummaryDao {
    public static final String TABLE_NAME = "xwlb_summary";
    public static final String SELECT_KEYS = "id, url, title, content, ctime, utime";
    public static final String INSERT_KEYS = "url, title, content, ctime, utime";
    public static final String INSERT_VALUES = "#{url}, #{title}, #{content}, #{ctime}, #{utime}";

    @Insert("insert into " + TABLE_NAME + " ( " + INSERT_KEYS + " ) values (" + INSERT_VALUES + " )")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int saveXwlbSummary(XwlbSummary xwlbSummary);
}
