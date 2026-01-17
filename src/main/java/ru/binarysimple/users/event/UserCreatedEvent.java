package ru.binarysimple.users.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.binarysimple.users.model.User;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UserCreatedEvent {
    private final User user; // Пользователь, создавший заказ order;
    private final String source;
    private final String eventId = UUID.randomUUID().toString(); // Уникальный ID события
    private final long timestamp = System.currentTimeMillis();
}