DROP TABLE IF EXISTS `crud_uuid`;
CREATE TABLE `crud_uuid` (
  `id` VARCHAR(32) NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `grade` INT NOT NULL,
  `birthday` DATE NULL,
  `wallet_balance` DECIMAL(6,3) NULL,
  `rich_text` LONGTEXT NULL,
  `json_data` JSON NULL,
  `base64_data` LONGTEXT NULL,
  `create_by` VARCHAR(45) NULL,
  `create_time` VARCHAR(45) NULL,
  `update_by` VARCHAR(45) NULL,
  `update_time` VARCHAR(45) NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `phone_number_UNIQUE` (`phone_number`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='crud uuid table';;
