package com.mineralidentificationservice.config;

import com.mineralidentificationservice.rabbit.RabbitReceiver;

import com.google.gson.Gson;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import lombok.Data;

@Data
@Configuration
public class RabbitConfiguration implements RabbitListenerConfigurer {

    private final Integer ONE_MESSAGE = 1;
    private final Gson gson;
    @Value("${rabbit.inputQueue}")
    private String inputQueue;
    @Value("${rabbit.outputQueue}")
    private String outputQueue;
    @Value("${rabbit.direct}")
    private String direct;

    @Value("${rabbit.ip}")
    private String serverIp;

    public RabbitConfiguration(Gson gson){
        this.gson = gson;
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(direct);
    }

    @Bean
    public Queue inputQueue(){
        return new Queue(inputQueue);
    }

    @Bean
    public Queue outputQueue(){
        return new Queue(outputQueue);
    }

    @Bean
    public Binding inputBinding(Queue inputQueue, DirectExchange directExchange){
        return BindingBuilder.bind(inputQueue).to(directExchange).with("inputQueue");
    }

    @Bean
    public Binding outputBinding(Queue outputQueue, DirectExchange directExchange){
        return BindingBuilder.bind(outputQueue).to(directExchange).with("outputQueue");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter(){
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate (final ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory massageHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory =
                new DefaultMessageHandlerMethodFactory();
        defaultMessageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return defaultMessageHandlerMethodFactory;
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchOneRabbitListenerContainerFactory(
            ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory =
                new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        simpleRabbitListenerContainerFactory.setPrefetchCount(ONE_MESSAGE);
        return simpleRabbitListenerContainerFactory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(massageHandlerMethodFactory());
    }

    @Bean
    public RabbitReceiver rabbitReceiver(){
        return new RabbitReceiver();
    }
}