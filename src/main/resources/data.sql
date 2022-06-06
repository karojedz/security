INSERT INTO user (username, password, account_activated)
VALUES ('user', '{noop}user', true),
       ('admin', '{noop}admin', true);

INSERT INTO user_role (username, role)
VALUES ('user', 'ROLE_USER'),
       ('admin', 'ROLE_ADMIN');

INSERT INTO person (first_name, last_name, user_id)
VALUES ('Karolina', 'Jedziniak', 1),
       ('Szymon', 'Wilk', 2)