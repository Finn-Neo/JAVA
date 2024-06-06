CREATE DATABASE e_commerce_center_db;
USE e_commerce_center_db;
CREATE TABLE member
(
    id     BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
    NAME   VARCHAR(64) COMMENT '用户名',
    pwd    CHAR(32) COMMENT '密码',
    mobile VARCHAR(20) COMMENT '手机号码',
    email  VARCHAR(64) COMMENT '邮箱',
    gender TINYINT COMMENT '性别',
    PRIMARY KEY (id)
);

INSERT INTO member
VALUES (NULL, 'smith', MD5('123'), '123456789000', 'smith@sohu.com', 1);

SELECT *
FROM member;