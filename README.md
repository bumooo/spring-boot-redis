## Redis 실행
```bash
# 위치 이동
cd infrastructure

# docker-compose.yml 실행
docker compse up -d
```

### 실행 확인
```bash
# 실행중인 도커 컨테이너 조회
docker ps

# Redis CLI 접속
docker exec -it redis-practice redis-cli
```