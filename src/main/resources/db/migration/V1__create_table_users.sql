CREATE TABLE users (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    cpf varchar(30) NOT NULL UNIQUE,
    password varchar(255) NOT NULL
);