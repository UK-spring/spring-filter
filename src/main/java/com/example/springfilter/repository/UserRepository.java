package com.example.springfilter.repository;

import com.example.springfilter.common.exception.NotFoundException;
import com.example.springfilter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
    }
}
