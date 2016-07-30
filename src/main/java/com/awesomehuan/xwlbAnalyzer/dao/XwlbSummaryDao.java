package com.awesomehuan.xwlbAnalyzer.dao;

import com.awesomehuan.xwlbAnalyzer.domain.XwlbSummary;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by huanyu on 16/7/17.
 */
@Component
public interface XwlbSummaryDao {
    public static final String TABLE_NAME = "xwlb_summary";
    public static final String SELECT_KEYS = "id, url, title, content, publish_time, ctime, utime";
    public static final String INSERT_KEYS = "url, title, content, publish_time, ctime, utime";
    public static final String INSERT_VALUES = "#{url}, #{title}, #{content}, #{publish_time}, unix_timestamp(), unix_timestamp()";

    @Insert("insert into " + TABLE_NAME + " ( " + INSERT_KEYS + " ) values (" + INSERT_VALUES + " )")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int saveXwlbSummary(XwlbSummary xwlbSummary);

    @Select("select " + SELECT_KEYS + " from " + TABLE_NAME + " where publish_time >= #{begin_time} and publish_time <= #{end_time}")
    public List<XwlbSummary> getSummaryByPublishTime(@Param("begin_time")long beginTime, @Param("end_time")long endTime);
}
