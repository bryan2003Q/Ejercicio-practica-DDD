package com.ejercicio.practica.modules.notifications.infrastructure.rest;

import com.ejercicio.practica.modules.notifications.application.NotificationDTO;
import com.ejercicio.practica.modules.notifications.application.NotificationService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<NotificationDTO> getAll() {
        return service.getAllNotifications();
    }
}
