package com.fastcampus.springbootredis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String LEADERBOARD_KEY = "game:leaderboard";

    public void addScore(String userId, double score) {
        redisTemplate.opsForZSet().add(LEADERBOARD_KEY, userId, score);
        log.info("Score added for user: {} with score: {}", userId, score);
    }

    public List<String> getTopPlayers(int count) {
        // 내림차순으로 데이터를 가져온다.
        Set<String> topScores = redisTemplate.opsForZSet()
                .reverseRange(LEADERBOARD_KEY, 0, count - 1);

        return new ArrayList<>(topScores != null ? topScores : Collections.emptySet());
    }

    public Long getUserRank(String userId) {
        return redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, userId);
    }

    public Double getUserScore(String userId) {
        return redisTemplate.opsForZSet().score(LEADERBOARD_KEY, userId);
    }

}
