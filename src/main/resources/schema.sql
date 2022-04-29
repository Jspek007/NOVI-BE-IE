SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses`
(
    `id`                    bigint NOT NULL AUTO_INCREMENT,
    `addition`              varchar(255) DEFAULT NULL,
    `city`                  varchar(255) DEFAULT NULL,
    `customer_address_type` varchar(255) DEFAULT NULL,
    `customer_id`           bigint       DEFAULT NULL,
    `default_address`       bit(1) NOT NULL,
    `house_number`          int    NOT NULL,
    `postal_code`           varchar(255) DEFAULT NULL,
    `street_name`           varchar(255) DEFAULT NULL,
    `parent_id`             bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `app_users`;
CREATE TABLE `app_users`
(
    `id`       bigint NOT NULL,
    `name`     varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `username` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities`
(
    `id`   bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `app_users_roles`;
CREATE TABLE `app_users_roles`
(
    `app_user_id` bigint NOT NULL,
    `roles_id`    bigint NOT NULL,
    KEY `FK3mipnf4me46mfctw8orng0u3i` (`roles_id`),
    KEY `FKjbe8lt8c1um2wc23xniiakyuf` (`app_user_id`),
    CONSTRAINT `FK3mipnf4me46mfctw8orng0u3i` FOREIGN KEY (`roles_id`) REFERENCES `authorities` (`id`),
    CONSTRAINT `FKjbe8lt8c1um2wc23xniiakyuf` FOREIGN KEY (`app_user_id`) REFERENCES `app_users` (`id`)
);

DROP TABLE IF EXISTS `catalog_products`;
CREATE TABLE `catalog_products`
(
    `entity_id`           bigint NOT NULL AUTO_INCREMENT,
    `created_at_date`     datetime(6)  DEFAULT NULL,
    `enabled`             bit(1) NOT NULL,
    `product_description` varchar(255) DEFAULT NULL,
    `product_price`       double NOT NULL,
    `product_title`       varchar(255) DEFAULT NULL,
    `sku`                 varchar(255) DEFAULT NULL,
    PRIMARY KEY (`entity_id`)
);

DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`
(
    `entity_id`            bigint NOT NULL AUTO_INCREMENT,
    `category_description` varchar(255) DEFAULT NULL,
    `category_name`        varchar(255) DEFAULT NULL,
    PRIMARY KEY (`entity_id`)
);

DROP TABLE IF EXISTS `catalog_category_products`;
CREATE TABLE `catalog_category_products`
(
    `product_id`  bigint NOT NULL,
    `category_id` bigint NOT NULL,
    KEY `FK3v1fsjkyog8uorwhstpggx0ct` (`category_id`),
    KEY `FK76jxs9ncg5gj6hp9fsino2egh` (`product_id`),
    CONSTRAINT `FK3v1fsjkyog8uorwhstpggx0ct` FOREIGN KEY (`category_id`) REFERENCES `categories` (`entity_id`),
    CONSTRAINT `FK76jxs9ncg5gj6hp9fsino2egh` FOREIGN KEY (`product_id`) REFERENCES `catalog_products` (`entity_id`)
);

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers`
(
    `entity_id`     bigint NOT NULL AUTO_INCREMENT,
    `customer_id`   bigint       DEFAULT NULL,
    `email_address` varchar(255) DEFAULT NULL,
    `first_name`    varchar(255) DEFAULT NULL,
    `insertion`     varchar(255) DEFAULT NULL,
    `last_name`     varchar(255) DEFAULT NULL,
    `password`      varchar(255) DEFAULT NULL,
    `phone_number`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`entity_id`)
);

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`
(
    `next_val` bigint DEFAULT NULL
);

DROP TABLE IF EXISTS `product_media_gallery`;
CREATE TABLE `product_media_gallery`
(
    `id`        bigint NOT NULL AUTO_INCREMENT,
    `data`      longblob,
    `file_name` varchar(255) DEFAULT NULL,
    `parent_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK5fw9wphgyculx2xef13uox7j4` (`parent_id`),
    CONSTRAINT `FK5fw9wphgyculx2xef13uox7j4` FOREIGN KEY (`parent_id`) REFERENCES `catalog_products` (`entity_id`)
);

DROP TABLE IF EXISTS `sales_orders`;
CREATE TABLE `sales_orders`
(
    `entity_id`                     bigint NOT NULL AUTO_INCREMENT,
    `billing_address_addition`      varchar(255) DEFAULT NULL,
    `billing_address_city`          varchar(255) DEFAULT NULL,
    `billing_address_house_number`  int    NOT NULL,
    `billing_address_postal_code`   varchar(255) DEFAULT NULL,
    `billing_address_street`        varchar(255) DEFAULT NULL,
    `created_at_date`               datetime(6)  DEFAULT NULL,
    `customer_email`                varchar(255) DEFAULT NULL,
    `customer_first_name`           varchar(255) DEFAULT NULL,
    `customer_id`                   bigint       DEFAULT NULL,
    `customer_insertion`            varchar(255) DEFAULT NULL,
    `customer_last_name`            varchar(255) DEFAULT NULL,
    `customer_phone_number`         varchar(255) DEFAULT NULL,
    `shipping_address_addition`     varchar(255) DEFAULT NULL,
    `shipping_address_city`         varchar(255) DEFAULT NULL,
    `shipping_address_house_number` int    NOT NULL,
    `shipping_address_postal_code`  varchar(255) DEFAULT NULL,
    `shipping_address_street`       varchar(255) DEFAULT NULL,
    `amount_paid`                   double NOT NULL,
    `amount_refunded`               double NOT NULL,
    `grand_total`                   double       DEFAULT NULL,
    `sales_order_items_total`       int          DEFAULT NULL,
    PRIMARY KEY (`entity_id`)
);

DROP TABLE IF EXISTS `sales_order_items`;
CREATE TABLE `sales_order_items`
(
    `entity_id`       bigint NOT NULL AUTO_INCREMENT,
    `product_price`   double NOT NULL,
    `product_title`   varchar(255) DEFAULT NULL,
    `sku`             varchar(255) DEFAULT NULL,
    `order_entity_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`entity_id`),
    KEY `FKdtoiu7vnmywpfulevjj3jcv64` (`order_entity_id`),
    CONSTRAINT `FKdtoiu7vnmywpfulevjj3jcv64` FOREIGN KEY (`order_entity_id`) REFERENCES `sales_orders` (`entity_id`)
);

DROP TABLE IF EXISTS `sales_order_payments`;
CREATE TABLE `sales_order_payments`
(
    `entity_id`             bigint NOT NULL AUTO_INCREMENT,
    `payment_amount`        double NOT NULL,
    `sales_order_entity_id` bigint DEFAULT NULL,
    PRIMARY KEY (`entity_id`),
    KEY `FKmydpg4c1d8kx61n9q40aty33t` (`sales_order_entity_id`),
    CONSTRAINT `FKmydpg4c1d8kx61n9q40aty33t` FOREIGN KEY (`sales_order_entity_id`) REFERENCES `sales_orders` (`entity_id`)
);

DROP TABLE IF EXISTS `sales_creditmemo`;
CREATE TABLE `sales_creditmemo`
(
    `entity_id`                     bigint NOT NULL AUTO_INCREMENT,
    `billing_address_addition`      varchar(255) DEFAULT NULL,
    `billing_address_city`          varchar(255) DEFAULT NULL,
    `billing_address_house_number`  int    NOT NULL,
    `billing_address_postal_code`   varchar(255) DEFAULT NULL,
    `billing_address_street`        varchar(255) DEFAULT NULL,
    `created_at_date`               datetime(6)  DEFAULT NULL,
    `customer_email`                varchar(255) DEFAULT NULL,
    `customer_first_name`           varchar(255) DEFAULT NULL,
    `customer_id`                   bigint       DEFAULT NULL,
    `customer_insertion`            varchar(255) DEFAULT NULL,
    `customer_last_name`            varchar(255) DEFAULT NULL,
    `customer_phone_number`         varchar(255) DEFAULT NULL,
    `shipping_address_addition`     varchar(255) DEFAULT NULL,
    `shipping_address_city`         varchar(255) DEFAULT NULL,
    `shipping_address_house_number` int    NOT NULL,
    `shipping_address_postal_code`  varchar(255) DEFAULT NULL,
    `shipping_address_street`       varchar(255) DEFAULT NULL,
    `amount_refunded`               double NOT NULL,
    `sales_order_entity_id`         bigint       DEFAULT NULL,
    PRIMARY KEY (`entity_id`),
    KEY `FKl0iibtrism518yyww91wqr4gv` (`sales_order_entity_id`),
    CONSTRAINT `FKl0iibtrism518yyww91wqr4gv` FOREIGN KEY (`sales_order_entity_id`) REFERENCES `sales_orders` (`entity_id`)
);

DROP TABLE IF EXISTS `sales_creditmemo_items`;
CREATE TABLE `sales_creditmemo_items`
(
    `entity_id`            bigint NOT NULL AUTO_INCREMENT,
    `product_price`        double NOT NULL,
    `product_title`        varchar(255) DEFAULT NULL,
    `sku`                  varchar(255) DEFAULT NULL,
    `creditmemo_entity_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`entity_id`),
    KEY `FKmyh7bqu6gc3pnqeol1sxttcrs` (`creditmemo_entity_id`),
    CONSTRAINT `FKmyh7bqu6gc3pnqeol1sxttcrs` FOREIGN KEY (`creditmemo_entity_id`) REFERENCES `sales_creditmemo` (`entity_id`)
);

DROP TABLE IF EXISTS `sales_invoices`;
CREATE TABLE `sales_invoices`
(
    `entity_id`                     bigint NOT NULL AUTO_INCREMENT,
    `billing_address_addition`      varchar(255) DEFAULT NULL,
    `billing_address_city`          varchar(255) DEFAULT NULL,
    `billing_address_house_number`  int    NOT NULL,
    `billing_address_postal_code`   varchar(255) DEFAULT NULL,
    `billing_address_street`        varchar(255) DEFAULT NULL,
    `created_at_date`               datetime(6)  DEFAULT NULL,
    `customer_email`                varchar(255) DEFAULT NULL,
    `customer_first_name`           varchar(255) DEFAULT NULL,
    `customer_id`                   bigint       DEFAULT NULL,
    `customer_insertion`            varchar(255) DEFAULT NULL,
    `customer_last_name`            varchar(255) DEFAULT NULL,
    `customer_phone_number`         varchar(255) DEFAULT NULL,
    `shipping_address_addition`     varchar(255) DEFAULT NULL,
    `shipping_address_city`         varchar(255) DEFAULT NULL,
    `shipping_address_house_number` int    NOT NULL,
    `shipping_address_postal_code`  varchar(255) DEFAULT NULL,
    `shipping_address_street`       varchar(255) DEFAULT NULL,
    `grand_total`                   double NOT NULL,
    `sales_order_entity_id`         bigint       DEFAULT NULL,
    PRIMARY KEY (`entity_id`),
    KEY `FKjobifb2nthitdxjqmrln8rcaq` (`sales_order_entity_id`),
    CONSTRAINT `FKjobifb2nthitdxjqmrln8rcaq` FOREIGN KEY (`sales_order_entity_id`) REFERENCES `sales_orders` (`entity_id`)
);
SET FOREIGN_KEY_CHECKS=1;