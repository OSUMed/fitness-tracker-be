-- SQL to create and populate the 'authority' table
CREATE TABLE IF NOT EXISTS authority (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_BLUE');
INSERT INTO authority (name) VALUES ('ROLE_RED');
INSERT INTO authority (name) VALUES ('ROLE_GREEN');

-- SQL to create and populate the 'users' table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- SQL to create the 'user_authorities' join table
CREATE TABLE IF NOT EXISTS user_authorities (
    user_id INT NOT NULL,
    authority_id INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_authority FOREIGN KEY (authority_id) REFERENCES authority(id)
);

