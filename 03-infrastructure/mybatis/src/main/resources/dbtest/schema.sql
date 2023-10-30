CREATE TABLE `crud_uuid`
(
    `id`                    varchar(32)  NOT NULL COMMENT 'primary key',
    `name`                  varchar(50) NOT NULL,
    `phone_number`          varchar(20) NOT NULL,
    `zip_code`              varchar(20) NOT NULL,
    `age`                   int(3) DEFAULT NULL COMMENT '1-200',
    `type`                  int(2)      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='crud uuid table';