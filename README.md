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

```
http Request
POST /auth/signup

{
  "username": "seller1",
  "password": "password123",
  "email":"seller1@example.com",
  "role": "SELLER"
},
{
  "username": "buyer1",
  "password": "password123",
  "email": "buyer1@example.com",
  "role": "buyer"
}
```

```
http Response
{
    "statusMsg": "Registration Successfull"
}
```

#### Login

```
http Request
POST /auth/login

{
  "username": "seller1",
  "password": "password123"
}
```

```
http Response

{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZWxsZXIxIiwiaWF0IjoxNzUzNjcyMjUyLCJleHAiOjE3NTM2NzMxNTJ9.FNyj5GeBTa7LsfuGfNhciZ223Zmh_HDWyyD_9OItYLc",
    "user": {
        "id": "6886e9c2814dd802842f80b2",
        "username": "seller1",
        "email": "seller1@example.com",
        "password": null,
        "role": "SELLER"
    }
}
```

Use the token for authenticated requests:

```
Authorization: Bearer your-jwt-token
```

---

### Step 4: Example API Requests

#### Get Authenticated User

```
http Request
GET /api/users/me
Authorization: Bearer your-jwt-token
```

```
http Response
{
    "statusMsg": "Authenticated User Details",
    "userDTO": {
        "id": "6886e9c2814dd802842f80b2",
        "username": "seller1",
        "email": "seller1@example.com",
        "password": null,
        "role": "SELLER"
    }
}
```

#### Update User details (SELLER)

```
http Request
PUT  /api/users/me
Authorization: Bearer your-jwt-token

{
  "email": "seller1update@example.com"
}
```

```
http Response
{
    "statusMsg": "Updated User Details",
    "userDTO": {
        "id": "6886e9c2814dd802842f80b2",
        "username": "seller1",
        "email": "seller1update@example.com",
        "password": null,
        "role": "SELLER"
    }
}
```

#### Create a Prompt (SELLER)

```
http Request
POST /api/prompts
Authorization: Bearer your-jwt-token
Content-Type: multipart/form-data

Fields:
- metadata: {"title":"new four ","description":"Resume","price":0.99,"category":"Interview","contentType":"PDF","tags":["learn","ai"]}

- file: [Upload prompt file]
```

```
http Response
{
    "statusMsg": "Prompt Creation Success",
    "dto": {
        "promptId": "6886ecc10f22b85a4d82c7fb",
        "title": "new four ",
        "description": "Resume",
        "price": 0.99,
        "category": "Interview",
        "contentType": "PDF",
        "tags": [
            "learn",
            "ai"
        ],
        "createdBy": "6886e9c2814dd802842f80b2",
        "s3Key": "prompts/b45635f5-1062-412f-868e-d17b28cdcccd-DeepakResume0.pdf",
        "likesCount": 0,
        "createdAt": "2025-07-28T03:21:37.816+00:00",
        "updatedAt": "2025-07-28T03:21:37.816+00:00",
        "fileUrl": "https://promp-lib-bucket.s3.amazonaws.com/prompts/b45635f5-1062-412f-868e-d17b28cdcccd-DeepakResume0.pdf"
    }
}
```

#### Update a Prompt (SELLER)

```
http Request
PUT api/prompts/{promptId}
Authorization: Bearer your-jwt-token

Fields:
- metadata: {"title":"Updated prompt title"}
-file : [Update prompt file ] optional
```

```
http Response
{
    "statusMsg": "Prompt Updated Successfully",
    "dto": {
        "promptId": "6886ecc10f22b85a4d82c7fb",
        "title": "Updated prompt title",
        "description": "Resume",
        "price": 0.99,
        "category": "Interview",
        "contentType": "PDF",
        "tags": [
            "learn",
            "ai"
        ],
        "createdBy": "6886e9c2814dd802842f80b2",
        "s3Key": "prompts/9c747fd1-08d5-4562-bb5b-6637e2f6504d-1745323236.pdf",
        "likesCount": 0,
        "createdAt": "2025-07-28T03:21:37.816+00:00",
        "updatedAt": "2025-07-28T03:27:43.410+00:00",
        "fileUrl": "https://promp-lib-bucket.s3.amazonaws.com/prompts/9c747fd1-08d5-4562-bb5b-6637e2f6504d-1745323236.pdf"
    }
}
```

#### Delete a Prompt (SELLER)

```
http Request
DELETE api/prompts/{promptId}
Authorization: Bearer your-jwt-token
```

```
http Response
{
    "statusMsg": "Prompt Deleted Successfully"
}
```

#### Get User Prompts (SELLER) LIST

```
http Request
GET /api/users/me/prompts
Authorization: Bearer your-jwt-token
```

```
http Response - LIST OF ALL PROMPTS CREATED BY USER-
{
    "statusMsg": "All User Prompts Fetched",
    "dtoList": [
        {
            "promptId": "6886eeeb0f22b85a4d82c7fc",
            "title": "new four ",
            "description": "Resume",
            "price": 0.99,
            "category": "Interview",
            "contentType": "PDF",
            "tags": [
                "learn",
                "ai"
            ],
            "createdBy": "6886e9c2814dd802842f80b2",
            "s3Key": "prompts/09c44a58-17f8-4302-a16d-ce62e3777e30-DeepakResume0.pdf",
            "likesCount": 0,
            "createdAt": "2025-07-28T03:30:51.509+00:00",
            "updatedAt": "2025-07-28T03:30:51.509+00:00",
            "fileUrl": null
        },
        {
            "promptId": "6886efb70f22b85a4d82c7fd",
            "title": "Second Prompt ",
            "description": "CV",
            "price": 223.99,
            "category": "JOB",
            "contentType": "PDF",
            "tags": [
                "job",
                "spring"
            ],
            "createdBy": "6886e9c2814dd802842f80b2",
            "s3Key": "prompts/08bed09b-ca05-4e66-83da-00b473dac40b-DeepakResume0.pdf",
            "likesCount": 0,
            "createdAt": "2025-07-28T03:34:15.848+00:00",
            "updatedAt": "2025-07-28T03:34:15.848+00:00",
            "fileUrl": null
        }
    ]
}
```

#### Get Prompt by Id

```
http Request
GET api/prompts/{promptId}
```

```
http Response
{
    "statusMsg": "Request Success",
    "dto": {
        "promptId": "6886eeeb0f22b85a4d82c7fc",
        "title": "new four ",
        "description": "Resume",
        "price": 0.99,
        "category": "Interview",
        "contentType": "PDF",
        "tags": [
            "learn",
            "ai"
        ],
        "createdBy": "6886e9c2814dd802842f80b2",
        "s3Key": "prompts/09c44a58-17f8-4302-a16d-ce62e3777e30-DeepakResume0.pdf",
        "likesCount": 0,
        "createdAt": "2025-07-28T03:30:51.509+00:00",
        "updatedAt": "2025-07-28T03:30:51.509+00:00",
        "fileUrl": "https://promp-lib-bucket.s3.amazonaws.com/prompts/09c44a58-17f8-4302-a16d-ce62e3777e30-DeepakResume0.pdf"
    }
}
```

```
Error Response
{
    "status": 404,
    "message": "Prompt not found",
    "timestamp": "2025-07-28T09:08:21.840572"
}
```

#### Get all public Prompts

```
http Request
GET /api/prompts
```

```
http Response
{
    "statusMsg": "Prompt List",
    "dtoList": [
        {
            "promptId": "6886eeeb0f22b85a4d82c7fc",
            "title": "new four ",
            "description": "Resume",
            "price": 0.99,
            "category": "Interview",
            "contentType": "PDF",
            "tags": [
                "learn",
                "ai"
            ],
            "createdBy": "6886e9c2814dd802842f80b2",
            "s3Key": "prompts/09c44a58-17f8-4302-a16d-ce62e3777e30-DeepakResume0.pdf",
            "likesCount": 0,
            "createdAt": "2025-07-28T03:30:51.509+00:00",
            "updatedAt": "2025-07-28T03:30:51.509+00:00",
            "fileUrl": null
        },
        {
            "promptId": "6886efb70f22b85a4d82c7fd",
            "title": "Second Prompt ",
            "description": "CV",
            "price": 223.99,
            "category": "JOB",
            "contentType": "PDF",
            "tags": [
                "job",
                "spring"
            ],
            "createdBy": "6886e9c2814dd802842f80b2",
            "s3Key": "prompts/08bed09b-ca05-4e66-83da-00b473dac40b-DeepakResume0.pdf",
            "likesCount": 0,
            "createdAt": "2025-07-28T03:34:15.848+00:00",
            "updatedAt": "2025-07-28T03:34:15.848+00:00",
            "fileUrl": null
        }
    ]
}
```

#### Add Comment (BUYER)

```
http Request
POST /api/prompts/{promptId}/comments
Authorization: Bearer your-jwt-token

{
  "text": "This is a great prompt!"
}
```

```
http Response
{
    "message": "Comment Added"
}
```

```error Response
{
    "status": 404,
    "message": "Prompt not found",
    "timestamp": "2025-07-28T09:16:24.082048"
}
```

### Delete a Comment (BUYER, SELLER)

```
http Request
DELETE /api/prompts/{promptId}/comments/{commentId}
Authorization: Bearer your-jwt-token
```

```
http Response
{
    "message": "Comment Deleted"
}
```

```Error Respones
{
    "status": 404,
    "message": "Comment not found",
    "timestamp": "2025-07-28T09:18:34.346241"
}
```

### GET all comments on a Prompt

```
http Request
GET /api/paompts/{promptId}/comments
```

```
http Response
{
    "message": "List of comments on prompt (6886eeeb0f22b85a4d82c7fc):",
    "commentDTOlist": [
        {
            "id": "6886f26a40fc886544eb994c",
            "promptId": "6886eeeb0f22b85a4d82c7fc",
            "userId": "6886e9f2814dd802842f80b3",
            "comment": "This is a great prompt!",
            "createdAt": "2025-07-28T03:45:46.242+00:00"
        },
        {
            "id": "6886f39e3b5910cc908b4b87",
            "promptId": "6886eeeb0f22b85a4d82c7fc",
            "userId": "6886e9f2814dd802842f80b3",
            "comment": "This is not a great prompt!",
            "createdAt": "2025-07-28T03:50:54.574+00:00"
        },
        {
            "id": "6886f3ab3b5910cc908b4b88",
            "promptId": "6886eeeb0f22b85a4d82c7fc",
            "userId": "6886e9f2814dd802842f80b3",
            "comment": "This is not a good takeaway",
            "createdAt": "2025-07-28T03:51:07.034+00:00"
        }
    ]
}
```

#### Like a Prompt (BUYER)

```
http Request
POST /api/prompts/{promptId}/like
Authorization: Bearer your-jwt-token
```

```
http Response
{
    "message": "Liked"
}
```

```
error Response
{
    "status": 404,
    "message": "Prompt not found",
    "timestamp": "2025-07-28T09:26:17.714242"
}
```

#### Unlike a Prompt (BUYER)

```
http Request
DELETE /api/prompts/{promptId}/like
Authorization: Bearer your-jwt-token
```

```
http Response
{
    "message": "Unliked"
}
```
