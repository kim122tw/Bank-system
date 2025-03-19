
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `created_at` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `balance` int NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` VALUES (3,'Alice Wang','alice@example.com','0912345678','2025-03-10 20:09:41','123','alice',490),
(4,'Bob Chen','bob@example.com','0987654321','2025-03-10 20:10:14','1234','bob',505),
(21,'Chang','kkim122tw@gmail.com','','2025-03-17 14:44:46.208841','1234','kim',505),
(22,'con','kkim122tw@gmail.com','','2025-03-17 18:36:26.206343','1234','root',0);


CREATE TABLE `transfers` (
  `transfer_id` bigint NOT NULL AUTO_INCREMENT,
  `from_account` bigint NOT NULL,
  `to_account` bigint NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transfer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `transfers` VALUES (15,3,21,5.00,'2025-03-18 12:41:02'),(16,3,4,5.00,'2025-03-18 12:51:49');

REATE TABLE `transactions` (
  `transaction_id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint NOT NULL,
  `transaction_type` varchar(255) NOT NULL,
  `amount` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/

INSERT INTO `transactions` VALUES (2,21,'DEPOSIT',500,'2025-03-17 06:44:46'),
(3,3,'DEPOSIT',500,'2025-03-17 06:44:46'),(4,4,'DEPOSIT',500,'2025-03-17 06:44:46'),
(5,16,'DEPOSIT',500,'2025-03-17 06:44:46'),(6,22,'DEPOSIT',0,'2025-03-17 10:36:26'),(35,3,'TRANSFERFROM',5,'2025-03-18 12:41:02'),
(36,21,'TRANSFERTO',5,'2025-03-18 12:41:02'),(37,3,'TRANSFERFROM',5,'2025-03-18 12:51:49'),(38,4,'TRANSFERTO',5,'2025-03-18 12:51:49');
