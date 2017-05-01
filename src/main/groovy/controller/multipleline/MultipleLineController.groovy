package controller.multipleline

import controller.singleline.OrderConsumer
import math.Common
import model.singleline.Line
import model.singleline.OrdersArchive
import view.MultipleLineBundle

/**
 * Created by chist on 23.04.2017.
 */
class MultipleLineController extends Thread{
    private Line line1, line2
    private MultipleOrderProducer producer1, producer2, producer3
    private OrderConsumer consumer1, consumer2
    private int generatorMinDelay, generatorMaxDelay
    static String line1Name = 'Line_1'
    static String line2Name = 'Line_2'

    MultipleLineController(MultipleLineBundle bundle, Observer observer){
        line1 = new Line(line1Name, bundle.lineMaxSize1)
        line2 = new Line(line2Name, bundle.lineMaxSize2)
        producer1 = new MultipleOrderProducer('Producer_1', line1, bundle.producerMinDelay1, bundle.producerMaxDelay1)
        producer2 = new MultipleOrderProducer('Producer_2', line1, bundle.producerMinDelay2, bundle.producerMaxDelay2)
        producer3 = new MultipleOrderProducer('Producer_3', line2, bundle.producerMinDelay3, bundle.producerMaxDelay3)
        consumer1 = new OrderConsumer('Consumer_1', line1, bundle.consumerMinDelay1, bundle.consumerMaxDelay1, 0)
        consumer2 = new OrderConsumer('Consumer_2', line2, bundle.consumerMinDelay2, bundle.consumerMaxDelay2, 0)
        generatorMinDelay = bundle.generatorMinDelay
        generatorMaxDelay = bundle.generatorMaxDelay
        line1.addObserver(consumer1)
        line2.addObserver(consumer2)
        line1.addObserver(observer)
        line2.addObserver(observer)
        OrdersArchive.getInstance().addObserver(observer)
        consumer1.getLocalObservableInstance().addObserver(observer)
        consumer2.getLocalObservableInstance().addObserver(observer)
    }

    void stopWork(){
        stop()
        line1.deleteObservers()
        line2.deleteObservers()
        OrdersArchive.getInstance().deleteObservers()
        consumer1.getLocalObservableInstance().deleteObservers()
        consumer2.getLocalObservableInstance().deleteObservers()
    }

    @Override
    void run() {
        while(!interrupted) {
            sleep(getDelay())
            if(producer1.start()) continue
            if(producer2.start()) continue
            producer3.start()
        }
    }

    private long getDelay() {
        Common.getRandom(generatorMinDelay, generatorMaxDelay) * 1000
    }
}
