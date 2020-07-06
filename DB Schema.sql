CREATE TABLE `customers` (
	`id` varchar(100) NOT NULL,
	`firstName` varchar(45) DEFAULT NULL,
	`lastName` varchar(45) DEFAULT NULL,
	`street1` varchar(45) DEFAULT NULL,
	`street2` varchar(45) DEFAULT NULL,
	`city` varchar(45) DEFAULT NULL,
	`state` varchar(45) DEFAULT NULL,
	`zip` varchar(45) DEFAULT NULL,
	`creditCardToken` varchar(128) DEFAULT NULL,
	`paymentAmount` decimal(10, 2) DEFAULT NULL,
	`paymentDay` int DEFAULT NULL,
	PRIMARY KEY (`id`)
);
CREATE TABLE `payments` (
	`id` varchar(100) NOT NULL,
	`customerId` varchar(45) DEFAULT NULL,
	`date` varchar(45) DEFAULT NULL,
	`amount` decimal(10, 2) DEFAULT NULL,
	PRIMARY KEY (`id`)
);