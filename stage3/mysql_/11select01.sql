-- select  TODO distinct关键字可以去重
-- 创建一个stu表
create table student(
	id int not null default 1,
	name varchar(20) not null default '',
	chinese float not null default 0.0,
	english float not null default 0.0,
	math float not null default 0.0
);

insert into student(id,name,chinese,english,math) values(1,'韩顺平',89,78,90);
insert into student(id,name,chinese,english,math) values(2,'张飞',67,98,56);
insert into student(id,name,chinese,english,math) values(3,'宋江',87,78,77);
insert into student(id,name,chinese,english,math) values(4,'关羽',88,98,90);
insert into student(id,name,chinese,english,math) values(5,'赵云',82,84,67);
insert into student(id,name,chinese,english,math) values(6,'欧阳锋',55,85,45);
insert into student(id,name,chinese,english,math) values(7,'黄蓉',75,65,30);

-- 查询所有
select * from student;

-- 所有名字和英语成绩
select name, english from student;

-- 所有名字和英语成绩 去重
select distinct name, english from student;

select english from student;
select distinct english from student;