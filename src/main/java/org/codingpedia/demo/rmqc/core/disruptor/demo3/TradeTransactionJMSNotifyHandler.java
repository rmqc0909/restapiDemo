package org.codingpedia.demo.rmqc.core.disruptor.demo3;

import com.lmax.disruptor.EventHandler;
import org.codingpedia.demo.rmqc.core.disruptor.TradeTransaction;

/**
 * Created by xiedan11 on 2016/12/9.
 */
public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction> {


    @Override
    public void onEvent(TradeTransaction tradeTransaction, long l, boolean b) throws Exception {
        //do send jms message
        System.out.println("handling the TradeTransactionJMSNotifyHandler after C1 and C2 finished!");
    }
}
