package com.evercomplianet.bakery;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class Product {

    @JsonProperty("name")
    @Getter private String name;

    @JsonProperty("ingredients")
    @Getter private long ingredientsGatheringTime;

    @JsonProperty("pre_bake")
    @Getter private long preBakeTime;

    @JsonProperty("bake")
    @Getter private long bakeTime;

    @JsonProperty("wrap")
    @Getter private long wrapTime;
}
