package com.services.trainings.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${rabbitmq.queue}")
    private String queue;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}

