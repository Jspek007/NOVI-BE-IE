INSERT INTO `customers` (`entity_id`, `customer_id`, `email_address`, `first_name`, `insertion`, `last_name`, `password`, `phone_number`)
VALUES
    (1, 670271, 'Pietpost@gmail.com', 'Piet', 'de', 'Post', '$2a$10$GOlltM0k1IEZppU5/C6gTO0/WUnDfhcT9YQME5cZrmeTBttcoi0wa', '+31612345678'),
    (2, 378798, 'Klaasbroek@hotmail.com', 'Klaas', 'van de ', 'Broek', '$2a$10$zdAfHZECQ5NEDQtIWUUwXekS9kDRiBd2xFCn.2bacgOlJsZVpXE7O', '+31612345678'),
    (3, 495517, 'Dgordijn@hotmail.com', 'Dirk', '', 'Gordijn', '$2a$10$iHqGBYao8Ww8BUPcC185dOfJnaXq/dAlEoatjQK4CPM/087DydNm6', '+31612345678');

INSERT INTO `authorities` (`id`, `name`)
VALUES
    (4, 'CUSTOMERSERVICE'),
    (5, 'PRODUCTMANAGER'),
    (6, 'ADMIN');

INSERT INTO `app_users` (`id`, `name`, `password`, `username`)
VALUES
    (7, 'adminUser', '$2a$10$w.DX9QllVg.JV4C5koz0iO4E3gxHFI/ohrPUmWK86GfFhER2yJYWO', 'adminUser'),
    (8, 'customerService', '$2a$10$ep8lJWOFg3o/nVt7rxn7nOjQmjkrSbvuw.lwujq5XCL5yfz9Xigbu', 'customerService'),
    (9, 'productManager', '$2a$10$AsLKfNCYklZmOZbleXqLruGS4d/dmrTinczdjraC.StWNf8fKzLva', 'productManager');
INSERT INTO `app_users_roles` (`app_user_id`, `roles_id`)
VALUES
    (7, 6),
    (8, 4),
    (9, 5);
