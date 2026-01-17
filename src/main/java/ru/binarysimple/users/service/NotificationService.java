package ru.binarysimple.users.service;

import ru.binarysimple.users.model.User;

public interface NotificationService {
    void sendNotification(User user);
}
