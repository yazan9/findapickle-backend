CREATE TABLE IF NOT EXISTS `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL UNIQUE,
  `name` varchar(100) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `quantity` varchar(20) DEFAULT NULL,
  `categories` varchar(100) DEFAULT NULL,
  `brands` varchar(100) DEFAULT NULL,
  `stores` varchar(100) DEFAULT NULL,
  `countries` varchar(100) DEFAULT NULL,
  `image_url` longtext DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8