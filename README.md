Authentication via email using Spring boot

Description
This is a basic project that shows how to authenticate and authorize users using Spring Boot and REST-api.
Users can sign up, sending their credentials. After, activation link is sent in response and also as a letter on provided email.
By clicking the link, the account is activated with standard role "USER" that allows visiting protected endpoint "localhost:8080/user". 

Thechnologies used:
Spring (Boot, Secutiry, Data)
MySQL
Maven

Endpoints:
POST /register
GET /register/activate?token=...
GET /

protected
GET /user - accounts with roles "USER", "ADMIN" can visit
GET /admin - accounts with role "ADMIN" can visit
