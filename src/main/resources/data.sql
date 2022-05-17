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
    (9, 'productManager', '$2a$10$AsLKfNCYklZmOZbleXqLruGS4d/dmrTinczdjraC.StWNf8fKzLva', 'productManager'),
    (10, 'Test user', '$2a$10$Fl0DCRxYjRsm6ztr75hJQejokGYMe54b9LU0S7F73GI7apDxXNeQK', 'test');

INSERT INTO `app_users_roles` (`app_user_id`, `roles_id`)
VALUES
    (7, 6),
    (8, 4),
    (9, 5),
    (10, 6);
INSERT INTO `addresses` (`id`, `addition`, `city`, `customer_address_type`, `customer_id`, `default_address`, `house_number`, `postal_code`, `street_name`, `parent_id`)
VALUES
    (1, 'a6', 'Poststad', 'shipping', 670271, 1, 12, '4112AL', 'Poststraat', 1),
    (2, 'a6', 'Poststad', 'billing', 670271, 1, 12, '4112AL', 'Poststraat', 1);
INSERT INTO `catalog_products` (`entity_id`, `created_at_date`, `enabled`, `product_description`, `product_price`, `product_title`, `sku`)
VALUES
    (1, '2022-04-29 15:33:52.000000', 1, 'De nieuwe jeans van dit moment', 64.99, 'Skinny jeans', 'sku_123456'),
    (2, '2022-04-29 15:34:13.000000', 1, 'Comfortabel en modieus', 120.99, 'Baggy jeans', 'sku_123341'),
    (3, '2022-04-29 15:34:36.000000', 0, 'Comfortabel en modieus', 74.99, 'Oversized tee', 'sku_123342');
INSERT INTO `categories` (`entity_id`, `category_description`, `category_name`)
VALUES
    (1, 'Get your new jeans now!', 'Jeans'),
    (2, 'Get your new tees now!', 'Tees');
INSERT INTO `catalog_category_products` (`product_id`, `category_id`)
VALUES
    (1, 1),
    (2, 1),
    (3, 2);
INSERT INTO `hibernate_sequence` (next_val)
VALUES
    (20);

