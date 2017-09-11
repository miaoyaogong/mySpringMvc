CREATE DATABASE IF NOT EXISTS pk DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use pk;
CREATE TABLE IF NOT EXISTS `data` (
  `name` varchar(20) NOT NULL,
  `value` text,
PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
