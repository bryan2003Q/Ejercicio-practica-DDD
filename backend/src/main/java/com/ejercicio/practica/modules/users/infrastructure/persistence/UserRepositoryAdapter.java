package com.ejercicio.practica.modules.users.infrastructure.persistence;

import com.ejercicio.practica.modules.users.domain.User;
import com.ejercicio.practica.modules.users.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    public UserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity jpaEntity = new UserJpaEntity(user.getId(), user.getName(), user.getEmail());
        UserJpaEntity savedEntity = springDataUserRepository.save(jpaEntity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataUserRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return springDataUserRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private User toDomain(UserJpaEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getEmail());
    }
}
