CREATE TABLE IF NOT EXISTS `User` (
    `id` bigint(20) NOT NULL auto_increment,
    `user_name` varchar(200) NOT NULL,
    `password` varchar(200) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `Topic`(
	`id` bigint(20) NOT NULL auto_increment,
	`title` varchar(200) NOT NULL,
	`description` varchar(200) NOT NULL,
	`creation_date` datetime,
	`user_id` bigint(20) NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT FK_USER_TOPIC FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
);

CREATE TABLE IF NOT EXISTS `Comment`(
	`id` bigint(20) NOT NULL auto_increment,
	`description` varchar(255) NOT NULL,
	`creation_date` datetime,
	`topic_id` bigint(20) NOT NULL,
	`user_id` bigint(20) NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT FK_COMMENT_TOPIC FOREIGN KEY (`topic_id`) REFERENCES `Topic`(`id`),
	CONSTRAINT FK_COMMENT_USER FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);