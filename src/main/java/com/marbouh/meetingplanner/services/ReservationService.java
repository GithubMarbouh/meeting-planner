package com.marbouh.meetingplanner.services;
import com.marbouh.meetingplanner.dtos.ReservationDto;
import com.marbouh.meetingplanner.mappers.MeetingMapper;
import com.marbouh.meetingplanner.models.Meeting;
import com.marbouh.meetingplanner.models.Reservation;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.repositories.MeetingRepository;
import com.marbouh.meetingplanner.repositories.ReservationRepository;
import com.marbouh.meetingplanner.repositories.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {
    private ReservationRepository reservationRepository;
    private MeetingRepository meetingRepository;
    private MeetingMapper meetingMapper;
    private RoomService roomService;

    // Methods
    @Transactional
    public Reservation createReservation(ReservationDto reservation) throws Exception{
        // Check if the room is available
        roomService.checkReservationDto(reservation);
        List<Room> rooms = availbleRooms(reservation);
        Reservation newReservation = meetingMapper.fromResvationDtoToReservation(reservation, rooms.get(0));

        //Booking the room 1 hour for cleaning

        Reservation bookCleaning = meetingMapper.fromResvationDtoToReservation(reservation, rooms.get(0));
        LocalDateTime StartCleaning = bookCleaning.getStartDate().plusHours(1);
        LocalDateTime EndCleaning = bookCleaning.getEndDate().plusHours(1);
        bookCleaning.setStartDate(StartCleaning);
        bookCleaning.setEndDate(EndCleaning);
        bookCleaning.setType("Cleaning");
        reservationRepository.save(bookCleaning);

        return reservationRepository.save(newReservation);
    }

    public List<Room> availbleRooms(ReservationDto reservationDto) throws Exception {
        roomService.checkReservationDto(reservationDto);
        List<Meeting> meetings = meetingRepository.findMeetingByType(reservationDto.getMeetingType());
        if (meetings.isEmpty())
            throw new EntityNotFoundException("Meeting type not found with this name");

        Meeting meeting = meetings.get(0); // get the first meeting

        List<Room> availableRooms = roomService.getAvailableRoomByBookingDate(reservationDto, meeting.getEquipments());
        if (availableRooms.isEmpty()) {
            throw new EntityNotFoundException("No room available for this date");
        }

        System.out.println("Size of available rooms: " + availableRooms.size());
        return availableRooms;
    }

    public List<Reservation> getReservationsByRoom(String roomName) {
        if (reservationRepository.findAllByRoomName(roomName).isEmpty()) {
            throw new RuntimeException("No reservations found with room name: " + roomName);
        }
        return reservationRepository.findAllByRoomName(roomName);
    }
    public List<Reservation> getReservationsByMeetingName(String type) {
        List<Meeting> meetings = meetingRepository.findMeetingByType(type);
        if (meetings.isEmpty()){
            throw new RuntimeException("No meeting found with type: " + type);
        }
        Meeting meeting = meetings.get(0); // get the first meeting
        return reservationRepository.findAllByMeeting(meeting);
    }

    public List<Reservation> getReservationsByDay() {
      LocalDateTime startDate = LocalDateTime.now().withHour(7).withMinute(0).withSecond(0);
      LocalDateTime endDate = LocalDateTime.now().withHour(21).withMinute(0).withSecond(0);

      if (reservationRepository.findAllByStartDateAfterAndEndDateBefore(startDate, endDate).size() == 0){
          throw new RuntimeException("No reservations found for today");
      }

        return reservationRepository.findAllByStartDateAfterAndEndDateBefore(startDate, endDate);
    }
}
