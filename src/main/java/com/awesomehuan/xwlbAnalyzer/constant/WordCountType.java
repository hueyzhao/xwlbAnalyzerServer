package com.awesomehuan.xwlbAnalyzer.constant;

/**
 * Created by huanyu on 16/7/23.
 */
public enum WordCountType {
    TODAY(1), LATEST_ONE_WEEK(2), LATEST_ONE_MONTH(3);
    private int value;

    WordCountType(int value) {
        this.value = value;
    }
}
