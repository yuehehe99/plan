DROP TABLE IF EXISTS `users`;

CREATE TABLE users
(
    `id`      bigint(20) NOT NULL auto_increment,
    `name`    VARCHAR(20) NOT NULL,
    `deleted` tinyint(1) NOT NULL DEFAULT '0',
    `gender`  tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

