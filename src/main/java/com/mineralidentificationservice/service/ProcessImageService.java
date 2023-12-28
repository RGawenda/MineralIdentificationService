package com.mineralidentificationservice.service;

import com.google.gson.Gson;
import com.mineralidentificationservice.rabbit.ImageToRecognitionMessage;
import com.mineralidentificationservice.rabbit.RabbitSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProcessImageService {

    private final RabbitSender rabbitSender;

    public ProcessImageService(RabbitSender rabbitSender) {
        this.rabbitSender = rabbitSender;
    }

    private final Gson gson = new Gson();

    public void process(UUID uuid, String image) {

        ImageToRecognitionMessage imageToRecognitionMessage = new ImageToRecognitionMessage(uuid, image);
        try {
            this.rabbitSender.send(gson.toJson(imageToRecognitionMessage), 50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
