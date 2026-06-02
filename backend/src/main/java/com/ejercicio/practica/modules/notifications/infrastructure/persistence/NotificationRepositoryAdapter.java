package com.ejercicio.practica.modules.notifications.infrastructure.persistence;

import com.ejercicio.practica.modules.notifications.domain.Notification;
import com.ejercicio.practica.modules.notifications.domain.NotificationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final SpringDataNotificationRepository springDataRepository;

    public NotificationRepositoryAdapter(SpringDataNotificationRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Notification save(Notification notification) {
        NotificationJpaEntity entity = new NotificationJpaEntity(notification.getId(), notification.getMessage(), notification.getDate());
        NotificationJpaEntity saved = springDataRepository.save(entity);
        return new Notification(saved.getId(), saved.getMessage(), saved.getDate());
    }

    @Override
    public List<Notification> findAll() {
        return springDataRepository.findAll().stream()
                .map(e -> new Notification(e.getId(), e.getMessage(), e.getDate()))
                .collect(Collectors.toList());
    }
}
