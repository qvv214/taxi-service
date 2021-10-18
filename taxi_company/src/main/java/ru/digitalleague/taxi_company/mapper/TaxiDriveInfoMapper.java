package ru.digitalleague.taxi_company.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import ru.digitalleague.core.model.TaxiDriverInfoModel;
import ru.digitalleague.taxi_company.model.TripInfoModel;

import java.util.List;

@Repository
@Mapper
public interface TaxiDriveInfoMapper {

    @Results(id = "taxi_drive_info", value = {
            @Result(property = "driverId", column = "driver_id"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "level", column = "level"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "busy", column = "busy"),
            @Result(property = "carModel", column = "car_model"),
            @Result(property = "city", column = "city"),
            @Result(property = "createDttm", column = "create_dttm"),
            @Result(property = "price", column = "price")
    })

    @Select("select * from taxi_drive_info t left join city_queue c on c.city_id=t.city where c.name= #{city} and t.level=#{level} and busy=false")
    List<TaxiDriverInfoModel> findTaxiDriveForTrip(String city, int level);

    @Insert("insert into trip_info(driver_id, client_id) values (#{driver_id}, #{client_id})")
    int createTrip(Long driver_id, Long client_id);

    @Update("update trip_info set start_trip=now() where driver_id=#{id} and start_trip is null and end_trip is null")
    int tripStart(Long id);

    @Update("update trip_info set end_trip=now() where driver_id=#{id} and end_trip is null and start_trip is not null")
    int tripEnd(Long id);

    @Update("update taxi_drive_info set busy=#{flag} where driver_id=#{id}")
    int setBusy(Long id, boolean flag);

    @Select("select * from trip_info where trip_id=#{id}")
    TripInfoModel getTripById(Long id);

    @Select("select price from taxi_drive_info where driver_id=#{id}")
    Long getDriverPriceById(Long id);

    @Update("update trip_info set total=#{total} where trip_id=#{id}")
    int setTripTotalById(Long id, Long total);

    @Update("update trip_info set rating=#{rating} where trip_id=#{id} and rating is null")
    int updateRatingForTripById(Long id, int rating);

    @Result(property = "driverId", column = "driver_id")
    @Select("select taxi.driver_id, taxi.rating from trip_info trip " +
    "join taxi_drive_info taxi on taxi.driver_id = trip.driver_id " +
    "where trip_id=#{id}")
    TaxiDriverInfoModel getDriverFromTripInfoById(Long id);

    @Update("update taxi_drive_info set rating=#{rating} where id=#{id}")
    int updateRatingForDriverById(Long id, Double rating);

    @Update("update taxi_drive_info set rating=#{rating} where driver_id=#{id}")
    int updateRatingById(Long id, Double rating);

}
