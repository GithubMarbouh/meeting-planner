package com.marbouh.meetingplanner.services;
import com.marbouh.meetingplanner.dtos.ReservationDto;
import com.marbouh.meetingplanner.exception.WrongData;
import com.marbouh.meetingplanner.models.Equipment;
import com.marbouh.meetingplanner.models.Meeting;
import com.marbouh.meetingplanner.models.Room;
import com.marbouh.meetingplanner.repositories.MeetingRepository;
import com.marbouh.meetingplanner.repositories.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
      private final RoomRepository roomRepository;
      private MeetingRepository meetingRepository;

      public List<Room> getAllRooms() {
          if (roomRepository.findAll().isEmpty()) {
              throw new RuntimeException("No rooms found");
          }
            return roomRepository.findAll();
      }

    public Room getRoom(String name){
        if(roomRepository.findByName(name)==null)
            throw new EntityNotFoundException("No room found with the name {"+name+"}");

        return roomRepository.findByName(name);
    }
    public List<Room> getAvailableRoomByBookingDate(ReservationDto ReservationDto, List<Equipment> equipments){

        //Get available room id
        List<Room> rooms = roomRepository.findRoomNotBooked(ReservationDto.getStartDate(),ReservationDto.getEndDate(),ReservationDto.getNbParticipants());

        if(ReservationDto.getMeetingType().equals("RS"))
            return roomRepository.findRoomNotBooked(ReservationDto.getStartDate(),ReservationDto.getEndDate(),ReservationDto.getNbParticipants());

        List<Room> meetingRooms = new ArrayList<>();


        rooms.forEach(room -> {
            if(room.getEquipments().size()!=equipments.size())
                return;

            for (int i = 0; i < room.getEquipments().size(); i++) {
                if(!equipments.contains(room.getEquipments().get(i)))
                    return;
            }
            meetingRooms.add(room);

        });

        return meetingRooms;
    }
    public List<Room> getAvailableRoomByMeetingType(String meetingType){

        List<Meeting> meetings = meetingRepository.findMeetingByType(meetingType);
        if (meetings.isEmpty())
            throw new EntityNotFoundException("There are no meetings with this type");

        Meeting meeting = meetings.get(0); // get the first meeting

        List<Room> rooms = roomRepository.findAll();

        if(meetingType.equals("RS"))
            return rooms;

        List<Room> meetingRooms = new ArrayList<>();

        rooms.forEach(room -> {
            if(room.getEquipments().size()!=meeting.getEquipments().size())
                return;

            for (int i = 0; i < room.getEquipments().size(); i++) {
                if(!meeting.getEquipments().contains(room.getEquipments().get(i)))
                    return;
            }
            meetingRooms.add(room);
        });

        if (meetingRooms.isEmpty())
            throw new EntityNotFoundException("No room available with this type of meeting");

        return meetingRooms;
    }


    public List<Room> findRoomByCapacity(Integer capacity){
        if(roomRepository.findAllByCapacityGreaterThan(capacity).isEmpty())
            throw new EntityNotFoundException("No room found with this capacity of people");

        return roomRepository.findAllByCapacityGreaterThan(capacity);
    }

    public void checkReservationDto(ReservationDto ReservationDto) throws Exception {

        if(ReservationDto.getStartDate().compareTo(LocalDateTime.now()) < 0)
            throw new WrongData("You can only reserve meeting from now");

        if(ReservationDto.getStartDate().getHour()<8
                || ReservationDto.getEndDate().getHour()>20 || ReservationDto.getStartDate().getHour()>20
                || isWeekend(ReservationDto.getStartDate()) || isWeekend(ReservationDto.getEndDate()) )
            throw new WrongData("The meeting reservation can only begin from 8:00 to 20:00 and no week-end");

        if(ReservationDto.getStartDate().getDayOfMonth()!=ReservationDto.getEndDate().getDayOfMonth()
                || ReservationDto.getStartDate().getMonthValue()!=ReservationDto.getEndDate().getMonthValue()
                || ReservationDto.getStartDate().getYear()!=ReservationDto.getEndDate().getYear())
            throw new WrongData("You can only reserve one hour");

        if(ReservationDto.getEndDate().getHour() > ReservationDto.getStartDate().getHour() + 1
                || ReservationDto.getStartDate().getHour() >= ReservationDto.getEndDate().getHour())
            throw new WrongData("You can only reserve one hour");

        if(ReservationDto.getStartDate().getMinute()!=0 || ReservationDto.getEndDate().getMinute()!=0 ||
                ReservationDto.getStartDate().getSecond()!=0 || ReservationDto.getEndDate().getSecond()!=0 )
            throw new WrongData("You can't reserve with this format of time - like 10:00:00");

    }


    private static boolean isWeekend(LocalDateTime localDate) {

        // get Day of week for the passed LocalDate
        return (localDate.get(ChronoField.DAY_OF_WEEK) == 6)
                || (localDate.get(ChronoField.DAY_OF_WEEK) == 7);
    }


}
