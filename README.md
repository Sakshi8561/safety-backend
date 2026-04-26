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

## Concurrency Handling

* Redis `INCR` ensures atomic updates
* Prevents race condition in bot limit test (100 max)
## How to Run

1. Start Redis & PostgreSQL
2. Run Spring Boot app
3. Test APIs via Postman

## API Examples

POST /api/posts
POST /api/posts/{postId}/comments
POST /api/posts/{postId}/like
## Result

## Testing & Proof

The system was manually tested using Postman to verify all guardrail conditions defined in the assignment.

### 1. Bot Limit Test (Horizontal Cap)

* Sent more than 100 bot requests to the same post
* Result:

  * First 100 requests → allowed
  * 101st request → **rejected with "Bot limit reached" (HTTP 429)**
* Verified using Redis key:

  post:{postId}:bot_count = 100

### 2. Cooldown Test (Bot ↔ Human)

* Same bot attempted multiple interactions on the same user within 10 minutes
* Result:

  * First interaction → **allowed**
  * Second interaction → **blocked ("Cooldown active")**
* Verified using Redis key with TTL:

  cooldown:bot_{botId}:human_{userId}
### 3. Depth Limit Test (Vertical Cap)

* Sent request with `depthLevel > 20`
* Result:

  * Request rejected with **"Max depth reached"**
* Ensures no deep recursive comment threads


### 4. Notification System Test

* First bot interaction → **instant notification logged**
* Multiple interactions within 15 minutes → stored in Redis list:

  user:{userId}:pending_notifs
* Scheduler (runs every 5 minutes):

  * Aggregates notifications
  * Logs summarized message:
    Summarized Push Notification: Bot X and N others interacted
### 5. Concurrency Handling Test

* Simulated multiple bot requests on the same post
* Redis `INCR` operation ensured:

  * Atomic updates
  * No race condition
  * Maximum limit strictly enforced at 100

### Conclusion

All guardrails (Bot limit, Cooldown, Depth control, Notification batching) were successfully tested and validated using Redis-based atomic operations and TTL mechanisms.

* Successfully implemented all guardrails
* Handles concurrency and distributed state using Redis
