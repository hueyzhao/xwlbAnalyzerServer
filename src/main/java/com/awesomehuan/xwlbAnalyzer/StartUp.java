package com.awesomehuan.xwlbAnalyzer;

import com.awesomehuan.xwlbAnalyzer.thrift.proxy.ThriftServerProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by huanyu on 16/7/10.
 */
public class StartUp {
    public static void main(String [] args){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:xwlb_analyzer_app_context.xml");
        ThriftServerProxy thriftServerProxy = (ThriftServerProxy) ctx.getBean(ThriftServerProxy.class);
        thriftServerProxy.start();
        System.out.println("System loading complete");
    }
}
