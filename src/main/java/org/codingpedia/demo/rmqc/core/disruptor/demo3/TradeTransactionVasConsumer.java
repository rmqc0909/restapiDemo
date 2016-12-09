package org.codingpedia.demo.rmqc.core.disruptor.demo3;

import com.lmax.disruptor.EventHandler;
import org.codingpedia.demo.rmqc.core.disruptor.TradeTransaction;

/**
 * Created by xiedan11 on 2016/12/9.
 */
public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {

    @Override
    public void onEvent(TradeTransaction tradeTransaction, long l, boolean b) throws Exception {
        //do something...
    }
}
