-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Migrated Schemata: wasteless_db
-- Source Schemata: wasteless_db
-- Created: Sat Mar 28 09:49:22 2020
-- Workbench Version: 8.0.18
-- ----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Schema wasteless_db
-- ----------------------------------------------------------------------------
DROP SCHEMA IF EXISTS `wasteless_db` ;
CREATE SCHEMA IF NOT EXISTS `wasteless_db` ;

-- ----------------------------------------------------------------------------
-- Table wasteless_db.bought_groceries
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `wasteless_db`.`bought_groceries` (
  `id_bought` INT(10) NOT NULL AUTO_INCREMENT,
  `item_name` VARCHAR(90) NOT NULL,
  `quantity` INT(11) NOT NULL,
  `calories` INT(11) NOT NULL,
  `purchase_date` DATE NOT NULL,
  `expiration_date` DATE NOT NULL,
  `consumption_date` DATE NULL DEFAULT NULL,
  `fk_user` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_bought`),
  INDEX `fk_user_idx` (`fk_user` ASC),
  CONSTRAINT `fk_user`
    FOREIGN KEY (`fk_user`)
    REFERENCES `wasteless_db`.`users` (`id_users`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 81
DEFAULT CHARACTER SET = latin1;

-- ----------------------------------------------------------------------------
-- Table wasteless_db.groceries
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `wasteless_db`.`groceries` (
  `id_groceries` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `item_name` VARCHAR(45) NOT NULL,
  `calories` INT(11) NOT NULL DEFAULT '0',
  `days` INT(11) NOT NULL,
  PRIMARY KEY (`id_groceries`),
  UNIQUE INDEX `id_groceries_UNIQUE` (`id_groceries` ASC),
  UNIQUE INDEX `item_name_UNIQUE` (`item_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = latin1;

-- ----------------------------------------------------------------------------
-- Table wasteless_db.users
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `wasteless_db`.`users` (
  `id_users` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `status` TINYINT(4) NULL DEFAULT '0',
  PRIMARY KEY (`id_users`),
  UNIQUE INDEX `id_users_UNIQUE` (`id_users` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;
SET FOREIGN_KEY_CHECKS = 1;
