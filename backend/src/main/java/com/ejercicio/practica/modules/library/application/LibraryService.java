package com.ejercicio.practica.modules.library.application;

import com.ejercicio.practica.modules.library.domain.Book;
import com.ejercicio.practica.modules.library.domain.BookRepository;
import com.ejercicio.practica.modules.users.domain.UserRepository;
import com.ejercicio.practica.shared.domain.events.BookRentedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository; // Interface de dominio de usuarios
    private final ApplicationEventPublisher eventPublisher;

    public LibraryService(BookRepository bookRepository, UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public BookDTO addBook(String title, String author) {
        Book book = new Book(null, title, author, "AVAILABLE", null);
        Book saved = bookRepository.save(book);
        return mapToDTO(saved);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public String rentBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));

        // Validamos que el usuario exista
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        book.rent(userId);
        bookRepository.save(book);

        // Disparamos el evento (Decoupling)
        eventPublisher.publishEvent(new BookRentedEvent(bookId, userId));

        return "Libro prestado correctamente";
    }

    private BookDTO mapToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.id = book.getId();
        dto.title = book.getTitle();
        dto.author = book.getAuthor();
        dto.status = book.getStatus();
        dto.rentedByUserId = book.getRentedByUserId();
        return dto;
    }
}
