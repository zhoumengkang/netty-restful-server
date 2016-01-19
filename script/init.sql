CREATE DATABASE `netty_rest`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `icon` varchar(16) NOT NULL DEFAULT '',
  `sex` enum('0','1','2') DEFAULT '0',
  `birthday` int(11) NOT NULL DEFAULT '0',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1',
  `register_ts` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;

INSERT INTO `user` (`id`, `name`, `icon`, `sex`, `birthday`, `status`, `register_ts`)
VALUES
  (1,'mengkang','1452501492495794','1',1130211450,1,1453211450);

UNLOCK TABLES;