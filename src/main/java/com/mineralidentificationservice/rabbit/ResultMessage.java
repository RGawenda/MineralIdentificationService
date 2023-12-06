package com.mineralidentificationservice.rabbit;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class ResultMessage {
    UUID classificationID;
    private Map<String, Double> predict;
}
