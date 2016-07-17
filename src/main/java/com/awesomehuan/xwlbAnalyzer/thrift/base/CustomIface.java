package com.awesomehuan.xwlbAnalyzer.thrift.base;

import com.facebook.fb303.FacebookService;
import com.facebook.fb303.fb_status;
import org.apache.thrift.TException;

import java.util.Map;

/**
 * Created by huanyu on 16/7/17.
 */
public class CustomIface implements FacebookService.Iface {
    @Override
    public String getName() throws TException {
        return null;
    }

    @Override
    public String getVersion() throws TException {
        return null;
    }

    @Override
    public fb_status getStatus() throws TException {
        return null;
    }

    @Override
    public String getStatusDetails() throws TException {
        return null;
    }

    @Override
    public Map<String, Long> getCounters() throws TException {
        return null;
    }

    @Override
    public long getCounter(String s) throws TException {
        return 0;
    }

    @Override
    public void setOption(String s, String s1) throws TException {

    }

    @Override
    public String getOption(String s) throws TException {
        return null;
    }

    @Override
    public Map<String, String> getOptions() throws TException {
        return null;
    }

    @Override
    public String getCpuProfile(int i) throws TException {
        return null;
    }

    @Override
    public long aliveSince() throws TException {
        return 0;
    }

    @Override
    public void reinitialize() throws TException {

    }

    @Override
    public void shutdown() throws TException {

    }
}
