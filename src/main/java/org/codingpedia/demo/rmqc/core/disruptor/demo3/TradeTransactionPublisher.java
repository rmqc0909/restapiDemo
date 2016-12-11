package org.codingpedia.demo.rmqc.core.disruptor.demo3;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import org.codingpedia.demo.rmqc.core.disruptor.TradeTransaction;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xiedan11 on 2016/12/9.
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
