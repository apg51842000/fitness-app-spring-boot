# Fitness Tracker Microservices – Setup & Usage

This project is a Spring Boot–based microservices system that integrates with **PostgreSQL** and **Kafka** to manage users, activities, and AI-powered fitness recommendations.

---

## Prerequisites

- **Homebrew** (for macOS users)
- **PostgreSQL**
- **Kafka**
- **DBeaver** (or any PostgreSQL client)
- **Postman** (for API testing)

---

## Installation

1. **Install PostgreSQL**
   ```bash
   brew install postgres
   ```

2. **Install Kafka**
   ```bash
   brew install kafka
   ```

---

## Starting Services

1. Start PostgreSQL service:
   ```bash
   brew services start postgres
   ```

2. Start Kafka service:
   ```bash
   brew services start kafka
   ```

3. Run each microservice individually:
   - **User Service** → runs on port `8081`
   - **Activity Service** → runs on port `8082`
   - **AI Service** → runs on port `8083`
   - **Config Server** → runs on port `8888`

---

## Database Setup

1. Open **DBeaver** (or your SQL client of choice).
2. Create a database named:
   ```
   fitness_user_db
   ```
3. When the project runs successfully, the following tables will be created automatically:
   - `user_db` → Stores user data
   - `activity_db` → Stores user activities
   - `recommendation_db` → Stores AI-generated recommendations

---

## API Usage

### 1. Register a User
**POST** `http://localhost:8081/api/users/register`

Request body:
```json
{
  "email": "<user email>",
  "password": "<user password>",
  "firstName": "<user first name>",
  "lastName": "<user last name>"
}
```

✅ The user will be stored in the `user_db` table.

---

### 2. Add User Activities
**POST** `http://localhost:8082/api/activities`

Request body:
```json
{
  "userId": "<userId>",
  "activityType": "<Activity type>",
  "caloriesBurnt": "<calories burnt>",
  "duration": "<duration in hours>",
  "startTime": "yyyy-mm-ddThh:mm:ss"
}
```

Available activity types:
```
WALKING, RUNNING, SWIMMING, CYCLING, YOGA, HIIT, CARDIO, STRETCHING, OTHER
```

✅ Activities will be stored in the `activity_db` table.

---

### 3. Get User Activities
**GET** `http://localhost:8082/api/activities/{userId}`  

✅ Returns all activities for the given user.

---

### 4. AI Recommendations
Whenever an activity is added, it is sent to a Kafka topic.  
The **AI Service** consumes the event and generates a **recommendation & improvement**.

- **Get by Activity ID**  
  `http://localhost:8083/api/recommendations/{activityId}`  

- **Get Most Recent by User ID**  
  `http://localhost:8083/api/recommendations/{userId}`  

✅ Recommendations will be stored in the `recommendation_db` table.

---

## Summary

- Users are managed via **User Service (8081)**.
- Activities are tracked via **Activity Service (8082)**.
- AI recommendations are generated via **AI Service (8083)**.
- Configurations are managed by **Config Server (8888)**.
- Data persistence is handled by **PostgreSQL**.
- Event streaming is powered by **Kafka**.
