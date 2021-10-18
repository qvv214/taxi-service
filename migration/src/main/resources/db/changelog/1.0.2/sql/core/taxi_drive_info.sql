create sequence if not exists taxi_driver_seq
    start 1;

create table if not exists taxi_drive_info
(
    driver_id   bigint  not null     default nextval('taxi_driver_seq' :: regclass),
    last_name   text    not null,
    first_name  text    not null,
    level       int     not null,
    rating      double precision  check (rating > 0 and rating <= 5),
    price       bigint  not null,
    busy        boolean not null,
    car_model   int     not null,
    city        int     not null,
    create_dttm timestamp with time zone default now() not null,

    constraint taxi_drive_info_id_pk
        primary key (driver_id),

    constraint taxi_driver_info_model_fk foreign key (car_model)
        references car(id),

    constraint taxi_driver_info_city_fk foreign key (city)
        references city_queue(city_id),

    constraint taxi_driver_info_level_fk foreign key (level)
        references drive_level(level_id)

);

comment on table taxi_drive_info is 'Информация о водителях такси';

comment on column taxi_drive_info.driver_id is 'Идентификатор водителя';
comment on column taxi_drive_info.last_name is 'Фамилия водителя';
comment on column taxi_drive_info.first_name is 'Имя водителя';
comment on column taxi_drive_info.level is 'Уровень водителя';
comment on column taxi_drive_info.car_model is 'Модель автомобиля';
comment on column taxi_drive_info.create_dttm is 'Дата внесения информации о водителе';
comment on column taxi_drive_info.rating is 'Рейтинг водителя';
comment on column taxi_drive_info.city is 'Город водителя';


