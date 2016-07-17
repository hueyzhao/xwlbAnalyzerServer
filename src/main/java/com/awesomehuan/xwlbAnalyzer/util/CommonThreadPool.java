package com.awesomehuan.xwlbAnalyzer.util;

import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by huanyu on 16/7/17.
 */
@Service
public class CommonThreadPool<T> {
    private  int maxQueueSize = 100;
    private  int corePoolSize = 5;
    private  int maxPoolSize = 20;
    private List<Future<T>> funtureResults = new CopyOnWriteArrayList<Future<T>>();


    private ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(maxQueueSize);
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maxPoolSize,10L, TimeUnit.MINUTES,blockingQueue);

    public CommonThreadPool(int maxQueueSize, int corePoolSize, int maxPoolSize) {
        this.maxQueueSize = maxQueueSize;
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
    }

    public CommonThreadPool() {
    }

    public void addWork(IThreadWorker threadWorker){
            Callable<T> callable = new CommonThreadCallableWorker<T>(threadWorker);
            Future<T> future = threadPoolExecutor.submit(callable);
            funtureResults.add(future);
    }

    public List<T> constructCallBacks(){
        if (funtureResults.isEmpty()){
            return Collections.emptyList();
        }
        List<T> callBacks = new ArrayList<T>();
        for (Future<T> future : funtureResults){
            try {
                callBacks.add(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return callBacks;
    }

    @PreDestroy
    public void releaseThreadPool() {
        threadPoolExecutor.shutdown();
    }

    class CommonThreadCallableWorker<T> implements Callable<T>{
        IThreadWorker<T> threadWorker = null;

        public CommonThreadCallableWorker(IThreadWorker<T> threadWorker) {
            this.threadWorker = threadWorker;
        }

        public IThreadWorker<T> getThreadWorker() {
            return threadWorker;
        }

        public void setThreadWorker(IThreadWorker<T> threadWorker) {
            this.threadWorker = threadWorker;
        }

        @Override
        public T call() throws Exception {
            return threadWorker.work();
        }
    }

}
