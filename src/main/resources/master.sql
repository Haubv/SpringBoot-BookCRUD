INSERT INTO `testdb2`.`roles` (`name`)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER'),
       ('ROLE_AUTHOR'),
       ('ROLE_EDITOR');
INSERT INTO `testdb2`.`users` (`username`, `password`)
VALUES ('hau', '$2a$10$VObHxmxycf0.QJNXlwqEc.mFZ.iYkS5V4zAJ0xdIMSoEYoW18O7cu'); -- hau/123456
INSERT INTO `testdb2`.`user_roles` (`user_id`, `role_id`)
VALUES (1, 1);
INSERT INTO `testdb2`.`type_of_book` (`type_name`)
VALUES ('Tình cảm'),
       ('Kinh dị'),
       ('Khoa học viễn tưởng'),
       ('Trinh thám');