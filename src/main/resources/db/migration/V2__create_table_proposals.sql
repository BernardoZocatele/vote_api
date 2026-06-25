CREATE TABLE proposals (
    id serial PRIMARY KEY,
    title varchar(255) NOT NULL,
    description varchar (255),
    creation_date TIMESTAMP NOT NULL,
    star_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    user_id INT NOT NULL,

    CONSTRAINT fk_user_proposal FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);