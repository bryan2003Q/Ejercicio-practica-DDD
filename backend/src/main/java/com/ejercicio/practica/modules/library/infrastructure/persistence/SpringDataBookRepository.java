package com.ejercicio.practica.modules.library.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataBookRepository extends JpaRepository<BookJpaEntity, Long> {
}
