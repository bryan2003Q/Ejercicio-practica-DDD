package com.ejercicio.practica.shared.domain.events;

public class BookRentedEvent {
    private final Long bookId;
    private final Long userId;

    public BookRentedEvent(Long bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    public Long getBookId() { return bookId; }
    public Long getUserId() { return userId; }
}
