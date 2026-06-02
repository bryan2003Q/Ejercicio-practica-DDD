package com.ejercicio.practica.modules.notifications.infrastructure.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class NotificationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private LocalDateTime date;

    public NotificationJpaEntity() {}

    public NotificationJpaEntity(Long id, String message, LocalDateTime date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public LocalDateTime getDate() { return date; }
}
