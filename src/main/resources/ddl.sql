CREATE TABLE `User` (
	`key`	int	NOT NULL auto_increment PRIMARY KEY,
	`id`	varchar(100),
    `email`	varchar(100),
	`password`	varchar(100),
	`nick_name`	varchar(100),
	`point`	int,
	`platform`	varchar(100)
);
