package ru.digitalleague.taxi_company.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TripInfoModel {

    private Long trip_id;

    private Long client_id;

    private int rating;

    private Long driver_id;

    private OffsetDateTime start_trip;

    private OffsetDateTime end_trip;
}
