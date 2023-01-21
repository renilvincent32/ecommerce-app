--liquibase formatted sql

--changeset rvincent:1
create table login_user (user_id uuid not null, email varchar(255), first_name varchar(255), last_name varchar(255), password varchar(255), role varchar(255), primary key (user_id))

--changeset rvincent:2
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset rvincent:3
insert into login_user values (uuid_generate_v4(), 'hello@test.com', 'John', 'Doe', '12345', 'USER');

--changeset rvincent:4
create table authority (authority_id uuid not null, authority_name varchar(255) not null);

--changeset rvincent:5
create table user_authority (user_id uuid not null, authority_id uuid not null);

--changeset rvincent:6
ALTER TABLE login_user DROP COLUMN IF EXISTS role;

--changeset rvincent:7
insert into authority values (uuid_generate_v4(), 'USER');
insert into authority values (uuid_generate_v4(), 'ADMIN');

--changeset rvincent:8
insert into user_authority select a.user_id, b.authority_id from login_user a, authority b where b.authority_name = 'USER';

--changeset rvincent:9
ALTER TABLE authority ADD PRIMARY KEY (authority_id);

--changeset rvincent:10
ALTER TABLE user_authority ADD PRIMARY KEY (user_id, authority_id);

--changeset rvincent:11
ALTER TABLE user_authority ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES login_user (user_id)
MATCH SIMPLE ON UPDATE CASCADE ON DELETE NO ACTION;

--changeset rvincent:12
ALTER TABLE user_authority ADD CONSTRAINT fk_authority FOREIGN KEY (authority_id) REFERENCES authority (authority_id)
MATCH SIMPLE ON UPDATE CASCADE ON DELETE NO ACTION;