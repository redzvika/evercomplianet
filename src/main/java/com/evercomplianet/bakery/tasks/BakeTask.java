package com.evercomplianet.bakery.tasks;

import com.evercomplianet.bakery.Order;
import com.evercomplianet.bakery.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.Semaphore;

public class BakeTask extends Task{

    public static Logger logger = LogManager.getLogger("BakeTask");
    private Semaphore semaphore;

    public BakeTask(Map<String,Order> orderMap, String orderId , Product product, Semaphore semaphore){
        super(orderMap,orderId,product);
        this.semaphore = semaphore;
    }
    @Override
    public boolean doTask() throws Exception {

        Order order=orderMap.get(orderId);
        if (order.getType()==TaskType.GI_PB) {
            logger.debug("order {} wait for Oven slot , available permits {}", orderId, semaphore.availablePermits());
            semaphore.acquire();
            logger.debug("order {} acquired oven slot ", orderId);
            logger.info("order {} product {} bake {} minutes", orderId, product.getName(), product.getBakeTime());
            Thread.sleep(product.getBakeTime());
            semaphore.release();
            logger.debug("order {} release oven slot ", orderId);
            order.setType(TaskType.BAKE);
            return true;
        }
        //logger.debug("order {} product {} not in expected Stage {} ",orderId,product.getName(),TaskType.GI_PB.name());
        return false;
    }


    @Override
    public TaskType getTaskType() {
        return TaskType.BAKE;
    }
}
