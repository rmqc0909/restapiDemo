package org.codingpedia.demo.rmqc.core.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xiedan11 on 2016/12/9.
 * 写入数据有两种方式,方式1有两个优点:
 * 1.隐藏了RingBuffer，业务方无需直接对RingBuffer进行写入操作（fill和publish）
 * 2.数据的fill和publish可以用单独的类进行封装，方便维护
 */

/**
 * 方式1
 */
public class TradeTransactionPublisher implements Runnable {
    Disruptor<TradeTransaction> disruptor;
    private CountDownLatch latch;
    private static int LOOP = 10000000;     //模拟一千万次交易的发生

    public TradeTransactionPublisher(Disruptor<TradeTransaction> disruptor, CountDownLatch latch) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        TradeTransactionEventTranslator eventTranslator = new TradeTransactionEventTranslator();
        for (int i = 0; i < LOOP; i++) {
            disruptor.publishEvent(eventTranslator);    //3.将事件提交到 RingBuffer
        }
        latch.countDown();
    }

}

/**
 * 1.先从 RingBuffer 获取下一个可以写入的事件的序号
 * 2.获取对应的事件对象，将数据写入事件对象
 */
class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
    private Random random = new Random();
    @Override
    public void translateTo(TradeTransaction tradeTransaction, long l) {
        this.generateTradeTransaction(tradeTransaction);
    }

    private TradeTransaction generateTradeTransaction(TradeTransaction transaction) {
        transaction.setPrice(random.nextDouble() * 9999);
        return transaction;
    }
}


/**
 * 方式2
 *

 public TradeTransactionPublisher implements Runnable{

     final RingBuffer<TradeTransaction> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<TradeTransaction>() {
     public TradeTransaction newInstance() {
     return new TradeTransaction();
     }
     }, BUFFER_SIZE, new YieldingWaitStrategy());


     @Override
     public void run() {
     long seq;
     for (int i = 0; i < 10000000; i++) {
     seq = ringBuffer.next();    //占个坑 --ringBuffer一个可用区块
     ringBuffer.get(seq).setPrice(Math.random() * 9999);     //给这个区块放入数据
     ringBuffer.publish(seq);    //发布这个区块的数据使handler(consumer)可见
     }
     latch.countDown();
     }
 }

 */
