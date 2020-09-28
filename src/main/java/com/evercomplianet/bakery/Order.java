package com.evercomplianet.bakery;

import com.evercomplianet.bakery.tasks.Task;
import com.evercomplianet.bakery.tasks.TaskType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    @JsonProperty("orderId")
    @Getter
    private String orderId;

    @JsonProperty("date")
    @Getter
    private String date;

    @JsonProperty("productType")
    @Getter
    private String productType;

    @JsonProperty("status")
    @Getter
    private String statusStringValue;

    @Getter
    @Setter
    private TaskType type= TaskType.NEW;

    @Getter
    private AtomicInteger parallelCounter=new AtomicInteger(0);
}
