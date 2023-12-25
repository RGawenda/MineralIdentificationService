package com.mineralidentificationservice.service;

import com.google.gson.Gson;
import com.mineralidentificationservice.rabbit.ResultMessage;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
