package com.example.springfilter.controller;

import com.example.springfilter.common.Const;
import com.example.springfilter.dto.PostCreateRequestDto;
import com.example.springfilter.dto.PostResponseDto;
import com.example.springfilter.dto.PostUpdateRequestDto;
import com.example.springfilter.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> create(
            @RequestBody PostCreateRequestDto request,
            @SessionAttribute(Const.SESSION_KEY) Long userId
    ) {
        PostResponseDto response = postService.create(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        PostResponseDto response = postService.findById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody PostUpdateRequestDto request,
            @SessionAttribute(Const.SESSION_KEY) Long userId
    ) {
        postService.update(id, request, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @SessionAttribute(Const.SESSION_KEY) Long userId
    ) {
        postService.delete(id, userId);

        return ResponseEntity.ok().build();
    }
}
