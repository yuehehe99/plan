DROP TABLE IF EXISTS `todo`;

CREATE TABLE todo
(
    `id`         bigint(20) NOT NULL auto_increment,
    `users_id`   bigint(20) NOT NUll,
    `name`       VARCHAR(20) NOT NULL,
    `content`    VARCHAR(30),
    `type`       VARCHAR(30),
    `deleted`    tinyint(1) NOT NULL DEFAULT '0',
    `created_at` datetime    NOT NULL,
    `updated_at` datetime    NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT user_id_tasks FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)

) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;