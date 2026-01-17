package ru.binarysimple.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.binarysimple.users.kafka.UserEvent;
import ru.binarysimple.users.mappers.UserMapper;
import ru.binarysimple.users.model.NotificationType;
import ru.binarysimple.users.model.User;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    private final UserMapper mapper;

    @Value("${app.kafka.topics.user-events:user.events}")
    private String orderTopic;

    @Override
    public void sendNotification(User user) {

        if (user == null) {
            return;
        }

        try {
            log.debug("Init notification sending for user {}", user.getId());
            sendEvent(user);
        } catch (Exception e) {
            log.error("Failed to process notification for user {}: {}", user.getUsername(), e.getMessage(), e);
        }
    }

    private void sendEvent(User user) {

        UserEvent event = mapper.toUserEvent(user);
        event.setNotificationType(NotificationType.SUCCESS);

        CompletableFuture<SendResult<String, UserEvent>> future =
                kafkaTemplate.send(orderTopic, user.getUsername(), event);

        // ok
        future.thenAccept(result -> {
            log.debug("Notification sent for user {}. Partition: {}, Offset: {}",
                    user.getUsername(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
        });

        // !ok
        future.exceptionally(ex -> {
            log.error("Failed to send notification for user {}: {}",
                    user.getUsername(), ex.getMessage(), ex);
            // тут можно потом сохранить в БД, например, в таблицу failed_messages
            return null;
        });

    }
}
