package com.ejercicio.practica.modules.notifications.domain;

import java.time.LocalDateTime;

public class Notification {
    private final Long id;
    private final String message;
    private final LocalDateTime date;

    public Notification(Long id, String message, LocalDateTime date) {
        this.id = id;
        this.message = message;
        this.date = date;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public LocalDateTime getDate() { return date; }
}
