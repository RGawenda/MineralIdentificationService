package com.mineralidentificationservice.rabbit;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
public class RabbitReceiver {
    private final Gson gson;


    public RabbitReceiver(){
        this.gson = new Gson();
    }


    @RabbitListener(queues = "#{inputQueue.name}", containerFactory = "prefetchOneRabbitListenerContainerFactory")
    public void receiveMessage(String jsonFromRabbitQueue) {
        ResultMessage rabbitMessage = null;
        try{
            log.info("Received rabbitmq message");
            rabbitMessage = this.gson.fromJson(jsonFromRabbitQueue, ResultMessage.class);
            log.info(rabbitMessage.authToken);
        } catch (Exception ex){
            log.error("An unexpected error occurred: {}. Message has been sent to the error queue",
                    ex.getMessage(), ex);
        }
    }
}
