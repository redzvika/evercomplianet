package com.evercomplianet.bakery;

import com.evercomplianet.bakery.tasks.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Simulation {

    public static Logger logger = LogManager.getLogger("Simulation");

    private Map<String, Product> productMap;
    private List<Order> ordersList;

    private int numberOfEmployees;
    private static int MAX_BAKING_ITEMS_PER_OVEN=3;
    private int maxOvensCapacity;

    private BlockingQueue<Task> ordersQueue = new ArrayBlockingQueue<>(1000);
    private Semaphore ovensSemaphore;
    private Map<String,Order> orderMap=new HashMap<>();

    public Simulation(int numberOfEmployees,int numberOfOvens){
        this.numberOfEmployees = numberOfEmployees;
        this.maxOvensCapacity=numberOfOvens*MAX_BAKING_ITEMS_PER_OVEN;
        ovensSemaphore =new Semaphore(maxOvensCapacity);
    }

    private void readData() throws Exception {
        productMap = DataParser.parseProductFile(Paths.get("src/main/resources/product_stages_time.txt"));
        ordersList = DataParser.parseOrderFile(Paths.get("src/main/resources/orders.txt"));

        for(Order order:ordersList){
            orderMap.put(order.getOrderId(),order);
            ordersQueue.add(new IngredientsGatheringTask(orderMap,order.getOrderId(),productMap.get(order.getProductType())));
            ordersQueue.add(new PreBakeTask(orderMap,order.getOrderId(),productMap.get(order.getProductType())));
            ordersQueue.add(new WrapTask(orderMap,order.getOrderId(),productMap.get(order.getProductType())));
            ordersQueue.add(new BakeTask(orderMap,order.getOrderId(),productMap.get(order.getProductType()),ovensSemaphore));

            //IngredientsGatheringTask task=new IngredientsGatheringTask(order,productMap.get(order.getProductType()));
            //IngredientsGatheringTask task=new IngredientsGatheringTask(order,productMap.get(order.getProductType()));
        }
        //ordersQueue.addAll(ordersList);
    }

    private void startSimulation() throws Exception{
        readData();
        for (int i=0;i<numberOfEmployees;i++){
            Worker worker=new Worker("Worker_"+(i+1), ordersQueue,ovensSemaphore);
            worker.start();
        }
    }

    class Worker extends Thread {
        private final BlockingQueue<Task> queue;
        private final Semaphore semaphore;


        Worker(String name,BlockingQueue<Task> queue,Semaphore semaphore) {
            this.queue = queue;
            this.semaphore =semaphore;
            this.setName(name);
        }
        public void run() {
            try {
                while (true) {
                    Task task=(Task)queue.take();
                    try {
                        if (!task.doTask()){
                            Thread.sleep(100);
                            queue.put(task);
                        }else{
                            if (task.getTaskType()==TaskType.WRAP &&
                                task.getOrderMap().get(task.getOrderId()).getType()==TaskType.DONE){
                                logger.info("order {} product {}  is Done ", task.getOrderId(), task.getProduct().getName());
                            }
                        }

                    }catch (Exception e){
                        logger.error("error ",e);
                    }

                }
            } catch (InterruptedException e) {
            }
        }
    }




    public static void main(String[] args) {
        Simulation simulation=new Simulation(7,3);
        try {
            simulation.startSimulation();
        }catch (Exception e){
            logger.error("failed in simulation",e);
        }
    }


}
