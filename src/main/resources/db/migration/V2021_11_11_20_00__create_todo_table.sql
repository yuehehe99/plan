DROP TABLE IF EXISTS `todo`;

CREATE TABLE task
(
    `id`         bigint(20) NOT NULL auto_increment,
    `user_id`   bigint(20) NOT NUll,
    `name`       VARCHAR(20) NOT NULL,
    `content`    VARCHAR(30),
    `type`       VARCHAR(30),
    `deleted`    tinyint(1) NOT NULL DEFAULT '0',
    `created_at` datetime    NOT NULL,
    `updated_at` datetime    NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT user_id_task FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)

) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;