package com.evercomplianet.bakery.tasks;

import com.evercomplianet.bakery.Order;
import com.evercomplianet.bakery.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class WrapTask extends Task{

    public static Logger logger = LogManager.getLogger("WrapTask");


    public WrapTask(Map<String,Order> orderMap,String orderId , Product product){

        super(orderMap,orderId,product);
    }
    @Override
    public boolean doTask() throws Exception{

        Order order=orderMap.get(orderId);
        if (order.getType()==TaskType.BAKE) {
            Thread.sleep(super.product.getWrapTime());
            logger.info("order {} product {}  wrap  {} minutes ", orderId, product.getName(), product.getWrapTime());
            order.setType(TaskType.DONE);
            return true;
        }

        //logger.debug("order {} product {} not in correct stage",orderId,product.getName());
        return false;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.WRAP;
    }
}
