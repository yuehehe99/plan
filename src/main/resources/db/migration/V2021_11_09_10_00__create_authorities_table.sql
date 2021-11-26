DROP TABLE IF EXISTS `authorities`;

CREATE TABLE authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (username, authority)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;