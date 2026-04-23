package com.example.springfilter.service;

import com.example.springfilter.common.exception.ForbiddenException;
import com.example.springfilter.dto.PostCreateRequestDto;
import com.example.springfilter.dto.PostResponseDto;
import com.example.springfilter.dto.PostUpdateRequestDto;
import com.example.springfilter.entity.Post;
import com.example.springfilter.entity.User;
import com.example.springfilter.repository.PostRepository;
import com.example.springfilter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto create(PostCreateRequestDto request, Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);

        Post post = new Post(request.getTitle(), request.getContents(), user);
        Post savedPost = postRepository.save(post);

        return new PostResponseDto(savedPost.getTitle(), savedPost.getContents(), user.getUsername());
    }

    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findByIdOrElseThrow(id);

        return new PostResponseDto(post.getTitle(), post.getContents(), post.getUser().getUsername());
    }

    @Transactional
    public void update(Long id, PostUpdateRequestDto request, Long userId) {
        Post post = postRepository.findByIdOrElseThrow(id);

        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("작성자만 수정할 수 있습니다.");
        }

        post.update(request.getTitle());
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Post post = postRepository.findByIdOrElseThrow(id);

        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}
