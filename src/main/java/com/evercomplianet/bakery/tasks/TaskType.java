package com.evercomplianet.bakery.tasks;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public enum TaskType {
    NEW,
    GATHER_INGREDIENTS,
    PRE_BAKE,
    GI_PB,
    BAKE,
    WRAP,
    DONE
}
