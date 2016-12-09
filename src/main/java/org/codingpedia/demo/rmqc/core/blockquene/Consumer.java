package org.codingpedia.demo.rmqc.core.blockquene;

import java.util.concurrent.BlockingQueue;

/**
 * Created by xiedan11 on 2016/12/6.
 */
public class Consumer implements Runnable {
    BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String temp = queue.take();//如果队列为空，会阻塞当前线程
            System.out.println(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
