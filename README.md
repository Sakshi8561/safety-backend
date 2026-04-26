# Backend Engineering Assignment – Guardrail System

## Tech Stack

* Java 17 / Spring Boot 3
* PostgreSQL
* Redis
## Features Implemented

### Core APIs
* Create Post
* Add Comment
* Like Post

### Redis Guardrails

#### 1. Virality Score

* Bot Reply → +1
* Human Like → +20
* Human Comment → +50

#### 2. Horizontal Cap

* Max 100 bot replies per post
* Implemented using Redis `INCR` (atomic)

#### 3. Vertical Cap

* Max depth = 20

#### 4. Cooldown

* Bot ↔ Human interaction restricted for 10 minutes
* Redis TTL used
### Notification System

* First interaction → instant notification
* Next interactions → stored in Redis list
* Cron job (5 min) → sends summary
## Design Decisions

* Redis used for atomic operations (thread-safe)
* PostgreSQL used as source of truth
* Application kept stateless (no in-memory storage)

## 🔥 Concurrency Handling

* Redis `INCR` ensures atomic updates
* Prevents race condition in bot limit test (100 max)
## ▶️ How to Run

1. Start Redis & PostgreSQL
2. Run Spring Boot app
3. Test APIs via Postman

## 📬 API Examples

POST /api/posts
POST /api/posts/{postId}/comments
POST /api/posts/{postId}/like
## 🎯 Result

* Successfully implemented all guardrails
* Handles concurrency and distributed state using Redis
