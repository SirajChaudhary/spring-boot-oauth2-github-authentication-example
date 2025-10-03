# Spring Boot GitHub OAuth2 Authentication Example

## Overview

This is a **Spring Boot 3 / Spring Security 6** demo project that demonstrates:

- Authenticating users using **GitHub OAuth2 login**.
- Generating **our own stateless JWT** after successful GitHub login to secure REST APIs.
- Exposing CRUD APIs for **Employee** with fields: `id`, `name`, `designation`, `salary`.
- Integrating **Swagger UI** to explore and test APIs with JWT authentication.

> **Why we generate our own JWT:**  
> GitHub OAuth2 issues access tokens for browser-based authentication, but these tokens are **not JWTs** and cannot be directly validated by Spring Security’s stateless JWT Resource Server.  
> To secure our APIs statelessly and decouple them from GitHub, we generate a signed JWT locally after successful login.

---

## Features

- OAuth2 login using GitHub as the Authorization Server.
- Stateless JWT-based authentication for API access.
- Employee CRUD APIs:
  - `GET /api/v1/employees` → Get all employees
  - `GET /api/v1/employees/{id}` → Get employee by ID
  - `POST /api/v1/employees` → Create employee
  - `PUT /api/v1/employees/{id}` → Update employee
  - `DELETE /api/v1/employees/{id}` → Delete employee
- In-memory storage for simplicity (no database required).
- Swagger UI integration with JWT support.

---

## Steps to Get GitHub Client ID and Secret

1. Go to **[GitHub Developer Settings → OAuth Apps](https://github.com/settings/developers)**  
2. Click **“New OAuth App”**.  
3. Fill in the fields:
   - **Application Name:** anything you like  
   - **Homepage URL:** `http://localhost:8080`  
   - **Authorization Callback URL:** `http://localhost:8080/login/oauth2/code/github`  
4. Click **Register application**.  
5. Copy the **Client ID** and **Client Secret** into your `application.properties`:

```properties
spring.security.oauth2.client.registration.github.client-id=YOUR_GITHUB_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_GITHUB_CLIENT_SECRET
```

---

## Usage Flow

1. Start the application:

```bash
mvn spring-boot:run
```

2. Open your browser and go to:

```
http://localhost:8080/oauth2/authorization/github
```

3. Login using GitHub credentials.  
4. After successful login, you are redirected to:

```
/api/v1/auth/success
```

5. The response contains your **stateless JWT**:

```json
{
  "message": "Login successful",
  "jwt": "<YOUR_JWT_TOKEN>",
  "user": { ... GitHub user info ... }
}
```

6. Copy the JWT and include it in the **Authorization header** for API calls:

```
Authorization: Bearer <YOUR_JWT_TOKEN>
```

---

## Accessing Employee APIs

All `/api/v1/employees/**` endpoints are protected and require the JWT token.

Example using `curl`:

```bash
curl -H "Authorization: Bearer <YOUR_JWT_TOKEN>" http://localhost:8080/api/v1/employees
```

---

## Swagger UI

1. Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

2. Click the **Authorize** button (lock icon) in Swagger.  
3. Paste your **JWT token** and click **Authorize**.  
4. You can now test all Employee APIs directly from Swagger UI.

---

## Project Structure

```
spring-boot-github-oauth2-authentication-example/
├─ src/main/java/com/example/demo/
│  ├─ DemoApplication.java
│  ├─ config/
│  │  ├─ SecurityConfig.java
│  │  ├─ OpenApiConfig.java
│  │  └─ SwaggerSecurityConfig.java
│  ├─ controller/
│  │  ├─ AuthController.java
│  │  └─ EmployeeController.java
│  └─ dto/
│     └─ Employee.java
│  └─ util/
│     └─ JwtUtil.java
└─ resources/application.properties
```

---

## Notes

- This project demonstrates **how to use GitHub as an OAuth2 authorization server** and secure your own REST APIs with stateless JWTs.  
- Using our own JWT allows **stateless API authorization**, avoiding reliance on GitHub access tokens for every API request.
- **JWT secret key** is stored in `JwtUtil.java`. In production, store it securely.
- In-memory storage is used for Employees; no database is required.

---

## Dependencies

- Spring Boot 3  
- Spring Security 6  
- Springdoc OpenAPI 2.x (Swagger UI)  
- JJWT 0.11.5  

---

## License

Free Software, by [Siraj Chaudhary](https://www.linkedin.com/in/sirajchaudhary/) 
