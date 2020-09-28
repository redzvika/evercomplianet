package com.evercomplianet.bakery.tasks;

import com.evercomplianet.bakery.Order;
import com.evercomplianet.bakery.Product;
import lombok.Getter;

import java.util.Map;

public abstract class  Task {

    @Getter  protected String orderId;
    @Getter  protected Product product;
    @Getter  protected Map<String,Order> orderMap;
    protected int taskTime;

    public Task(Map<String,Order> orderMap,String orderId, Product product){
        this.orderMap=orderMap;
        this.orderId=orderId;
        this.product=product;
    }

    abstract public boolean doTask() throws Exception;

    abstract public TaskType getTaskType();

}
