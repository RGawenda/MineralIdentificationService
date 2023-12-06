package com.mineralidentificationservice.rabbit;

import com.google.gson.Gson;

import com.mineralidentificationservice.services.ClassificationResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class RabbitReceiver {
    private final Gson gson;
    @Autowired
    private ClassificationResultService classificationResultService;

    public RabbitReceiver() {
        this.gson = new Gson();
    }


    @RabbitListener(queues = "#{inputQueue.name}", containerFactory = "prefetchOneRabbitListenerContainerFactory")
    public void receiveMessage(String jsonFromRabbitQueue) {
        ResultMessage rabbitMessage = null;
        try {
            log.info("Received rabbitmq message");
            rabbitMessage = this.gson.fromJson(jsonFromRabbitQueue, ResultMessage.class);
            classificationResultService.processResult(jsonFromRabbitQueue);

            log.info(rabbitMessage.classificationID.toString());
        } catch (Exception ex) {
            log.error("An unexpected error occurred: {}. Message has been sent to the error queue",
                    ex.getMessage(), ex);
        }
    }
}
