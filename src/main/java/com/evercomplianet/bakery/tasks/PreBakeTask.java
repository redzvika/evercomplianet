package com.evercomplianet.bakery.tasks;

import com.evercomplianet.bakery.Order;
import com.evercomplianet.bakery.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class PreBakeTask extends Task{

    public static Logger logger = LogManager.getLogger("PreBakeTask");
    public PreBakeTask(Map<String,Order> orderMap,String orderId , Product product){
        super(orderMap,orderId,product);
    }
    @Override
    public boolean doTask() throws Exception{
        Order order=orderMap.get(orderId);

        if (order.getType()==TaskType.NEW || order.getType()==TaskType.GATHER_INGREDIENTS) {
            Thread.sleep(super.product.getPreBakeTime());
            logger.info("order {} product {}  pre bake  {} minutes ",orderId,product.getName(),product.getPreBakeTime());

            if (order.getParallelCounter().incrementAndGet()==2){
                order.setType(TaskType.GI_PB);
                logger.debug("order {} setting type to {} ",orderId,TaskType.GI_PB.name() );
            }else{
                order.setType(TaskType.PRE_BAKE);
            }
           // orderMap.put(orderId,order);
            return true;
        }
        //logger.debug("order {} product {} not in correct stage",orderId,product.getName());
        return false;

    }

    @Override
    public TaskType getTaskType() {
        return TaskType.PRE_BAKE;
    }

}
