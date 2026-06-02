package com.ejercicio.practica.modules.notifications.application;

import com.ejercicio.practica.modules.notifications.domain.Notification;
import com.ejercicio.practica.modules.notifications.domain.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void createNotification(String message) {
        Notification notification = new Notification(null, message, LocalDateTime.now());
        repository.save(notification);
    }

    public List<NotificationDTO> getAllNotifications() {
        return repository.findAll().stream().map(n -> {
            NotificationDTO dto = new NotificationDTO();
            dto.id = n.getId();
            dto.message = n.getMessage();
            dto.date = n.getDate();
            return dto;
        }).collect(Collectors.toList());
    }
}
