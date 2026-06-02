package com.ejercicio.practica.modules.library.domain;

public class Book {
    private final Long id;
    private final String title;
    private final String author;
    private String status;
    private Long rentedByUserId;

    public Book(Long id, String title, String author, String status, Long rentedByUserId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.rentedByUserId = rentedByUserId;
    }

    public void rent(Long userId) {
        if ("RENTED".equals(this.status)) {
            throw new IllegalStateException("El libro ya está prestado.");
        }
        this.status = "RENTED";
        this.rentedByUserId = userId;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getStatus() { return status; }
    public Long getRentedByUserId() { return rentedByUserId; }
}
