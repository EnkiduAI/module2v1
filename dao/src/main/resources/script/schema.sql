
CREATE TABLE  `gift_certificate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `description` VARCHAR(400) NULL DEFAULT NULL,
  `price` INT NULL DEFAULT NULL,
  `duration` VARCHAR(45) NULL DEFAULT NULL,
  `create_date` TIMESTAMP(2) NULL DEFAULT NULL,
  `last_update_date` TIMESTAMP(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `gift_certificate_has_tag` (
  `id_gift_certificate_has_tag` int not null AUTO_INCREMENT,
  `gift_certificate_id` INT NOT NULL references gift_certificate (id),
  `tag_id` INT NOT NULL references tag (id),
  PRIMARY KEY (`id_gift_certificate_has_tag`));