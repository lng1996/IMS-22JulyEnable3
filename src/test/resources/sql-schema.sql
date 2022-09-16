SET GLOBAL time_zone = '+00:00';

DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `items`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `order_line_items`;


CREATE TABLE IF NOT EXISTS `customers` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(40) DEFAULT NULL,
    `surname` VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `items` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(40) NOT NULL UNIQUE,
    `price` DECIMAL(7, 2) DEFAULT 0.00,
    `stock` INT DEFAULT 0,
    PRIMARY KEY (`id`)
    
);

ALTER TABLE `items` AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS `orders` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`order_number` VARCHAR(16) UNIQUE,
	`customer_id` INT,
    `order_total` DECIMAL(8, 2) DEFAULT 0.00,
	PRIMARY KEY (`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `order_line_items` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `item_id` INT,
    `quantity` INT DEFAULT 0,
    `order_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`item_id`) REFERENCES `items` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
);