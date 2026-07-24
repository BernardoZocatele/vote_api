# **Vote API** - Proposal and voting management system
**Vote Api** is a RESTFul solution developed using Java and Spring Boot for proposal management and real-time vote counting. The system have access control via JWT and decoupled business Validation Rules

## Features
### Authentication and Users
#### User Register 
Secure user registration with unique CPF and encrypted password via BCrypt.
```JSON
{
  "name": "string",
  "cpf": "12345678900",
  "password": "1234"
}
```
#### JWT Authentication 
Secure login generating access tokens for protected routes.
```JSON
{
  "cpf": "12345678900",
  "password": "1234"
}
```

### Proposal Management

#### Create Proposal
```JSON
{
  "title": "string",
  "description": "string",
  "start_date": "2026-07-22T17:26:59.742Z",
  "expiration_date": "2026-07-22T17:26:59.742Z"
}
```

### Business Rules
  * Prevents creating proposals with start dates in the past.
  * Prevents expiration dates earlier than the start date.
  * Only the proposal owner can edit or delete a proposal.
  * Proposals cannot be edited or deleted after receiving votes.
    
    
### Voting System
#### Cast Vote
```JSON
{
  "proposal_id": 0,
  "vote": "YES"
}
```
#### Vote Options

  * "YES" — Vote in favor

  * "NO" — Vote against

#### Vote validation
  * Each user can vote only once per proposal.

### Proposal Result
Only the proposal owner can view proposal results.
```Json
{
  "title": "Community Park Renovation",
  "percentYes": 75.0,
  "percentNo": 25.0,
  "winner": "YES",
  "totalVotes": 4,
  "status": "Open"
}
```
#### Winner Values
  * **"YES"** — Proposal approved
  * **"NO"** — Proposal rejected 
  * **"TIE"** — Equal number of "Yes" and "No" votes

##  API Endpoints

###  Authentication
| Method | Endpoint  | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| **POST** | `/auth/register` | Register a new user | No |
| **POST** | `/auth/login` | Authenticate and return JWT token | No |

###  Proposals
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| **POST** | `/proposal/create` | Create a new proposal | Yes (Bearer) |
| **PUT** | `/proposal/edit/{id}` | Edit a proposal (if no votes yet) | Yes (Bearer) |
| **DELETE** | `/proposal/delete/{id}` | Delete a proposal (if no votes yet) | Yes (Bearer) |
| **GET** | `/proposal/result/{id}` | Get proposal results and statistics | Yes (Bearer) |

### Votes
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :---: |
| **POST** | `/vote` | Cast a vote on a proposal | Yes (Bearer) |

## Architecture
```text
VOTE-API/
├── .mvn/
├── .vscode/
├── src/
│   ├── main/
│   │   ├── java/com/BernardoZocatele/vote_api/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── entity/
│   │   │   ├── infra/
│   │   │   │   ├── exception/
│   │   │   │   └── security/
│   │   │   ├── provider/
│   │   │   │   ├── proposal/
│   │   │   │   └── vote/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── VoteApiApplication.java
│   │   └── resources/
│   └── test/
├── .gitattributes
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```
## Prerequisites & Tools

Before running the application, make sure you have installed:

  * Java 17+ (JDK)

  * Maven 3.8+

  * PostgreSQL 14+

  * Git

## Technologies

  * Spring Security
  * JPA / Hibernate
  * Flyway
  * PostgreSQL
  * JWT
  * Lombok

## Setup

### Database Setup (PostgreSQL)

1. Open your terminal or PostgreSQL GUI (like pgAdmin or DBeaver) and log into PostgreSQL:

    `psql -U postgres`

2. Create the database named voteApi:

    `CREATE DATABASE "voteApi";`

* (Note: Database migrations and schema creation are handled automatically upon startup via Flyway)

### Security Environment Variable

* For production or local testing, it is recommended to export your custom JWT_SECRET key in your environment variables:

  * Linux / macOs:

  `export JWT_SECRET="your_custom_ultra_secret_key"`

  * Windows (PowerShell):
  
  `$env:JWT_SECRET="your_custom_ultra_secret_key"`

  * Windows (CMD):
  
  `set JWT_SECRET=your_custom_ultra_secret_key`

### Installation & Running

1. Clone the repository:

    `git clone https://github.com/BernardoZocatele/vote_api.git
cd vote_api`

2. Build the project with Maven:

    `mvn clean install`

3. Run the application:

    `mvn spring-boot:run`

3. Access API via Postman at: 

    * http://localhost:8080
