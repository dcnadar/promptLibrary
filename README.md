# Prompt Marketplace API

A full-stack backend application built with **Spring Boot**, **MongoDB**, and **AWS S3** for managing and selling AI-generated prompt documents. Features include user authentication with JWT, prompt upload and management, and secure file storage using AWS S3.

---

## API Base URL

- Local: `http://localhost:8080`
- Authentication APIs: `/auth`
- User, Prompt, Comment, Like APIs: `/api`

---

## Features

- User Signup/Login (JWT Auth)
- Upload & Manage Prompts
- Like/Unlike Prompts
- Comment on Prompts
- AWS S3 File Storage Integration
- MongoDB for data persistence
- JWT-secured endpoints

---

## Setup Instructions

### Prerequisites

- **Java 17 or higher** (Java 21 recommended)
- **Maven 3.8+**
- **MongoDB** (local or MongoDB Atlas)
- **AWS Account** with S3 bucket

### Clone the Repository

```bash
git clone https://github.com/dcnadar/promptLibrary.git
cd promptLibrary
```

### Configure `application.properties`

Edit `src/main/resources/application.properties` with:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/promptdb
spring.data.mongodb.auto-index-creation=true

# JWT Secret
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000 # 1 day in ms

# AWS S3 Config
aws.region.static=your-region
aws.access-key=your-access-key
aws.secret-key=your-secret-key
aws.s3.bucket=your-s3-bucket-name

# file handling
spring.servlet.multipart.enabled=true
```

---

## Running the Project

Run using Maven:

```bash
mvn spring-boot:run

./mvnw spring-boot:run
```

Or build and run:

```bash
mvn clean install
java -jar target/promptlibrary-0.0.1-SNAPSHOT.jar
```

---

## API Endpoints Overview

| Controller            | Endpoint(s)                                                                        | Method(s)         | Auth Required |
| --------------------- | ---------------------------------------------------------------------------------- | ----------------- | ------------- |
| **AuthController**    | `/auth/signup`, `/auth/login`                                                      | POST              | No Token      |
| **UserController**    | `/api/users/me`                                                                    | GET, PUT          | JWT Token     |
| **PromptController**  | `/api/prompts`, `/api/prompts/{promptId}`, `/api/users/me/prompts`                 | GET               | JWT Token     |
|                       | `/api/prompts`, `/api/prompts/{promptId}`, `/api/prompts/{promptId}`               | POST, PUT, DELETE | JWT Token     |
| **CommentController** | `/api/prompts/{promptId}/comments`, `/api/prompts/{promptId}/comments/{commentId}` | POST, GET, DELETE | JWT Token     |
| **LikeController**    | `/api/prompts/{promptId}/like`                                                     | POST, DELETE      | JWT Token     |

---

## Using Postman for Testing

### Step 1: Import Postman Collection

1. Open Postman.
2. Import the provided file: `PromptApp-Postman-Collection.json`.

### Step 2: Set Environment Variables

- `base_url`: `http://localhost:8080`
- `access_token`: Set this after login.`  
  NOTE- Open Collection Folder inside postman and set Authorization in collection so that no need to enter token in every endpoint.

### Step 3: Authentication

#### Signup

```http
POST /auth/signup
Content-Type: application/json

{
  "username": "johndoe",
  "password": "password123",
  "role": "BUYER" // or "SELLER"
}
```

#### Login

```http
POST /auth/login
Content-Type: application/json

{
  "username": "johndoe",
  "password": "password123"
}
```

Response:

```json
{
  "token": "your-jwt-token"
}
```

Use the token for authenticated requests:

```
Authorization: Bearer your-jwt-token
```

---

### Step 4: Example API Requests

#### Get Authenticated User

```http
GET /api/users/me
Authorization: Bearer your-jwt-token
```

#### Create a Prompt (SELLER)

```http
POST /api/prompts
Authorization: Bearer your-jwt-token
Content-Type: multipart/form-data

Fields:
- metadata: { "title": "Sample", "description": "Demo", "isPublic": true }
- file: [Upload prompt file]
```

#### Add Comment (BUYER)

```http
POST /api/prompts/{promptId}/comments
Authorization: Bearer your-jwt-token
Content-Type: application/json

{
  "text": "This is a great prompt!"
}
```

#### Like a Prompt (BUYER)

```http
POST /api/prompts/{promptId}/like
Authorization: Bearer your-jwt-token
```

---
