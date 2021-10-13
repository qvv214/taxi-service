create sequence if not exists trip_info_seq
    start 1;

create table if not exists trip_info
(
    trip_id         bigint  not null     default nextval('trip_info_seq' :: regclass),
    start_trip      timestamp with time zone default null,
    end_trip        timestamp with time zone default null,
    rating          double precision  check (rating > 0 and rating <= 5),
    driver_id         bigint not null,

    constraint trips_info_id_pk
       primary key (trip_id)
);

comment on table trip_info is 'Информация о поездках';

comment on column trip_info.trip_id is 'Идентификатор поездки';
comment on column trip_info.start_trip is 'Время начала поездки';
comment on column trip_info.end_trip is 'Время окончания поездки';
comment on column trip_info.rating is 'Оценка клиента';
comment on column trip_info.driver_id is 'Id водителя которому принадлежит поездка';

alter table trip_info
    add constraint driver_id_fk foreign key (driver_id)
        references taxi_drive_info(driver_id)
