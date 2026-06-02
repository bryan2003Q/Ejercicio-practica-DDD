package com.ejercicio.practica.modules.notifications.domain;

import java.util.List;

public interface NotificationRepository {
    Notification save(Notification notification);
    List<Notification> findAll();
}
