package com.example.orderservice.web;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class Controller {

    @Value("${app.kafka.kafkaMessageOrderTopic}")
    private String topicName;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Order order){
        OrderEvent event = new OrderEvent();
        event.setProduct(order.getProduct());
        event.setQuantity(order.getQuantity());

        kafkaTemplate.send(topicName, event);

        return ResponseEntity.ok("Message sent to kafka!");
    }
}
