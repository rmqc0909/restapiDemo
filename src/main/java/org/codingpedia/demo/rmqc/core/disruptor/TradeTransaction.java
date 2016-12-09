package org.codingpedia.demo.rmqc.core.disruptor;

/**
 * Created by xiedan11 on 2016/12/8.
 */
public class TradeTransaction {
    private String id;  //交易ID
    private double price;   //交易金额

    public TradeTransaction() {
    }

    public TradeTransaction(String id, double price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
