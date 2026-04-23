package com.example.springfilter.repository;

import com.example.springfilter.common.exception.NotFoundException;
import com.example.springfilter.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }
}
