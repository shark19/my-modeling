package controller.singleline

import groovy.transform.PackageScope
import math.Common
import model.singleline.Line
import model.singleline.Order
import model.singleline.OrderActions
import model.singleline.OrderStatus
import model.singleline.OrdersArchive
import view.SingleLineFrame

/**
 * Created by chist on 22.04.2017.
 */
class OrderConsumer implements Observer{
    private Line line
    private double minDelay, maxDelay
    private LocalObservable localObservable
    private Thread thread
    private String name

    OrderConsumer(String name, Line line, double minDelay, double maxDelay){
        this.name = name
        this.line = line
        this.minDelay = minDelay
        this.maxDelay = maxDelay
        localObservable = new LocalObservable()
    }

    LocalObservable getLocalObservableInstance(){
        localObservable
    }

    static class LocalObservable extends Observable{
        @PackageScope
        void setChangedAndNotify(){
            setChanged()
            notifyObservers([OrderActions.IN_PROGRESS])
        }
    }

    @Override
    void update(Observable o, Object arg) {
        if(thread == null){
            thread = new LocalThread()
            thread.start()
        }
    }

    private long getDelay(){
        Common.getRandom(minDelay, maxDelay) * 1000
    }

    class LocalThread extends Thread{
        @Override
        void run() {
            Order order = line.get()
            if(order != null){
                order.accept()
                SingleLineFrame.printLogLine(OrderConsumer.this.name.concat(' take order: '.concat(order.toString())))
                localObservable.setChangedAndNotify()
                sleep(getDelay())
                order.release(OrderStatus.PASSED)
                OrdersArchive.getInstance().add(order)
                SingleLineFrame.printLogLine(OrderConsumer.this.name.concat(' release order: '.concat(order.toString())))
            }
            thread = null
        }
    }
}
