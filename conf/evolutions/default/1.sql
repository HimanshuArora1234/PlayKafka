# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  addr_id                       integer auto_increment not null,
  addr                          varchar(255),
  uid                           integer,
  constraint pk_address primary key (addr_id)
);

create table user (
  user_id                       integer auto_increment not null,
  name                          varchar(255),
  age                           integer,
  constraint pk_user primary key (user_id)
);

alter table address add constraint fk_address_uid foreign key (uid) references user (user_id) on delete restrict on update restrict;
create index ix_address_uid on address (uid);


# --- !Downs

alter table address drop foreign key fk_address_uid;
drop index ix_address_uid on address;

drop table if exists address;

drop table if exists user;

