package ru.binarysimple.users.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.binarysimple.users.event.UserCreatedEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserNotificationListener {

    private final NotificationService notificationService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreatedEvent(UserCreatedEvent event) {

        log.debug("Processing notification for user {}", event.getUser().getUsername());

        notificationService.sendNotification(event.getUser());
    }
}