CREATE DATABASE `mybatis`;
USE `mybatis`;

CREATE TABLE `monster` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `age` INT NOT NULL ,
    `birthday` DATE DEFAULT NULL,
    `email` VARCHAR(255) NOT NULL,
    `gender` TINYINT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `salary` DOUBLE NOT NULL,
    PRIMARY KEY (`id`)
) CHARSET=utf8;

INSERT INTO `monster`
(age, birthday, email, gender, name, salary)
VALUES (10, null, 'test.edu.con', 1, 'dam', 10000);

DELETE FROM `monster` WHERE id = 1;

UPDATE `monster`
set `age`=90, birthday=null, email='awert@edu', gender=0, name='qwer', salary=2.0
where id=4;

select * from `monster` where id = 4;

