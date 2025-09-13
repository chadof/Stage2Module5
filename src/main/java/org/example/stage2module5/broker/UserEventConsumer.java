package org.example.stage2module5.broker;

import org.example.stage2module5.config.KafkaConfigProperties;
import org.example.stage2module5.dto.UserEvent;
import org.example.stage2module5.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private final UserService userService;
    private final KafkaConfigProperties kafkaConfigProperties;

    public UserEventConsumer(UserService userService, KafkaConfigProperties kafkaConfigProperties) {
        this.userService = userService;
        this.kafkaConfigProperties = kafkaConfigProperties;
    }

    @KafkaListener(topics = "#{@kafkaConfigProperties.topic}", groupId = "#{@kafkaConfigProperties.groupId}")
    public void consume(UserEvent event) {
        if (event.getEmail() != null && !event.getEmail().trim().isEmpty()) {
            userService.sendEmail(event.getEmail(), event.getOperation());
        }
    }
}
