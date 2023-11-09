CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(15) NOT NULL
);


CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    password VARCHAR(90) NOT NULL
);


CREATE TABLE user_role(
    id_user INT REFERENCES "user" (id),
    id_role INT REFERENCES role (id)
);

INSERT INTO "user" (username, email, password) VALUES ('admin', 'admin@gmail.com', '$2a$10$z58eXf0exfL1UKJRMvlpZea4KCP0IaNlxye.ILpuJwG9q98t2Dehm');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_USER');

INSERT INTO user_role (id_user, id_role) VALUES (1, 1);