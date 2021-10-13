create sequence if not exists drive_level_seq
    start 1;

create table if not exists drive_level
(
    level_id  bigint  not null  default nextval('drive_level_seq' :: regclass),
    name      varchar unique not null,


    constraint drive_level_id_pk
    primary key (level_id)
);

comment on table drive_level is 'Уровни заказываемых такси (эконом, комфорт, бизнес)';

comment on column drive_level.level_id is 'Идентификатор уровня';
comment on column drive_level.name is 'Название уровня';