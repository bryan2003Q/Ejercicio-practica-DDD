package com.ejercicio.practica.modules.library.infrastructure.persistence;

import com.ejercicio.practica.modules.library.domain.Book;
import com.ejercicio.practica.modules.library.domain.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookRepositoryAdapter implements BookRepository {

    private final SpringDataBookRepository springDataRepository;

    public BookRepositoryAdapter(SpringDataBookRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Book save(Book book) {
        BookJpaEntity entity = new BookJpaEntity(book.getId(), book.getTitle(), book.getAuthor(), book.getStatus(), book.getRentedByUserId());
        BookJpaEntity saved = springDataRepository.save(entity);
        return new Book(saved.getId(), saved.getTitle(), saved.getAuthor(), saved.getStatus(), saved.getRentedByUserId());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return springDataRepository.findById(id)
                .map(e -> new Book(e.getId(), e.getTitle(), e.getAuthor(), e.getStatus(), e.getRentedByUserId()));
    }

    @Override
    public List<Book> findAll() {
        return springDataRepository.findAll().stream()
                .map(e -> new Book(e.getId(), e.getTitle(), e.getAuthor(), e.getStatus(), e.getRentedByUserId()))
                .collect(Collectors.toList());
    }
}
