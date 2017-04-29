package controller.singleline

import controller.multipleline.MultipleLineController
import model.singleline.Line
import model.singleline.OrdersArchive
import view.SingleLineBundle

/**
 * Created by chist on 22.04.2017.
 */
class SingleLineController extends Thread{
    private Line line
    private OrderConsumer consumer
    private OrderProducer producer

    SingleLineController(SingleLineBundle bundle, Observer observer){
        line = new Line(MultipleLineController.line1Name, bundle.lineMaxSize)
        consumer = new OrderConsumer('Consumer_1', line, bundle.consumerMinDelay, bundle.consumerMaxDelay)
        producer = new OrderProducer('Producer_1', line, bundle.producerMinDelay, bundle.producerMaxDelay)
        line.addObserver(consumer)
        line.addObserver(observer)
        OrdersArchive.getInstance().addObserver(observer)
        consumer.getLocalObservableInstance().addObserver(observer)
    }

   void stopWork(){
        if(producer != null) {
            producer.stop()
        }
       line.deleteObservers()
       OrdersArchive.getInstance().deleteObservers()
       consumer.getLocalObservableInstance().deleteObservers()
   }

    @Override
    void run() {
        if(producer != null) {
            producer.start()
        }
    }
}
