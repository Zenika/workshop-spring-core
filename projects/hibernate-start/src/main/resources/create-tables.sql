drop sequence if exists users_seq;
create sequence users_seq;
drop table if exists users;
create table users(id bigint, login varchar(255), password varchar(512), primary key(id));
alter table users add constraint login_unique unique (login);