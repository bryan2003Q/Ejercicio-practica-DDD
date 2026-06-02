package com.ejercicio.practica.modules.notifications.infrastructure.events;

import com.ejercicio.practica.modules.notifications.application.NotificationService;
import com.ejercicio.practica.shared.domain.events.BookRentedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookRentedEventListener {

    private final NotificationService notificationService;

    public BookRentedEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void onBookRented(BookRentedEvent event) {
        String msg = "El usuario " + event.getUserId() + " ha prestado el libro " + event.getBookId() + " exitosamente.";
        notificationService.createNotification(msg);
    }
}
