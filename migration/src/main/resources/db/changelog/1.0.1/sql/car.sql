create sequence car_seq
    start 1;

create table if not exists car
(
    id         bigint      not null default (nextval('car_seq')),
    model      varchar(100) not null,
    createDttm date not null
);

GO

alter table car
    add constraint car_pk primary key (id);
