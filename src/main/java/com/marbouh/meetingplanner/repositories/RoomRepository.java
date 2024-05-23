package com.marbouh.meetingplanner.repositories;
import com.marbouh.meetingplanner.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByName(String name);

    List<Room> findAllByCapacityGreaterThan(Integer capacity);


  // Query pour trouver les salles non réservées pour une date donnée, et ayant une capacité suffisante
    @Query(nativeQuery =true,value="select * from room r where r.id NOT IN (select room_id from reservation where start_date=:start_date and end_date=:end_date) and capacity>=:nbParticipants")
    List<Room> findRoomNotBooked(@Param("start_date") LocalDateTime start_date, @Param("end_date") LocalDateTime end_date, int nbParticipants);

    List<Room> findRoomsByCapacityGreaterThan(Integer capacity);


    boolean deleteByName(String name);
}
