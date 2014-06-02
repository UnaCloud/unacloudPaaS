# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table command (
  id                        bigint auto_increment not null,
  command                   varchar(255),
  multiplicity              integer,
  running_user              varchar(255),
  type                      integer,
  main_command              tinyint(1) default 0,
  rol_execution_id          bigint,
  constraint ck_command_multiplicity check (multiplicity in (0,1,2)),
  constraint ck_command_type check (type in (0,1)),
  constraint pk_command primary key (id))
;

create table command_wait (
  id                        bigint auto_increment not null,
  process_id                bigint,
  status                    integer,
  node_id                   bigint,
  constraint ck_command_wait_status check (status in (0,1,2,3,4,5,6)),
  constraint pk_command_wait primary key (id))
;

create table execution_log (
  id                        bigint auto_increment not null,
  id_node_log               bigint,
  time                      datetime,
  component                 varchar(255),
  message                   varchar(255),
  platform_execution_id     bigint,
  constraint pk_execution_log primary key (id))
;

create table grupo (
  groupname                 varchar(255) not null,
  constraint pk_grupo primary key (groupname))
;

create table main_command (
  id                        bigint auto_increment not null,
  command                   varchar(255),
  multiplicity              integer,
  resource_type             integer,
  running_user              varchar(255),
  role_id                   bigint,
  constraint ck_main_command_multiplicity check (multiplicity in (0,1,2)),
  constraint ck_main_command_resource_type check (resource_type in (0,1)),
  constraint pk_main_command primary key (id))
;

create table node (
  id                        bigint auto_increment not null,
  ip_address                varchar(255),
  hostname                  varchar(255),
  iaas_execution_id         varchar(255),
  max_secuential_fail_count integer,
  rol_execution_id          bigint,
  constraint pk_node primary key (id))
;

create table platform (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  cluster_id                bigint,
  waiter_class              varchar(255),
  main_command_id           bigint,
  constraint pk_platform primary key (id))
;

create table platform_execution (
  id                        bigint auto_increment not null,
  run_name                  varchar(255),
  main_command_args         varchar(255),
  start_time                datetime,
  eternal                   tinyint(1) default 0,
  status                    integer,
  user_user_id              bigint,
  platform_id               bigint,
  constraint ck_platform_execution_status check (status in (0,1,2,3,4,5,6)),
  constraint pk_platform_execution primary key (id))
;

create table puppet_module (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_puppet_module primary key (id))
;

create table puppet_module_usage (
  id                        integer auto_increment not null,
  puppet_module_id          bigint,
  constraint pk_puppet_module_usage primary key (id))
;

create table puppet_param (
  id                        bigint auto_increment not null,
  puppet_module_id          bigint not null,
  name                      varchar(255),
  default_value             varchar(255),
  constraint pk_puppet_param primary key (id))
;

create table puppet_param_value (
  id                        bigint auto_increment not null,
  puppet_module_usage_id    integer not null,
  name                      varchar(255),
  valor                     varchar(255),
  constraint pk_puppet_param_value primary key (id))
;

create table rol (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  multiplicity              integer,
  image_id                  integer,
  mount_platform_folder     tinyint(1) default 0,
  puppet_module_id          integer,
  platform_id               bigint,
  constraint ck_rol_multiplicity check (multiplicity in (0,1,2)),
  constraint pk_rol primary key (id))
;

create table rol_execution (
  id                        bigint auto_increment not null,
  rol_id                    bigint,
  cores_per_vm              integer,
  ram_per_core              integer,
  size                      integer,
  platform_execution_id     bigint,
  constraint pk_rol_execution primary key (id))
;

create table sshshared_key (
  id                        bigint auto_increment not null,
  platform_id               bigint,
  source_role_id            bigint,
  target_role_id            bigint,
  constraint pk_sshshared_key primary key (id))
;

create table user (
  user_id                   bigint auto_increment not null,
  username                  varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  password                  varchar(255),
  hash                      varchar(255),
  activated                 tinyint(1) default 0,
  email                     varchar(255),
  description               varchar(255),
  constraint uq_user_username unique (username),
  constraint pk_user primary key (user_id))
;

create table user_file (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  path                      varchar(255),
  user_user_id              bigint,
  constraint pk_user_file primary key (id))
;


create table rol_execution_puppet_module_usage (
  rol_execution_id               bigint not null,
  puppet_module_usage_id         integer not null,
  constraint pk_rol_execution_puppet_module_usage primary key (rol_execution_id, puppet_module_usage_id))
;

create table user_grupo (
  user_user_id                   bigint not null,
  grupo_groupname                varchar(255) not null,
  constraint pk_user_grupo primary key (user_user_id, grupo_groupname))
;
alter table command add constraint fk_command_rolExecution_1 foreign key (rol_execution_id) references rol_execution (id) on delete restrict on update restrict;
create index ix_command_rolExecution_1 on command (rol_execution_id);
alter table command_wait add constraint fk_command_wait_node_2 foreign key (node_id) references node (id) on delete restrict on update restrict;
create index ix_command_wait_node_2 on command_wait (node_id);
alter table node add constraint fk_node_rolExecution_3 foreign key (rol_execution_id) references rol_execution (id) on delete restrict on update restrict;
create index ix_node_rolExecution_3 on node (rol_execution_id);
alter table platform add constraint fk_platform_mainCommand_4 foreign key (main_command_id) references main_command (id) on delete restrict on update restrict;
create index ix_platform_mainCommand_4 on platform (main_command_id);
alter table platform_execution add constraint fk_platform_execution_user_5 foreign key (user_user_id) references user (user_id) on delete restrict on update restrict;
create index ix_platform_execution_user_5 on platform_execution (user_user_id);
alter table platform_execution add constraint fk_platform_execution_platform_6 foreign key (platform_id) references platform (id) on delete restrict on update restrict;
create index ix_platform_execution_platform_6 on platform_execution (platform_id);
alter table puppet_module_usage add constraint fk_puppet_module_usage_puppetModule_7 foreign key (puppet_module_id) references puppet_module (id) on delete restrict on update restrict;
create index ix_puppet_module_usage_puppetModule_7 on puppet_module_usage (puppet_module_id);
alter table puppet_param add constraint fk_puppet_param_puppet_module_8 foreign key (puppet_module_id) references puppet_module (id) on delete restrict on update restrict;
create index ix_puppet_param_puppet_module_8 on puppet_param (puppet_module_id);
alter table puppet_param_value add constraint fk_puppet_param_value_puppet_module_usage_9 foreign key (puppet_module_usage_id) references puppet_module_usage (id) on delete restrict on update restrict;
create index ix_puppet_param_value_puppet_module_usage_9 on puppet_param_value (puppet_module_usage_id);
alter table rol add constraint fk_rol_puppetModule_10 foreign key (puppet_module_id) references puppet_module_usage (id) on delete restrict on update restrict;
create index ix_rol_puppetModule_10 on rol (puppet_module_id);
alter table rol add constraint fk_rol_platform_11 foreign key (platform_id) references platform (id) on delete restrict on update restrict;
create index ix_rol_platform_11 on rol (platform_id);
alter table rol_execution add constraint fk_rol_execution_rol_12 foreign key (rol_id) references rol (id) on delete restrict on update restrict;
create index ix_rol_execution_rol_12 on rol_execution (rol_id);
alter table rol_execution add constraint fk_rol_execution_platformExecution_13 foreign key (platform_execution_id) references platform_execution (id) on delete restrict on update restrict;
create index ix_rol_execution_platformExecution_13 on rol_execution (platform_execution_id);
alter table sshshared_key add constraint fk_sshshared_key_platform_14 foreign key (platform_id) references platform (id) on delete restrict on update restrict;
create index ix_sshshared_key_platform_14 on sshshared_key (platform_id);
alter table sshshared_key add constraint fk_sshshared_key_sourceRole_15 foreign key (source_role_id) references rol (id) on delete restrict on update restrict;
create index ix_sshshared_key_sourceRole_15 on sshshared_key (source_role_id);
alter table sshshared_key add constraint fk_sshshared_key_targetRole_16 foreign key (target_role_id) references rol (id) on delete restrict on update restrict;
create index ix_sshshared_key_targetRole_16 on sshshared_key (target_role_id);
alter table user_file add constraint fk_user_file_user_17 foreign key (user_user_id) references user (user_id) on delete restrict on update restrict;
create index ix_user_file_user_17 on user_file (user_user_id);



alter table rol_execution_puppet_module_usage add constraint fk_rol_execution_puppet_module_usage_rol_execution_01 foreign key (rol_execution_id) references rol_execution (id) on delete restrict on update restrict;

alter table rol_execution_puppet_module_usage add constraint fk_rol_execution_puppet_module_usage_puppet_module_usage_02 foreign key (puppet_module_usage_id) references puppet_module_usage (id) on delete restrict on update restrict;

alter table user_grupo add constraint fk_user_grupo_user_01 foreign key (user_user_id) references user (user_id) on delete restrict on update restrict;

alter table user_grupo add constraint fk_user_grupo_grupo_02 foreign key (grupo_groupname) references grupo (groupname) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table command;

drop table command_wait;

drop table execution_log;

drop table grupo;

drop table main_command;

drop table node;

drop table platform;

drop table platform_execution;

drop table puppet_module;

drop table puppet_module_usage;

drop table puppet_param;

drop table puppet_param_value;

drop table rol;

drop table rol_execution;

drop table rol_execution_puppet_module_usage;

drop table sshshared_key;

drop table user;

drop table user_grupo;

drop table user_file;

SET FOREIGN_KEY_CHECKS=1;

