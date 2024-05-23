package com.marbouh.meetingplanner.mappers;
import com.marbouh.meetingplanner.dtos.ReservationDto;
import com.marbouh.meetingplanner.models.Meeting;
import com.marbouh.meetingplanner.models.Reservation;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.repositories.MeetingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MeetingMapper {
    private MeetingRepository meetingRepository;
    public Reservation fromResvationDtoToReservation(ReservationDto reservationDto, Room room) {
        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());

        List<Meeting> meetings = meetingRepository.findMeetingByType(reservationDto.getMeetingType());
        if (meetings.isEmpty())
            throw new EntityNotFoundException("Meeting type not found with this name");
        Meeting meeting = meetings.get(0); // get the first meeting

        reservation.setMeeting(meeting);
        reservation.setRoom(room);

        return reservation;
    }

}
