INSERT INTO user (username, password, enabled)
VALUES ('user', '{noop}user', true),
       ('admin', '{noop}admin', true);

INSERT INTO user_role (username, role)
VALUES ('user', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN');

INSERT INTO person (first_name, last_name, user_id)
VALUES ('Karolina', 'Jedziniak', 2),
       ('Szymon', 'Wilk', 1)