package ru.digitalleague.taxi_company.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import ru.digitalleague.taxi_company.model.TaxiDriverInfoModel;

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
            @Result(property = "createDttm", column = "create_dttm")
    })

    @Select("select * from taxi_drive_info t left join city_queue c on c.city_id=t.city where c.name= #{city} and t.level=#{level} and busy=false")
    List<TaxiDriverInfoModel> findTaxiDriveForTrip(String city, int level);

    @Insert("insert into trip_info(driver_id) values (#{id})")
    int createTrip(Long id);

    @Update("update trip_info set start_trip=now() where driver_id=#{id} and start_trip is null and end_trip is null")
    int tripStart(Long id);

    @Update("update trip_info set end_trip=now() where driver_id=#{id} and end_trip is null and start_trip is not null")
    int tripEnd(Long id);

    @Update("update taxi_drive_info set busy=#{flag} where driver_id=#{id}")
    void setBusy(Long id, boolean flag);
}
