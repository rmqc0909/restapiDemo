package org.codingpedia.demo.rmqc.core.disruptor.demo3;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import org.codingpedia.demo.rmqc.core.disruptor.TradeTransaction;
import org.codingpedia.demo.rmqc.core.disruptor.TradeTransactionInDBHandler;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiedan11 on 2016/12/9.
 * 假设如下场景：
 1、交易网关收到交易(P1)把交易数据发到RingBuffer中
 2、负责处理增值业务的消费者C1和负责数据存储的消费者C2负责处理交易
 3、负责发送JMS消息的消费者C3在C1和C2处理完成后再进行处理。
 */
public class Demo3 {
    @Test
    public void test() throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        int bufferSize = 1024;
        ExecutorService executors = Executors.newFixedThreadPool(4);
        Disruptor<TradeTransaction> disruptor = new Disruptor<TradeTransaction>(new EventFactory<TradeTransaction>() {
            @Override
            public TradeTransaction newInstance() {
                return new TradeTransaction();
            }
        }, bufferSize, executors, ProducerType.SINGLE, new BusySpinWaitStrategy());
        //使用disruptor创建消费者组C1,C2
        EventHandlerGroup<TradeTransaction> handlerGroup = disruptor.handleEventsWith(new TradeTransactionVasConsumer(), new TradeTransactionInDBHandler());
        TradeTransactionJMSNotifyHandler jmsNotifyHandler = new TradeTransactionJMSNotifyHandler();
        //声明在C1,C2完事之后执行JMS消息发送操作 也就是流程走到C3
        handlerGroup.then(jmsNotifyHandler);

        disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);
        //生产者准备
        executors.submit(new TradeTransactionPublisher(disruptor, latch));
        latch.await();      //等待生产者完事.
        disruptor.shutdown();
        executors.shutdown();
        System.out.println("总耗时:"+(System.currentTimeMillis() - beginTime));
    }
}
