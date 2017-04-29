package model.singleline

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.time.LocalTime


/**
 * Created by chist on 22.04.2017.
 */
@EqualsAndHashCode
@ToString
class Order {
    int id
    OrderStatus status
    LocalTime createTime, releaseTime

    Order(int id){
        this.id = id
        status = OrderStatus.NEW
        createTime = LocalTime.now()
    }

    void accept(){
        status = OrderStatus.IN_PROGRESS
    }

    void release(OrderStatus status){
        this.status = status
        releaseTime = LocalTime.now()
    }
}
