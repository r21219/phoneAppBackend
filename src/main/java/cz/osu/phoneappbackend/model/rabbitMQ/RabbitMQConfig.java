package cz.osu.phoneappbackend.model.rabbitMQ;

import cz.osu.phoneappbackend.model.ExchangeType;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(ExchangeType.TOPIC.toString());
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(ExchangeType.DIRECT.toString());
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(ExchangeType.FANOUT.toString());
    }
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
