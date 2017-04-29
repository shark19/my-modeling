package controller.multipleline

import math.Common
import model.singleline.Line
import model.singleline.Order
import model.singleline.OrderStatus
import model.singleline.OrdersArchive
import view.SingleLineFrame

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by chist on 23.04.2017.
 */
class MultipleOrderProducer {
    private Line line
    private double minDelay, maxDelay
    private static AtomicInteger orderId
    private String name
    private Thread thread

    MultipleOrderProducer (String name, Line line, double minDelay,double maxDelay) {
        this.name = name
        this.line = line
        this.minDelay = minDelay
        this.maxDelay = maxDelay
        if (orderId == null) {
            orderId = new AtomicInteger(0)
        }
    }

    boolean start() {
        if(thread == null){
            thread = new LocalThread()
            thread.start()
            return true
        }
        return false
    }

    class LocalThread extends Thread{

        @Override
        void run() {
            sleep(getDelay())
            Order order = new Order(orderId.incrementAndGet())
            SingleLineFrame.printLogLine(MultipleOrderProducer.this.name.concat(' create order: '.concat(order.toString())))
            if (!line.add(order)) {
                order.release(OrderStatus.LOST)
                OrdersArchive.getInstance().add(order)
                SingleLineFrame.printLogLine(MultipleOrderProducer.this.name.concat(' lost order: '.concat(order.toString())))
            }
            thread = null
        }

        private long getDelay() {
            Common.getRandom(minDelay, maxDelay) * 1000
        }
    }
}
