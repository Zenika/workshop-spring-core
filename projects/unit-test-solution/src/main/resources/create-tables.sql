drop table if exists users;
create table users(id bigint auto_increment, login varchar(255), password varchar(512), primary key(login));