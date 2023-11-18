package com.mineralidentificationservice.rabbit;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RabbitSender {
    private final RabbitTemplate rabbitTemplate;

    public RabbitSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    private Gson gson = new Gson();

    public void send(String rabbitMessage, int waitToSend) throws InterruptedException {
        for (int i = 0; i<3; i++) {
            try {
                rabbitTemplate.convertAndSend(new DirectExchange("direct").getName(), "outputQueue", rabbitMessage);
                log.debug("Sent to rabbit");
                break;
            } catch (AmqpException e) {
                log.warn("Problem connecting to rabbitMQ. Waiting: "+waitToSend/1000+" seconds");
                Thread.sleep(waitToSend);
            }
        }
    }

    public void sendImageToClassification(ImageToRecognitionMessage rabbitErrorMessage) {
        rabbitTemplate.convertAndSend(new DirectExchange("direct").getName(), "inputQueue", gson.toJson(rabbitErrorMessage));
    }

}
