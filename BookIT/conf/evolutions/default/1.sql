# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table event (
  event_id                      integer auto_increment not null,
  event_name                    varchar(255),
  event_date                    datetime(6),
  event_location                varchar(255),
  per_ticket_cost               float not null,
  event_owner_email             varchar(255),
  available_no_of_seats         integer,
  total_sales                   integer,
  constraint pk_event primary key (event_id)
);

create table ticket (
  ticket_id                     integer auto_increment not null,
  num_seats                     integer not null,
  event_manager_mail            varchar(255),
  customer_mail                 varchar(255),
  book_date                     datetime(6),
  status                        tinyint(1) default 0 not null,
  constraint pk_ticket primary key (ticket_id)
);

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

drop table if exists event;

drop table if exists ticket;

drop table if exists user;

