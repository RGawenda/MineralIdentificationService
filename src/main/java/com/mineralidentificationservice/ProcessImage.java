package com.mineralidentificationservice;

import com.mineralidentificationservice.rabbit.ImageToRecognitionMessage;
import com.mineralidentificationservice.rabbit.RabbitSender;
import org.springframework.stereotype.Service;

@Service
public class ProcessImage {

    private RabbitSender rabbitSender;
    public ProcessImage(RabbitSender rabbitSender){
        this.rabbitSender = rabbitSender;
    }

    public void process(String path ){

       //ImageToRecognitionMessage imageToRecognitionMessage = new ImageToRecognitionMessage();
        try {
            this.rabbitSender.send(path, 50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
