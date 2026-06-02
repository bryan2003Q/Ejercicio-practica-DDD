package com.ejercicio.practica.modules.library.infrastructure.rest;

import com.ejercicio.practica.modules.library.application.BookDTO;
import com.ejercicio.practica.modules.library.application.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @PostMapping
    public BookDTO addBook(@RequestBody Map<String, String> body) {
        return libraryService.addBook(body.get("title"), body.get("author"));
    }

    @PostMapping("/{bookId}/rent")
    public ResponseEntity<String> rentBook(@PathVariable Long bookId, @RequestParam Long userId) {
        try {
            String result = libraryService.rentBook(bookId, userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
