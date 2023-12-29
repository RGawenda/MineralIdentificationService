package com.mineralidentificationservice.service;

import com.google.gson.Gson;
import com.mineralidentificationservice.rabbit.ResultMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Service
public class ClassificationResultService {
    private final ConcurrentHashMap<UUID, String> classificationResults = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    public void processResult(String result) {
        ResultMessage resultObj = this.gson.fromJson(result, ResultMessage.class);
        String predict = this.gson.toJson(resultObj.getPredict());
        classificationResults.put(resultObj.getClassificationID(), predict);
    }

    public String getClassificationResult(UUID uuid) {
        return classificationResults.get(uuid);
    }
}
