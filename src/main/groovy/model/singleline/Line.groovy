package model.singleline

import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by chist on 22.04.2017.
 */
class Line extends Observable{
    private final ConcurrentLinkedQueue<Order> orders = new ConcurrentLinkedQueue<>()
    private AtomicInteger maxSize
    private final String lock = getClass().toString().concat("_lock")
    private String name

    Line(String name, int maxSize){
        this.name = name
        this.maxSize = new AtomicInteger(maxSize)
    }

    boolean add(Order order){
        synchronized (lock) {
            boolean result = false
            if (orders.size() < maxSize.get()) {
                orders.offer(order)
                result = true
            }
            setChanged()
            notifyObservers([OrderActions.NEW_ORDER, new ArrayList<>(orders), name])
            result
        }
    }

    Order get() {
        synchronized (lock) {
            if(!orders.empty){
                orders.poll()
            }
            else null
        }
    }
}
