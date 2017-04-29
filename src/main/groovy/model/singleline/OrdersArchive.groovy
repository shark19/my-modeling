package model.singleline

/**
 * Created by chist on 22.04.2017.
 */

class OrdersArchive extends Observable{
    private static final OrdersArchive INSTANCE = new OrdersArchive()
    private final String lock = getClass().toString().concat("_lock")

    private OrdersArchive(){
        if(INSTANCE != null){
            throw new ExceptionInInitializerError('You can not create second instance of Singleton')
        }
    }

    static OrdersArchive getInstance(){
        INSTANCE
    }

    private List<Order> archive = Collections.synchronizedList(new ArrayList())

    void add(Order order){
        archive.add(order)
        setChanged()
        notifyObservers([OrderActions.RELEASE_ORDER, order])
    }

    int getOrdersCount(){
        archive.size()
    }

    int getCountByStatus(OrderStatus status){
        synchronized (lock) {
            int count = 0
            archive.each {
                if (it.status == status) {
                    count++
                }
            }
            count
        }
    }

    double getResultStatusDiff(){
        synchronized (lock) {
            (getCountByStatus(OrderStatus.LOST) as double) / ((getOrdersCount() as double) / 100.0)
        }
    }
}
