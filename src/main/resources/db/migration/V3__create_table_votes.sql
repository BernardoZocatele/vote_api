CREATE TABLE votes(
    id serial PRIMARY KEY,
    user_id INT NOT NULL,
    proposal_id INT NOT NULL,
    vote varchar(10) NOT NULL,
    creation_date TIMESTAMP NOT NULL,

    CONSTRAINT fk_user_vote FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_proposal_vote FOREIGN KEY (proposal_id) REFERENCES proposals(id) ON DELETE CASCADE
);