INSERT INTO `customers` (`first_name`, `surname`) VALUES ('jordan', 'harrison');
INSERT INTO `items` (`name`, `price`, `stock`) VALUES ('testItem', 123.45, 6);
INSERT INTO `orders` (`order_number`, `customer_id`, `order_total`) VALUES ('T35T', 1, 123.45);
INSERT INTO `order_line_items` (`item_id`, `quantity`, `order_id`) VALUES (1, 1, 1);