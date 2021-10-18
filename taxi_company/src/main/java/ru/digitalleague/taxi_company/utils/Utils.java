package ru.digitalleague.taxi_company.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;

@Slf4j
@Component
public class Utils {
    public Long getDifferenceTime(OffsetDateTime start, OffsetDateTime end) {
        Instant instantX = start.toInstant();
        Instant instantY = end.toInstant();
        Duration duration = Duration.between(instantX, instantY);
        return duration.toMinutes();
    }
}
