package com.mineralidentificationservice;

import com.google.gson.Gson;
import com.mineralidentificationservice.rabbit.ImageToRecognitionMessage;
import com.mineralidentificationservice.rabbit.RabbitSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProcessImage {

    private final RabbitSender rabbitSender;

    public ProcessImage(RabbitSender rabbitSender) {
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
