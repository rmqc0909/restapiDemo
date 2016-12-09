package org.codingpedia.demo.rmqc.core.disruptor;

import com.lmax.disruptor.*;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiedan11 on 2016/12/8.
 */
public class Demo2 {
    @Test
    public void test() throws InterruptedException{
        long beginTime = System.currentTimeMillis();
        int BUFFER_SIZE=1024;
        int THREAD_NUMBERS=4;
        EventFactory<TradeTransaction> eventFactory = new EventFactory<TradeTransaction>() {
            @Override
            public TradeTransaction newInstance() {
                return new TradeTransaction();
            }
        };
        RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(eventFactory, BUFFER_SIZE);

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBERS);

        WorkHandler<TradeTransaction> workHandler = new TradeTransactionInDBHandler();

        WorkerPool<TradeTransaction> workerPool = new WorkerPool<TradeTransaction>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), workHandler);

        workerPool.start(executors);

        for (int i = 0; i < 10000000; i++) {
            long seq = ringBuffer.next();
            ringBuffer.get(seq).setPrice(Math.random() * 9999);
            ringBuffer.publish(seq);
        }

        Thread.sleep(1000);
        workerPool.halt();
        executors.shutdown();

        System.out.println("总耗时:"+(System.currentTimeMillis() - beginTime));
    }
}
