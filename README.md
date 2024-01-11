**Authentication via email using Spring boot**

**Description
**This is a basic project that shows how to authenticate and authorize users using Spring Boot and REST-API.
Users can sign up, by sending their credentials. After, the activation link is sent in response and also as a letter in the provided email.
By clicking the link, the account is activated with the standard role "USER" that allows visiting protected endpoint "localhost:8080/user". 

**Technologies used:**
* Spring (Boot, Security, Data)
* MySQL
* Maven


**Endpoints:**
* POST /register
* GET /register/activate?token=...
* GET /

protected
* GET /user - accounts with roles "USER" and "ADMIN" can visit
* GET /admin - accounts with the role "ADMIN" can visit


**Requirements to run the project:**
* Running MySQL docker container with database email_auth
* Replace email username and password for the account that will be sending email links in the file application.properties
