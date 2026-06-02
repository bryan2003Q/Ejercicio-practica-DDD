package com.ejercicio.practica.modules.library.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String status;
    private Long rentedByUserId;

    public BookJpaEntity() {}

    public BookJpaEntity(Long id, String title, String author, String status, Long rentedByUserId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.rentedByUserId = rentedByUserId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getRentedByUserId() { return rentedByUserId; }
    public void setRentedByUserId(Long rentedByUserId) { this.rentedByUserId = rentedByUserId; }
}
