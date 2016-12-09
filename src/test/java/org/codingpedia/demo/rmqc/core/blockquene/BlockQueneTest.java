package org.codingpedia.demo.rmqc.core.blockquene;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by xiedan11 on 2016/12/6.
 */
public class BlockQueneTest {
    @Test
    public void test() {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(2);
        // BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        //不设置的话，LinkedBlockingQueue默认大小为Integer.MAX_VALUE
        // BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);

        new Thread(consumer, "Consumer====>>>>").start();
        new Thread(producer, "Producer====>>>>").start();
        new Thread(consumer, "Consumer====>>>>").start();

        for (int i = 0; i < 5; i++) {
            new Thread(producer, "Producer" + (i + 1)).start();
            new Thread(consumer, "Consumer" + (i + 1)).start();
        }
    }
}
