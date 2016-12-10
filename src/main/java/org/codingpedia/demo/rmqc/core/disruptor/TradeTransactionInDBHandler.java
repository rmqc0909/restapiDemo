package org.codingpedia.demo.rmqc.core.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

/**
 * Created by xiedan11 on 2016/12/8.
 */
public class TradeTransactionInDBHandler implements EventHandler<TradeTransaction>, WorkHandler<TradeTransaction>{
    @Override
    public void onEvent(TradeTransaction tradeTransaction, long l, boolean b) throws Exception {
        this.onEvent(tradeTransaction);
    }

    @Override
    public void onEvent(TradeTransaction tradeTransaction) throws Exception {
        //这里是具体的消费逻辑
        tradeTransaction.setId(UUID.randomUUID().toString());   //生成ID
        System.out.println("TradeTransactionInDBHandler:" + tradeTransaction.getId());
    }
}
