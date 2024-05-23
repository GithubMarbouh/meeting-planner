package com.marbouh.meetingplanner.repositories;

import com.marbouh.meetingplanner.models.Meeting;
import com.marbouh.meetingplanner.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    Boolean existsByStartDateAndEndDateAndType(LocalDateTime startDate, LocalDateTime endDate, String type);

    List<Reservation> findAllByRoomName(String roomName);

    List<Reservation> findAllByMeeting(Meeting meeting);

    List<Reservation> findAllByStartDateAndEndDate(LocalDateTime startDate, LocalDateTime endDate);
    List<Reservation> findAllByStartDateAfterAndEndDateBefore(LocalDateTime startDate, LocalDateTime endDate);
}
