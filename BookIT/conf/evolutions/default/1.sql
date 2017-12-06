# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  user_type                     varchar(31) not null,
  user_email                    varchar(255) not null,
  user_id                       integer,
  user_name                     varchar(255),
  user_password                 varchar(255),
  phone_no                      bigint,
  has_any                       tinyint(1) default 0 not null,
  is_approved                   tinyint(1) default 0 not null,
  constraint pk_user primary key (user_email)
);


# --- !Downs

drop table if exists user;

