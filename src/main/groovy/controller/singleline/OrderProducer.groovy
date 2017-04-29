package controller.singleline

import math.Common
import model.singleline.Line
import model.singleline.Order
import model.singleline.OrderStatus
import model.singleline.OrdersArchive
import view.SingleLineFrame

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by chist on 22.04.2017.
 */
class OrderProducer extends Thread{
    private Line line
    private double minDelay, maxDelay
    private static AtomicInteger orderId
    private String name

    OrderProducer(String name, Line line, double minDelay, double maxDelay){
        this.name = name
        this.line = line
        this.minDelay = minDelay
        this.maxDelay = maxDelay
        if(orderId == null){
            orderId = new AtomicInteger(0)
        }
    }

    @Override
    void run() {
        while (!interrupted){
            sleep(getDelay())
            Order order = new Order(orderId.incrementAndGet())
            SingleLineFrame.printLogLine(name.concat(' create order: '.concat(order.toString())))
            if(!line.add(order)){
                order.release(OrderStatus.LOST)
                OrdersArchive.getInstance().add(order)
                SingleLineFrame.printLogLine(name.concat(' lost order: '.concat(order.toString())))
            }
        }
    }

    private long getDelay(){
        Common.getRandom(minDelay, maxDelay) * 1000
    }
}
