package com.evercomplianet.bakery.tasks;

import com.evercomplianet.bakery.Order;
import com.evercomplianet.bakery.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class IngredientsGatheringTask extends Task{

    public static Logger logger = LogManager.getLogger("IngredientsGatheringTask");

    public IngredientsGatheringTask(Map<String,Order> orderMap,String orderId, Product product){
        super(orderMap,orderId,product);
    }
    @Override
    public boolean doTask() throws Exception{

        Order order=orderMap.get(orderId);

        if (order.getType()==TaskType.NEW || order.getType()==TaskType.PRE_BAKE) {
            Thread.sleep(super.product.getIngredientsGatheringTime());
            logger.info("order {} product {}  gathering ingredients {} minutes ", orderId, product.getName(), product.getIngredientsGatheringTime());

            if (order.getParallelCounter().incrementAndGet()==2){
                order.setType(TaskType.GI_PB);
                logger.debug("order {} setting type to {} ",orderId,TaskType.GI_PB.name() );
            }else{
                order.setType(TaskType.GATHER_INGREDIENTS);
            }

            return true;
        }

        return false;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.GATHER_INGREDIENTS;
    }
}
