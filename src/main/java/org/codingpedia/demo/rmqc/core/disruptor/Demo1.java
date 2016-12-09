package org.codingpedia.demo.rmqc.core.disruptor;


import com.lmax.disruptor.*;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Created by xiedan11 on 2016/12/8.
 *
 * 使用原生API创建一个简单的生产者和消费者
 */
public class Demo1 {
    @Test
    public void test() throws InterruptedException, ExecutionException {
        long beginTime = System.currentTimeMillis();
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBERS = 4;
        /**
         * createSingleProducer创建一个单生产者的RingBuffer:
         * 1.EventFactory:其职责就是产生数据填充RingBuffer的区块
         * 2.RingBuffer:必须是2的指数倍 目的是为了将求模运算转为&运算提高效率
         * 3.RingBuffer:在没有可用区块的时候(可能是消费者（或者说是事件处理器） 太慢了)的等待策略
         */
        final RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<TradeTransaction>() {
            public TradeTransaction newInstance() {
                return new TradeTransaction();
            }
        }, BUFFER_SIZE, new YieldingWaitStrategy());

        //创建线程池
        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBERS);
        //创建SequenceBarrier
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        //创建消息处理器
        BatchEventProcessor<TradeTransaction> transactionBatchEventProcessor = new BatchEventProcessor<TradeTransaction>(ringBuffer, sequenceBarrier, new TradeTransactionInDBHandler());
        //RingBuffer监视消费者状态，如果只有一个消费者的情况可以省略
        ringBuffer.addGatingSequences(transactionBatchEventProcessor.getSequence());
        //把消息处理器提交到线程池
        executors.submit(transactionBatchEventProcessor);

        Future<?> future = executors.submit(new Callable<Void>() {
            public Void call() throws Exception {
                long seq;
                for (int i = 0; i < 10000000; i++) {
                    seq = ringBuffer.next();    //占个坑 --ringBuffer一个可用区块
                    ringBuffer.get(seq).setPrice(Math.random() * 9999);     //给这个区块放入数据
                    ringBuffer.publish(seq);    //发布这个区块的数据使handler(consumer)可见
                }
                return null;
            }
        });

        future.get();   //等待生产者结束
        Thread.sleep(1000);
        transactionBatchEventProcessor.halt();  //通知事件(或者说消息)处理器 可以结束了（并不是马上结束!!!）
        executors.shutdown();   //终止线程
        System.out.println("总耗时:"+(System.currentTimeMillis() - beginTime));

    }

}
