package com.awesomehuan.xwlbAnalyzer.domain;

/**
 * Created by huanyu on 16/7/23.
 */
public class XwlbWordCount {
    private long id;
    private String keyword;
    private int count;
    private long publish_time;
    private long ctime;
    private long utime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(long publish_time) {
        this.publish_time = publish_time;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    @Override
    public String toString() {
        return "XwlbWordCount{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", count=" + count +
                ", publish_time=" + publish_time +
                ", ctime=" + ctime +
                ", utime=" + utime +
                '}';
    }
}
