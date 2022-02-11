# Project Programming 2.3

This is a progressing project made by **Ruben Brouwers** for **Programming 2.3** in S2 2021-2022.

**Course**: Programming 2.3 <br>
**Name**: Ruben Brouwers <br>
**Email**: ruben.brouwers@student.kdg.be <br>
**ID**: 0146084-02 <br>
**Git Repository**: [prog23/Project](https://gitlab.com/BrouwersRuben/prog23/-/tree/main/Project)
<br>

I continued this project on the project that I made in programming 2.1.

## Domain explanation

Buildings can have multiple architects, architects can also have 0 or more buildings. There is also a many-to-one
relationship between building type and building, a building can only have 1 type, but multiple buildings can have the
same types.

<img src="src/main/resources/static/uml-diagram.png" alt="uml domain model" width="500"/>

## How to get it Running

- JDK 11
- H2 database
    - No need for PostGreSQL configuration
- [Landing page](http://localhost:6969)

# Weeks
## Week 1
### Architects
#### Getting all architects - 200 Ok
```http request
GET http://localhost:6969/api/architects HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 10 Feb 2022 23:53:05 GMT
Keep-Alive: timeout=60
Connection: keep-alive
[
  {
    "id": 1,
    "nameCompany": "Zaha Hadid Architects",
    "establishmentDate": "1980-01-01",
    "numberOfEmployees": 708,
    "buildings": [
      {
        "id": 1,
        "name": "Port Authority", ...
```
#### Getting architects with more than 300 employees - 200 Ok
```http request
GET http://localhost:6969/api/architects/300 HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Thu, 10 Feb 2022 23:54:57 GMT
Keep-Alive: timeout=60
Connection: keep-alive

[
  {
    "id": 1,
    "nameCompany": "Zaha Hadid Architects",
    "establishmentDate": "1980-01-01",
    "numberOfEmployees": 708,...
```
#### Removing architect with id 1 - 200 Ok
```http request
DELETE http://localhost:6969/api/architects/del/1 HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 200
Content-Length: 0
Date: Thu, 10 Feb 2022 23:57:23 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>
```
#### Getting architects with more than 300 employees - 204 No Content
```http request
GET http://localhost:6969/api/architects/900 HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 204 
Date: Thu, 10 Feb 2022 23:59:28 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>
```
#### Getting all architects - 404 Not Found
```http request
GET http://localhost:6969/api/architectss HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 404
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 11 Feb 2022 00:00:59 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "timestamp": "2022-02-11T00:00:59.864+00:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/architectss"
}
```
#### Getting architects with more than "Denmark" employees - 500 Internal Server Error
```http request
GET http://localhost:6969/api/architects/Denmark HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 500
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 11 Feb 2022 00:02:17 GMT
Connection: close

{
  "timestamp": "2022-02-11T00:02:17.965+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/api/architects/Denmark"
}
```
### Buildings
#### Getting all buildings - 200 Ok
```http request
GET http://localhost:6969/api/buildings HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 11 Feb 2022 00:04:11 GMT
Keep-Alive: timeout=60
Connection: keep-alive

[
  {
    "id": 1,
    "name": "Port Authority",
    "location": "Antwerp, Belgium", ...
```
#### Getting all buildings in Denmark - 200 Ok
```http request
GET http://localhost:6969/api/buildings/Denmark HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 11 Feb 2022 00:05:26 GMT
Keep-Alive: timeout=60
Connection: keep-alive

[
  {
    "id": 4,
    "name": "The Uno-X Petrol Station",
    "location": "Denmark", ...
```
#### Delete building with id 1 - 200 Ok
```http request
DELETE http://localhost:6969/api/buildings/del/1 HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 200
Content-Length: 0
Date: Fri, 11 Feb 2022 00:06:46 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>
```
#### Getting all buildings in Hoevenen - 204 No Content
```http request
GET http://localhost:6969/api/buildings/Hoevenen HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 204
Date: Fri, 11 Feb 2022 00:07:39 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>
```
#### Getting all buildings in 123 - 204 No Content
```http request
GET http://localhost:6969/api/buildings/123 HTTP/1.1
Accept: application/
```
```http request
HTTP/1.1 204
Date: Fri, 11 Feb 2022 00:08:08 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>
```
#### Getting all buildings - 404 Not Found
```http request
GET http://localhost:6969/api/buildingss HTTP/1.1
Accept: application/json
```
```http request
HTTP/1.1 404
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 11 Feb 2022 00:08:58 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "timestamp": "2022-02-11T00:08:58.423+00:00",
  "status": 404,
  "error": "Not Found",
  "path": "/api/buildingss"
}
```