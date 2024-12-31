package com.fastcampus.springbootredis.controller;

import com.fastcampus.springbootredis.model.User;
import com.fastcampus.springbootredis.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final CacheService cacheService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return cacheService.getCachedData("user:" + id, User.class)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        cacheService.setCacheData("user:" + user.getId(), user, 3600);
        return ResponseEntity.ok(user);
    }
}
